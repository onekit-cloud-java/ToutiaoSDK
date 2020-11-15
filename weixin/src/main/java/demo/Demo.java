package demo;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.entity.*;
import com.toutiao.developer.entity.v2.tags__image_body;
import com.toutiao.developer.entity.v2.tags__image_response;
import com.toutiao.developer.entity.v2.tags__text__antidirt_body;
import com.toutiao.developer.entity.v2.tags__text__antidirt_response;
import com.toutiao.developer.ToutiaoSDK;
import com.toutiao.developer.ToutiaoSDK2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.ArrayList;

@RestController
@RequestMapping("/toutiao_demo")

public class Demo {
    final String sig_method = "hmac_sha256";
     ToutiaoSDK sdk = new ToutiaoSDK("http://localhost/toutiao");
    ToutiaoSDK2 sdk2 = new ToutiaoSDK2("http://localhost/toutiao");
    @RequestMapping("/decrypt")
    public String decrypt(
            @RequestParam String session_key,
            @RequestParam String iv,
            @RequestParam String encryptedData,
            @RequestParam String rawData,
            @RequestParam String signature
    ) throws Exception {
        if(!sdk._signRaw(rawData,session_key).equals(signature)){
           throw new Exception("bad sign!!");
        }
        return sdk._decrypt(encryptedData,iv,session_key);
    }
    @RequestMapping("/getAccessToken")
    public apps__token_response getAccessToken() throws Exception {
        return sdk.apps__token(ToutiaoAccount.appid, ToutiaoAccount.secret, "client_credential");
    }

    @RequestMapping("/code2Session1")
    public apps__jscode2session_response code2Session1(
            @RequestParam String code) throws Exception {
        return sdk.apps__jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, code, null);

    }

    @RequestMapping("/code2Session2")
    public apps__jscode2session_response code2Session2(
            @RequestParam String anonymous_code) throws Exception {
        return sdk.apps__jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, null, anonymous_code);
    }


    @RequestMapping("/setUserStorage")
    public apps__set_user_storage_response setUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        apps__set_user_storage_body body = new apps__set_user_storage_body();
        body.setTt_kv_list(new ArrayList<KV>() {{
            add(new KV("key1", "value1"));
        }});

        String signature = sdk._signBody(sig_method, session_key, JSON.object2json(body).toString());
        return sdk.apps__set_user_storage(
                access_token,
                openid,
                signature,
                sig_method,
                body);
    }

    @RequestMapping("/removeUserStorage")
    public apps__remove_user_storage_response removeUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        apps__remove_user_storage_body body = new apps__remove_user_storage_body();
        body.setKey(new ArrayList<String>() {{
            add("key1");
        }});
        String signature = sdk._signBody(sig_method, session_key, JSON.object2json(body).toString());
        return sdk.apps__remove_user_storage(
                access_token,
                openid,
                signature,
                sig_method,
                body);
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
        return Base64.encodeBase64String(sdk.apps__qrcode(body));
    }

    @RequestMapping("/checkContent")
    public tags__text__antidirt_response checkContent(
            @RequestParam String access_token,
            @RequestParam String content
    ) throws Exception {
        tags__text__antidirt_body.Task task=new tags__text__antidirt_body.Task();
        task.setContent(content);
        //
        tags__text__antidirt_body body = new tags__text__antidirt_body();
        body.setTasks(new ArrayList<tags__text__antidirt_body.Task>(){{add(task);}});
        return sdk2.tags__text__antidirt(access_token,body);
    }
    @RequestMapping("/checkImage")
    public tags__image_response checkImage(
            @RequestParam String access_token
    ) throws Exception {
        tags__image_body.Task task = new tags__image_body.Task();
        task.setImage("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        //
        tags__image_body body = new tags__image_body();
        body.setTasks(new ArrayList<tags__image_body.Task>(){{add(task);}});
        return sdk2.tags__image(access_token,body);
    }
}
