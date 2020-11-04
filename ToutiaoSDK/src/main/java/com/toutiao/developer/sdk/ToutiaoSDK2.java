package com.toutiao.developer.sdk;

import com.toutiao.developer.ToutiaoAPI2;
import cn.onekit.thekit.AJAX;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ToutiaoSDK2 extends ToutiaoAPI2{

    @Override
    public tags$text$antidirt_response tags$text$antidirt(String X_Token, ToutiaoAPI2.tags$text$antidirt_body body) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) new Gson().toJsonTree(body);
            result = (JsonObject) new JsonParser().parse(AJAX.request("https://developer.toutiao.com/api/v2/tags/text/antidirt", "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if(result.has("error")&& result.get("error").getAsInt()!=0) {
            throw new Gson().fromJson(result, errCode.class);
        }
        return  new Gson().fromJson(result, tags$text$antidirt_response.class);
    }

    @Override
    public tags$image_response tags$image(String X_Token, tags$image_body body) throws errCode {
        final JsonObject result;
        try {
            JsonObject post_body = (JsonObject) new Gson().toJsonTree(body);
            result = (JsonObject) new JsonParser().parse(AJAX.request("https://developer.toutiao.com/api/v2/tags/image/", "post", post_body.toString()));
        } catch (Exception e) {
            errCode errCode = new errCode();
            throw errCode;
        }
        if(result.has("error")&& result.get("error").getAsInt()!=0) {
            throw new Gson().fromJson(result, errCode.class);
        }
        return  new Gson().fromJson(result, tags$image_response.class);
    }
}
