package demo;

import com.google.gson.Gson;
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
        ToutiaoAPI.apps$set_user_storage_body body = new ToutiaoAPI.apps$set_user_storage_body();
        body.setTt_kv_list(new ArrayList<Kvltem>() {{
            add(new Kvltem("key1", "value1"));
        }});

        String signature = ToutiaoSDK.crypto(sig_method, session_key, new Gson().toJson(body));
        return new ToutiaoSDK().apps$set_user_storage(
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
        ToutiaoAPI.apps$remove_user_storage_body body = new ToutiaoAPI.apps$remove_user_storage_body();
        body.setKey(new ArrayList<String>() {{
            add("key1");
        }});
        String signature = ToutiaoSDK.crypto(sig_method, session_key, new Gson().toJson(body));
        return new ToutiaoSDK().apps$remove_user_storage(
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
}
