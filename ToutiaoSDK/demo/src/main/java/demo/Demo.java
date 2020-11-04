package demo;

import com.toutiao.developer.Kvltem;
import com.toutiao.developer.RGB;
import com.toutiao.developer.ToutiaoAPI;
import com.toutiao.developer.sdk.ToutiaoSDK;
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
    public String getAccessToken() throws Exception {
        return new ToutiaoSDK().apps$token(ToutiaoAccount.appid, ToutiaoAccount.secret, "client_credential").toString();
    }

    @RequestMapping("/code2Session1")
    public String code2Session1(
            @RequestParam String code) throws Exception {
        return new ToutiaoSDK().apps$jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, code, null).toString();

    }

    @RequestMapping("/code2Session2")
    public String code2Session2(
            @RequestParam String anonymous_code) throws Exception {
        return new ToutiaoSDK().apps$jscode2session(ToutiaoAccount.appid, ToutiaoAccount.secret, null, anonymous_code).toString();
    }


    @RequestMapping("/setUserStorage")
    public String setUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        ToutiaoAPI.apps$set_user_storage_body body = new ToutiaoAPI.apps$set_user_storage_body();
        body.setTt_kv_list(new ArrayList<Kvltem>() {{
            add(new Kvltem("key1", "value1"));
        }});

        String signature = ToutiaoSDK.crypto(sig_method, session_key, body.toString());
        return new ToutiaoSDK().apps$set_user_storage(
                access_token,
                openid,
                signature,
                sig_method,
                body).toString();
    }

    @RequestMapping("/removeUserStorage")
    public String removeUserStorage(
            @RequestParam String session_key,
            @RequestParam String access_token,
            @RequestParam String openid
    ) throws Exception {
        ToutiaoAPI.apps$remove_user_storage_body body = new ToutiaoAPI.apps$remove_user_storage_body();
        body.setTt_kv_list(new ArrayList<String>() {{
            add("key1");
        }});
        String signature = ToutiaoSDK.crypto(sig_method, session_key, body.toString());
        return new ToutiaoSDK().apps$remove_user_storage(
                access_token,
                openid,
                signature,
                sig_method,
                body).toString();
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
}
