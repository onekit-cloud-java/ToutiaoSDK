package com.toutiao.developer;


import cn.onekit.thekit.JSON;
import cn.onekit.thekit.SIGN;


public class ToutiaoX extends ToutiaoSDK{

    public void checksession(String tt_access_token,String tt_openid,String tt_signature,String tt_sig_method,String tt_body) throws Exception {

     //   String tt_session_key =
       //  if(_signBody(tt_sig_method,tt_session_key,tt_body).equals();


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
