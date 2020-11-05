package com.toutiao.developer.sdk;

import cn.onekit.thekit.JSON;
import com.toutiao.developer.ToutiaoAPI2;
import cn.onekit.thekit.AJAX;
import com.google.gson.JsonObject;

public class ToutiaoSDK2 extends ToutiaoAPI2 {

    @Override
    public tags$text$antidirt_response tags$text$antidirt(String X_Token, ToutiaoAPI2.tags$text$antidirt_body body) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/v2/tags/text/antidirt", "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, tags$text$antidirt_response.class);
    }

    @Override
    public tags$image_response tags$image(String X_Token, tags$image_body body) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) JSON.object2json(body);
            result = (JsonObject) JSON.parse(AJAX.request("https://developer.toutiao.com/api/v2/tags/image/", "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if (result.has("error") && result.get("error").getAsInt() != 0) {
            throw JSON.json2object(result, errCode.class);
        }
        return JSON.json2object(result, tags$image_response.class);
    }
}
