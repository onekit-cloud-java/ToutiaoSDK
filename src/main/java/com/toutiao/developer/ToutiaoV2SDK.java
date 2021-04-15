package com.toutiao.developer;

import cn.onekit.thekit.JSON;
import cn.onekit.thekit.AJAX;
import com.google.gson.JsonObject;
import com.toutiao.developer.v2.ToutiaoError2;
import com.toutiao.developer.v2.ToutiaoV2API;
import com.toutiao.developer.v2.request.ApiAppsCensorImageRequset;
import com.toutiao.developer.v2.request.ApiTagsTextAntidirtRequest;
import com.toutiao.developer.v2.response.ApiAppsCensorImageResponse;
import com.toutiao.developer.v2.response.ApiTagsTextAntidirtResponse;


import java.util.HashMap;

public class ToutiaoV2SDK implements ToutiaoV2API {

    private final String host;
    private final String appid;

    public ToutiaoV2SDK(String host, String appid){
        this.host=host;
        this.appid = appid;
    }

    @Override
    public ApiTagsTextAntidirtResponse apiTagsTextAntidirt(ApiTagsTextAntidirtRequest apiTagsTextAntidirtRequest) throws ToutiaoError2 {

        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("X-Token", apiTagsTextAntidirtRequest.getAccess_token());

            }};
            String url = String.format("%s/api/v2/tags/text/antidirt",host);
            JsonObject body = (JsonObject) JSON.object2json(apiTagsTextAntidirtRequest);
            body.remove("X-Token");
            String post = AJAX.request(url, "post", body.toString());
            JsonObject result = (JsonObject) JSON.parse(post);
            if (result.has("code")) {
                throw JSON.json2object(result, ToutiaoError2.class);
            }
            return JSON.json2object(result,ApiTagsTextAntidirtResponse.class);
        } catch (Exception e) {
            ToutiaoError2 errCode = new ToutiaoError2();
            errCode.setCode(9527);
            errCode.setMessage(e.getMessage());
            throw errCode;
        }

    }

    @Override
    public ApiAppsCensorImageResponse apiAppsCensorImage(ApiAppsCensorImageRequset apiAppsCensorImageRequset) throws ToutiaoError2 {

        try {
            AJAX.headers = new HashMap<String, String>() {{
                put("Content-Type", "application/json");

            }};
            apiAppsCensorImageRequset.setApp_id(appid);
            String url = String.format("%s/api/apps/censor/image",host);
            JsonObject body = (JsonObject) JSON.object2json(apiAppsCensorImageRequset);
            JsonObject result = (JsonObject) JSON.parse(AJAX.request(url, "post", body.toString()));
            if (result.get("error").getAsInt() != 0) {
                throw JSON.json2object(result, ToutiaoError2.class);
            }
            return JSON.json2object(result, ApiAppsCensorImageResponse.class);
        } catch (Exception e) {
            ToutiaoError2 errCode = new ToutiaoError2();
            errCode.setCode(9527);
            errCode.setMessage(e.getMessage());
            throw errCode;
        }


    }
}
