package com.toutiao.developer.sdk;
import cn.onekit.thekit.AJAX;
import cn.onekit.thekit.Crypto;
import cn.onekit.thekit.JSON;
import cn.onekit.thekit.STRING;
import com.google.gson.JsonObject;
import com.toutiao.developer.ToutiaoAPI;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

public class ToutiaoSDK extends ToutiaoAPI {
    @Override
    public String crypto(String sig_method, String session_key, String data) throws Exception {
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

    @Override
    public apps$token_response apps$token(String appid, String secret, String grant_type) throws errCode {
        final JsonObject result;
        try {
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/apps/token", "get", new HashMap<String, String>() {{
                put("appid", appid);
                put("secret", secret);
                put("grant_type", grant_type);
            }}));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error")) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, apps$token_response.class);
    }

    @Override
    public apps$jscode2session_response apps$jscode2session(String appid, String secret, String code, String anonymous_code) throws errCode {
        final JsonObject result;
        try {
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
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/apps/jscode2session", "get", request));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, apps$jscode2session_response.class);
    }

    @Override
    public apps$set_user_storage_response apps$set_user_storage(String access_token, String openid, String signature, String sig_method, apps$set_user_storage_body body) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request(String.format("https://developer.toutiao.com/api/apps/set_user_storage?access_token=%s&openid=%s&sig_method=%s&signature=%s",
                    access_token,
                    openid,
                    sig_method,
                    signature
            ), "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, apps$set_user_storage_response.class);
    }

    @Override
    public apps$remove_user_storage_response apps$remove_user_storage(String access_token, String openid, String signature, String sig_method, apps$remove_user_storage_body body) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request(String.format("https://developer.toutiao.com/api/apps/remove_user_storage?access_token=%s&openid=%s&sig_method=%s&signature=%s",

                    access_token,
                    openid,
                    sig_method,
                    signature
            ), "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, apps$remove_user_storage_response.class);
    }

    @Override
    public byte[] apps$qrcode(apps$qrcode_body request) throws errCode {
        final byte[] bytes;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(request);
            bytes = AJAX.download("https://developer.toutiao.com/api/apps/qrcode", "post", post_body.toString());
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        try {
            JsonObject result = (JsonObject) JSON.parse(Base64.encodeBase64String(bytes));
            if (result.has("error") && result.get("error").getAsInt() != 0) {
                throw JSON.json2object(result, errCode.class);
            }
        } catch (Exception e) {

        }
        return bytes;
    }

    @Override
    public apps$game$template$send_response apps$game$template$send(apps$game$template$send_body request) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(request);
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/apps/game/template/send", "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, apps$game$template$send_response.class);
    }

    @Override
    public apps$subscribe_notification$developer$v1$notify_response apps$subscribe_notification$developer$v1$notify(apps$subscribe_notification$developer$v1$notify_body request) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(request);
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/apps/game/template/send", "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, apps$subscribe_notification$developer$v1$notify_response.class);
    }
}
