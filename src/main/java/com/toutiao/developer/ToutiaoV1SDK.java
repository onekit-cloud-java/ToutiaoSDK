package com.toutiao.developer;

import cn.onekit.thekit.AJAX;
import cn.onekit.thekit.JSON;

import com.google.gson.JsonObject;
import com.toutiao.developer.v1.ToutiaoError;
import com.toutiao.developer.v1.ToutiaoV1API;
import com.toutiao.developer.v1.request.*;
import com.toutiao.developer.v1.response.ApiAppsJscode2sessionResponse;
import com.toutiao.developer.v1.response.ApiAppsSubscribeNotificationDeveloperNotifyResponse;
import com.toutiao.developer.v1.response.ApiAppsTokenResponse;

import java.util.HashMap;


public class ToutiaoV1SDK implements ToutiaoV1API {

    private final String host;
    private final String appid;
    private final String secret;

    public ToutiaoV1SDK(String host, String appid, String secret){
        this.host=host;
        this.appid=appid;
        this.secret=secret;

    }

    public ApiAppsTokenResponse apiAppsToken(ApiAppsTokenRequset apiAppsTokenRequset) throws ToutiaoError {
        try {
            String url = String.format("%s/api/apps/token",host);
            JsonObject  result = (JsonObject) JSON.parse(AJAX.request(url, "get", new HashMap<String, String>() {{
                put("appid", appid);
                put("secret", secret);
                put("grant_type",apiAppsTokenRequset.getGrant_type());
            }}));
            if (result.get("errcode")!= null) {
                throw JSON.json2object(result, ToutiaoError.class);
            }
            return JSON.json2object(result, ApiAppsTokenResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            ToutiaoError errCode = new ToutiaoError();
            errCode.setError(9527);
            errCode.setErrcode(9527);
            throw errCode;
        }

    }

    public ApiAppsJscode2sessionResponse apiAppsJscode2session(ApiAppsJscode2sessionRequset apiAppsJscode2sessionRequset) throws ToutiaoError {
        try {
            String url = String.format("%s/api/apps/jscode2session",host);
            JsonObject  result;
            if(apiAppsJscode2sessionRequset.getCode()!=null){
                result= (JsonObject) JSON.parse(AJAX.request(url, "get", new HashMap<String, String>() {{
                    put("appid", appid);
                    put("secret", secret);
                    put("code",apiAppsJscode2sessionRequset.getCode());
                }}));
            } else {
                result= (JsonObject) JSON.parse(AJAX.request(url, "get", new HashMap<String, String>() {{
                    put("appid", appid);
                    put("secret", secret);
                    put("anonymous_code",apiAppsJscode2sessionRequset.getAnonymous_code());
                }}));
            }
            if (result.get("error").getAsInt()!= 0) {
                throw JSON.json2object(result, ToutiaoError.class);
            }
            return JSON.json2object(result, ApiAppsJscode2sessionResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            ToutiaoError errCode = new ToutiaoError();
            errCode.setError(9527);
            errCode.setErrcode(9527);
            throw errCode;
        }
    }

    public void apiAppsSetUserStorage(ApiAppsSetUserStorageRequest apiAppsSetUserStorageRequest) throws ToutiaoError {
        try {
            JsonObject body = (JsonObject)JSON.object2json(apiAppsSetUserStorageRequest);
            body.remove("session_key");
            body.remove("access_token");
            body.remove("openid");
            body.remove("signature");
            body.remove("sig_method");

            String sinature = sign(apiAppsSetUserStorageRequest.getSig_method(), apiAppsSetUserStorageRequest.getSession_key(), body.toString());
            String url = String.format("%s/api/apps/set_user_storage?access_token=%s&openid=%s&signature=%s&sig_method=%s",host,
                    apiAppsSetUserStorageRequest.getAccess_token(),
                    apiAppsSetUserStorageRequest.getOpenid(),
                    sinature,
                    apiAppsSetUserStorageRequest.getSig_method());


            JsonObject  result = (JsonObject) JSON.parse(AJAX.request(url, "post",body.toString()));
            if (result.get("error").getAsInt()!= 0) {
                throw JSON.json2object(result, ToutiaoError.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToutiaoError errCode = new ToutiaoError();
            errCode.setError(9527);
            errCode.setErrcode(9527);
            throw errCode;
        }
    }

    public void apiAppsRemoveUserStorage(ApiAppsRemoveUserStorageRequest apiAppsRemoveUserStorageRequest) throws ToutiaoError {
        try {
            JsonObject body = (JsonObject)JSON.object2json(apiAppsRemoveUserStorageRequest);
            body.remove("session_key");
            body.remove("access_token");
            body.remove("openid");
            body.remove("signature");
            body.remove("sig_method");
            String sinature = sign(apiAppsRemoveUserStorageRequest.getSig_method(), apiAppsRemoveUserStorageRequest.getSession_key(),body.toString());
            String url = String.format("%s/api/apps/remove_user_storage?access_token=%s&openid=%s&signature=%s&sig_method=%s",host,
                    apiAppsRemoveUserStorageRequest.getAccess_token(),
                    apiAppsRemoveUserStorageRequest.getOpenid(),
                    sinature,
                    apiAppsRemoveUserStorageRequest.getSig_method());
            JsonObject  result = (JsonObject) JSON.parse(AJAX.request(url, "post",body.toString()));
            if (result.get("error").getAsInt()!= 0) {
                throw JSON.json2object(result, ToutiaoError.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToutiaoError errCode = new ToutiaoError();
            errCode.setError(9527);
            errCode.setErrcode(9527);
            throw errCode;
        }
    }

    public byte[] apiAppsQrcode(ApiAppsQrcodeRequest apiAppsQrcodeRequest) throws ToutiaoError {
        try {
            String url = String.format("%s/api/apps/qrcode",host);
            JsonObject body = (JsonObject)JSON.object2json(apiAppsQrcodeRequest);
            return AJAX.download(url, "post", body.toString());

        } catch (Exception e) {
            e.printStackTrace();
            ToutiaoError errCode = new ToutiaoError();
            errCode.setError(9527);
            errCode.setErrcode(9527);
            throw errCode;
        }
    }

    public ApiAppsSubscribeNotificationDeveloperNotifyResponse apiappsSubscribeNotificationDeveloperNotify(ApiAppsSubscribeNotificationDeveloperNotifyRequest apiAppsSubscribeNotificationDeveloperNotifyRequest) throws ToutiaoError {
        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("Content-Type", "application/json");

            }};
            apiAppsSubscribeNotificationDeveloperNotifyRequest.setApp_id(appid);
            String url = String.format("%s/api/apps/subscribe_notification/developer/v1/notify",host);
            JsonObject body = (JsonObject)JSON.object2json(apiAppsSubscribeNotificationDeveloperNotifyRequest);
            JsonObject  result = (JsonObject) JSON.parse(AJAX.request(url, "post",body.toString()));
            if (result.get("err_no").getAsInt()!= 0) {
                throw JSON.json2object(result, ToutiaoError.class);
            }
            return JSON.json2object(result, ApiAppsSubscribeNotificationDeveloperNotifyResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            ToutiaoError errCode = new ToutiaoError();
            errCode.setError(9527);
            errCode.setErrcode(9527);
            throw errCode;
        }
    }
}
