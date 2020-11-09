package demo;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.Kv;
import com.toutiao.developer.RGB;
import com.toutiao.developer.ToutiaoAPI;
import com.toutiao.developer.ToutiaoAPI2;
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
    public ToutiaoAPI.apps$token_response getAccessToken() throws Exception {
        return new ToutiaoSDK().apps$token(ToutiaoAccount.appid, ToutiaoAccount.secret, "client_credential");
    }

    @RequestMapping("/code2Session1")
    public ToutiaoAPI.apps$jscode2session_response code2Session1(
            @RequestParam String code) throws Exception {
        return new ToutiaoSDK().apps$jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, code, null);

    }

    @RequestMapping("/code2Session2")
    public ToutiaoAPI.apps$jscode2session_response code2Session2(
            @RequestParam String anonymous_code) throws Exception {
        return new ToutiaoSDK().apps$jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, null, anonymous_code);
    }


    @RequestMapping("/setUserStorage")
    public ToutiaoAPI.apps$set_user_storage_response setUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        ToutiaoSDK ToutiaoSDK = new ToutiaoSDK();
        ToutiaoAPI.apps$set_user_storage_body body = new ToutiaoAPI.apps$set_user_storage_body();
        body.setTt_kv_list(new ArrayList<Kv>() {{
            add(new Kv("key1", "value1"));
        }});

        String signature = ToutiaoSDK.crypto(sig_method, session_key, JSON.object2json(body).toString());
        return ToutiaoSDK.apps$set_user_storage(
                access_token,
                openid,
                signature,
                sig_method,
                body);
    }

    @RequestMapping("/removeUserStorage")
    public ToutiaoAPI.apps$remove_user_storage_response removeUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        ToutiaoSDK ToutiaoSDK = new ToutiaoSDK();
        ToutiaoAPI.apps$remove_user_storage_body body = new ToutiaoAPI.apps$remove_user_storage_body();
        body.setKey(new ArrayList<String>() {{
            add("key1");
        }});
        String signature = ToutiaoSDK.crypto(sig_method, session_key, JSON.object2json(body).toString());
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
        ToutiaoAPI.apps$qrcode_body body = new ToutiaoAPI.apps$qrcode_body();
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
    public ToutiaoAPI2.tags$text$antidirt_response checkContent(
            @RequestParam String access_token,
            @RequestParam String content
    ) throws Exception {
        ToutiaoAPI2.tags$text$antidirt_body.Task task=new ToutiaoAPI2.tags$text$antidirt_body.Task();
        task.setContent(content);
        //
        ToutiaoAPI2.tags$text$antidirt_body body = new ToutiaoAPI2.tags$text$antidirt_body();
        body.setTasks(new ArrayList<ToutiaoAPI2.tags$text$antidirt_body.Task>(){{add(task);}});
        return new ToutiaoSDK2().tags$text$antidirt(access_token,body);
    }
    @RequestMapping("/checkImage")
    public ToutiaoAPI2.tags$image_response checkImage(
            @RequestParam String access_token
    ) throws Exception {
        ToutiaoAPI2.tags$image_body.Task task = new ToutiaoAPI2.tags$image_body.Task();
        task.setImage("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        //
        ToutiaoAPI2.tags$image_body body = new ToutiaoAPI2.tags$image_body();
        body.setTasks(new ArrayList<ToutiaoAPI2.tags$image_body.Task>(){{add(task);}});
        return new ToutiaoSDK2().tags$image(access_token,body);
    }
}
