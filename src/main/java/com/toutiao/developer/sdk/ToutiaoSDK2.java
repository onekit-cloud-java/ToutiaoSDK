package com.toutiao.developer.sdk;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.ToutiaoAPI2;
import cn.onekit.thekit.AJAX;
import com.google.gson.JsonObject;
import com.toutiao.developer.entity.v2.*;

import java.util.HashMap;

public class ToutiaoSDK2 implements ToutiaoAPI2 {

    
    public tags$text$antidirt_response tags$text$antidirt(String X_Token, tags$text$antidirt_body body) throws ToutiaoError {
        final JsonObject result;
        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("X-Token", X_Token);
            }};
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/v2/tags/text/antidirt", "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, tags$text$antidirt_response.class);
    }

    
    public tags$image_response tags$image(String X_Token, tags$image_body body) throws ToutiaoError {
        final JsonObject result;
        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("X-Token", X_Token);
            }};
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/v2/tags/image/", "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, tags$image_response.class);
    }
}
