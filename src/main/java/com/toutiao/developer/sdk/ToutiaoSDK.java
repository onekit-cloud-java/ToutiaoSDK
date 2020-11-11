package com.toutiao.developer.sdk;
import cn.onekit.thekit.*;
import com.google.gson.JsonObject;
import com.toutiao.developer.ToutiaoAPI;
import com.toutiao.developer.entity.*;
import org.apache.commons.codec.binary.Base64;
import java.util.HashMap;

public class ToutiaoSDK implements ToutiaoAPI {

    public String _sign(String sig_method, String session_key, String data) throws Exception
    {
        SIGN.Method method;
        switch (sig_method) {
            case "hmac_sha256":
                method = SIGN.Method.HMACSHA256;
                break;
            default:
                throw new Exception(sig_method);
        }
        return new SIGN(method).sign(session_key, data);
    }
    public String _sign( String rawData,String session_key) throws Exception{
        return new SIGN(SIGN.Method.SHA1).sign(rawData+session_key);
    }
    public apps__token_response apps__token(String appid, String secret, String grant_type) throws ToutiaoError {
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
        return JSON.json2object(result, apps__token_response.class);
    }

    
    public apps__jscode2session_response apps__jscode2session(String appid, String secret, String code, String anonymous_code) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = "https://developer.toutiao.com/api/apps/jscode2session";
            HashMap<String, String> request = new HashMap<String, String>() {{
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
        return JSON.json2object(result, apps__jscode2session_response.class);
    }

    
    public apps__set_user_storage_response apps__set_user_storage(String access_token, String openid, String signature, String sig_method, apps__set_user_storage_body body) throws ToutiaoError {
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
        return JSON.json2object(result, apps__set_user_storage_response.class);
    }

    
    public apps__remove_user_storage_response apps__remove_user_storage(String access_token, String openid, String signature, String sig_method, apps__remove_user_storage_body body) throws ToutiaoError {
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
        return JSON.json2object(result, apps__remove_user_storage_response.class);
    }

    
    public byte[] apps__qrcode(apps__qrcode_body request) throws ToutiaoError {
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

    
    public apps__game__template__send_response apps__game__template__send(apps__game__template__send_body request) throws ToutiaoError {
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
        return JSON.json2object(result, apps__game__template__send_response.class);
    }

    
    public apps__subscribe_notification__developer__notify_response apps__subscribe_notification__developer__notify(apps__subscribe_notification__developer__notify_body request) throws ToutiaoError {
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
        return JSON.json2object(result, apps__subscribe_notification__developer__notify_response.class);
    }
}
