package com.toutiao.developer.sdk;
import cn.onekit.thekit.AJAX;
import cn.onekit.thekit.Crypto;
import cn.onekit.thekit.JSON;
import cn.onekit.thekit.STRING;
import com.google.gson.JsonObject;
import com.toutiao.developer.ToutiaoAPI;
import com.toutiao.developer.entity.*;
import org.apache.commons.codec.binary.Base64;
import java.util.HashMap;
import java.util.Map;

public class ToutiaoSDK implements ToutiaoAPI {
    
    public String _crypto(String sig_method, String session_key, String data) throws Exception {
        Crypto.Method method;
        switch (sig_method) {
            case "hmac_sha256":
                method = Crypto.Method.HMACSHA256;
                break;
            default:
                throw new Exception(sig_method);
        }
        return new Crypto(method).encode(session_key, data);
    }

    
    public apps$token_response apps$token(String appid, String secret, String grant_type) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = "https://developer.toutiao.com/api/apps/token";
            result = (JsonObject) JSON.parse(AJAX.request(url, "get", new HashMap<String, String>() {{
                put("appid", appid);
                put("secret", secret);
                put("grant_type", grant_type);
            }}));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error")) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps$token_response.class);
    }

    
    public apps$jscode2session_response apps$jscode2session(String appid, String secret, String code, String anonymous_code) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = "https://developer.toutiao.com/api/apps/jscode2session";
            Map<String, String> request = new HashMap<String, String>() {{
                put("appid", appid);
                put("secret", secret);
                if (!STRING.isEmpty(code)) {
                    put("code", code);
                }
                if (!STRING.isEmpty(anonymous_code)) {
                    put("anonymous_code", anonymous_code);
                }
            }};
            result = (JsonObject) JSON.parse(AJAX.request(url, "get", request));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps$jscode2session_response.class);
    }

    
    public apps$set_user_storage_response apps$set_user_storage(String access_token, String openid, String signature, String sig_method, apps$set_user_storage_body body) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("https://developer.toutiao.com/api/apps/set_user_storage?access_token=%s&openid=%s&sig_method=%s&signature=%s",
                    access_token,
                    openid,
                    sig_method,
                    signature
            );
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request(url, "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps$set_user_storage_response.class);
    }

    
    public apps$remove_user_storage_response apps$remove_user_storage(String access_token, String openid, String signature, String sig_method, apps$remove_user_storage_body body) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("https://developer.toutiao.com/api/apps/remove_user_storage?access_token=%s&openid=%s&sig_method=%s&signature=%s",
                    access_token,
                    openid,
                    sig_method,
                    signature);
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request(url, "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps$remove_user_storage_response.class);
    }

    
    public byte[] apps$qrcode(apps$qrcode_body request) throws ToutiaoError {
        final byte[] bytes;
        try {
            String url = "https://developer.toutiao.com/api/apps/qrcode";
            JsonObject post_body = (JsonObject) JSON.object2json(request);
            bytes = AJAX.download(url, "post", post_body.toString());
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        try {
            JsonObject result = (JsonObject) JSON.parse(Base64.encodeBase64String(bytes));
            if (result.has("error") && result.get("error").getAsInt() != 0) {
                throw JSON.json2object(result, ToutiaoError.class);
            }
        } catch (Exception e) {

        }
        return bytes;
    }

    
    public apps$game$template$send_response apps$game$template$send(apps$game$template$send_body request) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = "https://developer.toutiao.com/api/apps/game/template/send";
            JsonObject post_body = (JsonObject) JSON.object2json(request);
            result = (JsonObject) JSON.parse(AJAX.request(url, "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps$game$template$send_response.class);
    }

    
    public apps$subscribe_notification$developer$v1$notify_response apps$subscribe_notification$developer$v1$notify(apps$subscribe_notification$developer$v1$notify_body request) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = "https://developer.toutiao.com/api/apps/game/template/send";
            JsonObject post_body = (JsonObject) JSON.object2json(request);
            result = (JsonObject) JSON.parse(AJAX.request(url, "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps$subscribe_notification$developer$v1$notify_response.class);
    }
}
