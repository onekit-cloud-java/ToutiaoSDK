package com.toutiao.developer;

import cn.onekit.thekit.AJAX;
import cn.onekit.thekit.CRYPTO;
import cn.onekit.thekit.JSON;
import cn.onekit.thekit.STRING;
import com.google.gson.JsonObject;
import com.toutiao.developer.entity.*;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;

public class ToutiaoSDK implements ToutiaoAPI {

    private final String host;
    public ToutiaoSDK(String host){
        this.host=host;
    }

    public String _decrypt(String tt_encryptedData,String tt_iv,String tt_session_key) throws Exception {
        return new CRYPTO(CRYPTO.Key.AES, CRYPTO.Mode.PKCS5,128).decrypt(tt_encryptedData,tt_iv,tt_session_key);
    }
    public apps__token_response apps__token(String tt_appid, String tt_secret, String tt_grant_type) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("%s/api/apps/token",host);
            result = (JsonObject) JSON.parse(AJAX.request(url, "get", new HashMap<String, String>() {{
                put("tt_appid", tt_appid);
                put("tt_secret", tt_secret);
                put("tt_grant_type", tt_grant_type);
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

    
    public apps__jscode2session_response apps__jscode2session(String tt_appid, String tt_secret, String tt_code, String tt_anonymous_code) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("%s/api/apps/jscode2session",host);
            HashMap<String, String> tt_body = new HashMap<String, String>() {{
                put("tt_appid", tt_appid);
                put("tt_secret", tt_secret);
                if (!STRING.isEmpty(tt_code)) {
                    put("tt_code", tt_code);
                }
                if (!STRING.isEmpty(tt_anonymous_code)) {
                    put("tt_anonymous_code", tt_anonymous_code);
                }
            }};
            result = (JsonObject) JSON.parse(AJAX.request(url, "get", tt_body));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, apps__jscode2session_response.class);
    }

    
    public apps__set_user_storage_response apps__set_user_storage(String tt_access_token, String tt_openid, String tt_signature, String tt_sig_method, apps__set_user_storage_body tt_body) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("%s/api/apps/set_user_storage?tt_access_token=%s&tt_openid=%s&tt_sig_method=%s&tt_signature=%s",
                    host,
                    tt_access_token,
                    tt_openid,
                    tt_sig_method,
                    tt_signature
            );
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
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

    
    public apps__remove_user_storage_response apps__remove_user_storage(String tt_access_token, String tt_openid, String tt_signature, String tt_sig_method, apps__remove_user_storage_body tt_body) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("%s/api/apps/remove_user_storage?tt_access_token=%s&tt_openid=%s&tt_sig_method=%s&tt_signature=%s",
                    host,
                    tt_access_token,
                    tt_openid,
                    tt_sig_method,
                    tt_signature);
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
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

    
    public byte[] apps__qrcode(apps__qrcode_body tt_body) throws ToutiaoError {
        final byte[] bytes;
        try {
            String url = String.format("%s/api/apps/qrcode",host);
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
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

    
    public apps__game__template__send_response apps__game__template__send(apps__game__template__send_body tt_body) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("%s/api/apps/game/template/send",host);
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
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

    
    public apps__subscribe_notification__developer__notify_response apps__subscribe_notification__developer__notify(apps__subscribe_notification__developer__notify_body tt_body) throws ToutiaoError {
        final JsonObject result;
        try {
            String url = String.format("%s/api/apps/game/template/send",host);
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
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
