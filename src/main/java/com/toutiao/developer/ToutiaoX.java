package com.toutiao.developer;


import cn.onekit.thekit.JSON;
import cn.onekit.thekit.SIGN;


public class ToutiaoX {

    public ToutiaoResponse checksession(String tt_session_key,String tt_sig_method,String tt_body) throws Exception {

         return JSON.string2object(_signBody(tt_sig_method,tt_session_key,tt_body),ToutiaoResponse.class);


    }

    public String _signBody(String tt_sig_method, String tt_session_key, String tt_body) throws Exception {
        SIGN.Method method;
        if ("hmac_sha256".equals(tt_sig_method)) {
            method = SIGN.Method.HMACSHA256;
        } else {
            throw new Exception(tt_sig_method);
        }
        return new SIGN(method).sign(tt_session_key, tt_body);
    }




}
