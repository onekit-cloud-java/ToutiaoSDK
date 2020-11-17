package demo;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.entity.*;
import com.toutiao.developer.entity.v2.*;
import com.toutiao.developer.ToutiaoSDK;
import com.toutiao.developer.ToutiaoSDK2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.ArrayList;

@RestController
@RequestMapping("/")

public class Demo {
    final String sig_method = "hmac_sha256";
    ToutiaoSDK sdk = new ToutiaoSDK("https://developer.toutiao.com");
    ToutiaoSDK2 sdk2 = new ToutiaoSDK2("https://developer.toutiao.com");

    @RequestMapping("/decrypt")
    public String decrypt(
            @RequestParam String session_key,
            @RequestParam String iv,
            @RequestParam String encryptedData,
            @RequestParam String rawData,
            @RequestParam String signature
    ) throws Exception {
        if (!sdk._signRaw(rawData, session_key).equals(signature)) {
            throw new Exception("bad sign!!");
        }
        return sdk._decrypt(encryptedData, iv, session_key);
    }

    @RequestMapping("/getAccessToken")
    public String getAccessToken() throws Exception {
        try {
            return JSON.object2string(sdk.apps__token(ToutiaoAccount.appid, ToutiaoAccount.secret, "client_credential"));
        } catch (ToutiaoError e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }

    @RequestMapping("/code2Session1")
    public String code2Session1(
            @RequestParam String code) throws Exception {
        try {
            return JSON.object2string(sdk.apps__jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, code, null));
        } catch (ToutiaoError e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }

    @RequestMapping("/code2Session2")
    public String code2Session2(
            @RequestParam String anonymous_code) throws Exception {
        try {
            return JSON.object2string(sdk.apps__jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, null, anonymous_code));
        } catch (ToutiaoError e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }


    @RequestMapping("/setUserStorage")
    public String setUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        apps__set_user_storage_body body = new apps__set_user_storage_body();
        body.setTt_kv_list(new ArrayList<KV>() {{
            add(new KV("key1", "value1"));
        }});

        String signature = sdk._signBody(sig_method, session_key, JSON.object2json(body).toString());
        try {
            return JSON.object2string(sdk.apps__set_user_storage(
                    access_token,
                    openid,
                    signature,
                    sig_method,
                    body));
        } catch (ToutiaoError e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }

    @RequestMapping("/removeUserStorage")
    public String removeUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        apps__remove_user_storage_body body = new apps__remove_user_storage_body();
        body.setKey(new ArrayList<String>() {{
            add("key1");
        }});
        String signature = sdk._signBody(sig_method, session_key, JSON.object2json(body).toString());
        try {
            return JSON.object2string(sdk.apps__remove_user_storage(
                    access_token,
                    openid,
                    signature,
                    sig_method,
                    body));
        } catch (ToutiaoError e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }

    @RequestMapping("/createQRCode")
    public String createQRCode(
            @RequestParam String access_token
    ) throws Exception {
        apps__qrcode_body body = new apps__qrcode_body();
        body.setAccess_token(access_token);
        body.setAppname("toutiao");
        body.setPath(URLEncoder.encode("pages/index/index", "utf-8"));
        body.setWidth(500);
        body.setLine_color(new RGB(255, 0, 0));
        body.setBackground(new RGB(0, 255, 0));
        body.setSet_icon(true);
        try {
            return Base64.encodeBase64String(sdk.apps__qrcode(body));
        } catch (ToutiaoError e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }

    @RequestMapping("/checkContent")
    public String checkContent(
            @RequestParam String access_token,
            @RequestParam String content
    ) throws Exception {
        tags__text__antidirt_body.Task task = new tags__text__antidirt_body.Task();
        task.setContent(content);
        //
        tags__text__antidirt_body body = new tags__text__antidirt_body();
        body.setTasks(new ArrayList<tags__text__antidirt_body.Task>() {{
            add(task);
        }});
        try {
            return JSON.object2string(sdk2.tags__text__antidirt(access_token, body));
        } catch (ToutiaoError2 e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }

    @RequestMapping("/checkImage1")
    public String checkImage1(
            @RequestParam String access_token
    ) throws Exception {
        tags__image_body.Task task = new tags__image_body.Task();
        task.setImage("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        //
        tags__image_body body = new tags__image_body();
        body.setTasks(new ArrayList<tags__image_body.Task>() {{
            add(task);
        }});
        try {
            return JSON.object2string(sdk2.tags__image(access_token, body));
        } catch (ToutiaoError2 e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }
    @RequestMapping("/checkImage2")
    public String checkImage2(
            @RequestParam String access_token
    ) throws Exception {
        tags__image_body.Task task = new tags__image_body.Task();
        task.setImage_data("data:image/png;base64,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Ukk3/Mu1H72ejW7sb7GI9dOPhi+14IT/or+LPd6ixZ9f3xj77DM9ZgyQoeGWBjrYIObNu2HTO0vn0srbcygWx8l0bCLVjaqILRWBvINEkf3+lApKJtPph6r9VrvppOik9ead2l+6UXbSj3mHh3vXjwqHm51yoaT3qHjbgkF/JvmkKqt86NAY/+QTU+9dSKU7E+kzZ7aM6FLUu+nyehU5dwnCdGXCEq0MWrfEWYh+kevpR7mBS0ILDrdTYny3JKQOxSt3sLjYI9PIkMKVISoTQ2UmDaF4LsNI7nBSMIEcvAnkIB9GEwSz/zDD2T+ZR/rBhhXVKZ9k7SDc7JfP4hTjzA5dTtEhFAoJLvF24ri55sZbN5h91XqLT75AlZmu33f1Tbcv2l/OlpKSiqgSPkEvD+16ylW+y8c9H48Mhz/LxLWzfdiwYd2RPRNVRYyiM9ir11bu9TRWd0Q+0SufC3J553DkQqOhDrlHYOrMliNicfU6wcWldPMuTX8NuUrPZE10fV5B3cTveFT1PV7m/2DNK6MsRz6smy984rPgeHJfptDlfxY5JrPImTqK6pR2Z5VuMNrqgr9yLt/f1FC5LP0cUQIIgIBeAnA49JKCHAhkgMCcOW2l7T09l5EjMJ+Km21LTwH1mlGvw3pyZJqoB4OcDGk1K/WtzOTkSc0JYZ+2TKPeEG1p5CSyRYvJMJHqWU5/LW9Uv/epg+Y5r0968P3l1aaG0SwbAQUgAAJJCcDhSIoHiSCQPQKzZu0Y0hHvOpFuykdTb8FEuqFW0VDbcOqZ8NOJK1Gk6Dg5ED3UjdxNVu7gQuygCcVtNN+nhZ70m5kimj1e6dM1K6pasleL5CUfNXdzeaQ7Po4JtVYwqZZztZrqWkFDMyOoniOoh6SE/lJ0SqF1VWvDj9qbXnZT/Vtp90PB5QaJ+15tWlmBSK7JUSMVBEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAABEAgXwj8P1QjL/BchIZ8AAAAAElFTkSuQmCC");
        //
        tags__image_body body = new tags__image_body();
        body.setTasks(new ArrayList<tags__image_body.Task>() {{
            add(task);
        }});
        try {
            return JSON.object2string(sdk2.tags__image(access_token, body));
        } catch (ToutiaoError2 e) {
            System.out.println(JSON.object2json(e));
            return null;
        }
    }
}
