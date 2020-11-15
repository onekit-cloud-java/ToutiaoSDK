package com.toutiao.developer;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.ToutiaoAPI2;
import cn.onekit.thekit.AJAX;
import com.google.gson.JsonObject;
import com.toutiao.developer.entity.v2.*;

import java.util.HashMap;

public class ToutiaoSDK2 implements ToutiaoAPI2 {

    private final String host;
    public ToutiaoSDK2(String host){
        this.host=host;
    }
    public tags__text__antidirt_response tags__text__antidirt(String tt_X_Token, tags__text__antidirt_body tt_body) throws ToutiaoError {
        final JsonObject result;
        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("X-Token", tt_X_Token);
            }};
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
            String url = String.format("%s/api/v2/tags/text/antidirt",host);
            result = (JsonObject) JSON.parse(AJAX.request(url, "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, tags__text__antidirt_response.class);
    }

    
    public tags__image_response tags__image(String tt_X_Token, tags__image_body tt_body) throws ToutiaoError {
        final JsonObject result;
        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("X-Token", tt_X_Token);
            }};
            JsonObject post_body = (JsonObject) JSON.object2json(tt_body);
            String url = String.format("%s/api/v2/tags/image/",host);
            result = (JsonObject) JSON.parse(AJAX.request(url, "post", post_body.toString()));
        } catch (Exception e) {
            ToutiaoError errCode = new ToutiaoError();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, ToutiaoError.class);
        }
        return JSON.json2object(result, tags__image_response.class);
    }
}
