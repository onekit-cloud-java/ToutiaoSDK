package demo;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.entity.*;
import com.toutiao.developer.entity.v2.*;
import com.toutiao.developer.sdk.ToutiaoSDK;
import com.toutiao.developer.sdk.ToutiaoSDK2;
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

    @RequestMapping("/getAccessToken")
    public apps$token_response getAccessToken() throws Exception {
        return new ToutiaoSDK().apps$token(ToutiaoAccount.appid, ToutiaoAccount.secret, "client_credential");
    }

    @RequestMapping("/code2Session1")
    public apps$jscode2session_response code2Session1(
            @RequestParam String code) throws Exception {
        return new ToutiaoSDK().apps$jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, code, null);

    }

    @RequestMapping("/code2Session2")
    public apps$jscode2session_response code2Session2(
            @RequestParam String anonymous_code) throws Exception {
        return new ToutiaoSDK().apps$jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, null, anonymous_code);
    }


    @RequestMapping("/setUserStorage")
    public apps$set_user_storage_response setUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        ToutiaoSDK ToutiaoSDK = new ToutiaoSDK();
        apps$set_user_storage_body body = new apps$set_user_storage_body();
        body.setTt_kv_list(new ArrayList<KV>() {{
            add(new KV("key1", "value1"));
        }});

        String signature = ToutiaoSDK._crypto(sig_method, session_key, JSON.object2json(body).toString());
        return ToutiaoSDK.apps$set_user_storage(
                access_token,
                openid,
                signature,
                sig_method,
                body);
    }

    @RequestMapping("/removeUserStorage")
    public apps$remove_user_storage_response removeUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        ToutiaoSDK ToutiaoSDK = new ToutiaoSDK();
        apps$remove_user_storage_body body = new apps$remove_user_storage_body();
        body.setKey(new ArrayList<String>() {{
            add("key1");
        }});
        String signature = ToutiaoSDK._crypto(sig_method, session_key, JSON.object2json(body).toString());
        return ToutiaoSDK.apps$remove_user_storage(
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
        apps$qrcode_body body = new apps$qrcode_body();
        body.setAccess_token(access_token);
        body.setAppname("toutiao");
        body.setPath(URLEncoder.encode("pages/index/index", "utf-8"));
        body.setWidth(500);
        body.setLine_color(new RGB(255, 0, 0));
        body.setBackground(new RGB(0, 255, 0));
        body.setSet_icon(true);
        return Base64.encodeBase64String(new ToutiaoSDK().apps$qrcode(body));
    }

    @RequestMapping("/checkContent")
    public tags$text$antidirt_response checkContent(
            @RequestParam String access_token,
            @RequestParam String content
    ) throws Exception {
        tags$text$antidirt_body.Task task=new tags$text$antidirt_body.Task();
        task.setContent(content);
        //
        tags$text$antidirt_body body = new tags$text$antidirt_body();
        body.setTasks(new ArrayList<tags$text$antidirt_body.Task>(){{add(task);}});
        return new ToutiaoSDK2().tags$text$antidirt(access_token,body);
    }
    @RequestMapping("/checkImage")
    public tags$image_response checkImage(
            @RequestParam String access_token
    ) throws Exception {
        tags$image_body.Task task = new tags$image_body.Task();
        task.setImage("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        //
        tags$image_body body = new tags$image_body();
        body.setTasks(new ArrayList<tags$image_body.Task>(){{add(task);}});
        return new ToutiaoSDK2().tags$image(access_token,body);
    }
}
