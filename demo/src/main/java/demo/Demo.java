package demo;


import cn.onekit.thekit.FileDB;
import com.toutiao.developer.ToutiaoV1SDK;
import com.toutiao.developer.ToutiaoV2SDK;
import com.toutiao.developer.v1.ToutiaoError;
import com.toutiao.developer.v1.request.*;
import com.toutiao.developer.v1.response.ApiAppsJscode2sessionResponse;
import com.toutiao.developer.v1.response.ApiAppsSubscribeNotificationDeveloperNotifyResponse;
import com.toutiao.developer.v1.response.ApiAppsTokenResponse;
import com.toutiao.developer.v2.ToutiaoError2;
import com.toutiao.developer.v2.request.ApiAppsCensorImageRequset;
import com.toutiao.developer.v2.request.ApiTagsTextAntidirtRequest;
import com.toutiao.developer.v2.response.ApiAppsCensorImageResponse;
import com.toutiao.developer.v2.response.ApiTagsTextAntidirtResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/")

public class Demo {

    private ToutiaoV1SDK sdk = new ToutiaoV1SDK("https://developer.toutiao.com",ToutiaoAccount.appid,ToutiaoAccount.secret);
    private ToutiaoV2SDK sdk2 = new ToutiaoV2SDK("https://developer.toutiao.com",ToutiaoAccount.appid);



    @RequestMapping("/getAccessToken")
    public ApiAppsTokenResponse getAccessToken(@RequestBody ApiAppsTokenRequset apiAppsTokenRequset) throws ToutiaoError, IOException {
        ApiAppsTokenResponse response = sdk.apiAppsToken(apiAppsTokenRequset);
        FileDB fileDB = new FileDB("toutiao");
        fileDB.set("getAccessToken","token",response.getAccess_token());
        return response;

    }

    @RequestMapping("/code2Session")
    public ApiAppsJscode2sessionResponse code2Session1(@RequestBody ApiAppsJscode2sessionRequset apiAppsJscode2sessionRequset) throws ToutiaoError, IOException {
        ApiAppsJscode2sessionResponse response = sdk.apiAppsJscode2session(apiAppsJscode2sessionRequset);
        FileDB fileDB = new FileDB("toutiao");
        fileDB.set("openid","openid",response.getOpenid());
        fileDB.set("session_key","session_key",response.getSession_key());
        return response;

    }


    @RequestMapping("/setUserStorage")
    public String setUserStorage(@RequestBody ApiAppsSetUserStorageRequest apiAppsSetUserStorageRequest) throws ToutiaoError {
        FileDB fileDB = new FileDB("toutiao");
        String accessstoken = fileDB.get("getAccessToken", "token").value;
        String openid = fileDB.get("openid", "openid").value;
        String session_key = fileDB.get("session_key", "session_key").value;

        apiAppsSetUserStorageRequest.setAccess_token(accessstoken);
        apiAppsSetUserStorageRequest.setOpenid(openid);
        apiAppsSetUserStorageRequest.setSession_key(session_key);

        sdk.apiAppsSetUserStorage(apiAppsSetUserStorageRequest);
        return "success";

    }

    @RequestMapping("/removeUserStorage")
    public String removeUserStorage(@RequestBody ApiAppsRemoveUserStorageRequest apiAppsRemoveUserStorageRequest ) throws ToutiaoError {
        FileDB fileDB = new FileDB("toutiao");
        String accessstoken = fileDB.get("getAccessToken", "token").value;
        String openid = fileDB.get("openid", "openid").value;
        String session_key = fileDB.get("session_key", "session_key").value;

        apiAppsRemoveUserStorageRequest.setAccess_token(accessstoken);
        apiAppsRemoveUserStorageRequest.setOpenid(openid);
        apiAppsRemoveUserStorageRequest.setSession_key(session_key);

        sdk.apiAppsRemoveUserStorage(apiAppsRemoveUserStorageRequest);
        return "success";
    }

    @RequestMapping("/createQRCode")
    public void createQRCode(HttpServletResponse response,@RequestBody ApiAppsQrcodeRequest apiAppsQrcodeRequest) throws ToutiaoError, IOException {
        FileDB fileDB = new FileDB("toutiao");
        String accessstoken = fileDB.get("getAccessToken", "token").value;
        apiAppsQrcodeRequest.setAccess_token(accessstoken);
        byte[] bytes = sdk.apiAppsQrcode(apiAppsQrcodeRequest);
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/checkContent")
    public ApiTagsTextAntidirtResponse checkContent(@RequestBody ApiTagsTextAntidirtRequest apiTagsTextAntidirtRequest) throws ToutiaoError2 {
        FileDB fileDB = new FileDB("toutiao");
        String accessstoken = fileDB.get("getAccessToken", "token").value;
        apiTagsTextAntidirtRequest.setAccess_token(accessstoken);
        return sdk2.apiTagsTextAntidirt(apiTagsTextAntidirtRequest);
    }

    @RequestMapping("/checkImage")
    public ApiAppsCensorImageResponse checkImage(@RequestBody ApiAppsCensorImageRequset apiAppsCensorImageRequset) throws ToutiaoError2 {
        FileDB fileDB = new FileDB("toutiao");
        String accessstoken = fileDB.get("getAccessToken", "token").value;
        apiAppsCensorImageRequset.setAccess_token(accessstoken);
        return sdk2.apiAppsCensorImage(apiAppsCensorImageRequset);
    }

    @RequestMapping("/sendsubscribmessage")
    public ApiAppsSubscribeNotificationDeveloperNotifyResponse sendsubscribmessage(@RequestBody ApiAppsSubscribeNotificationDeveloperNotifyRequest       apiAppsSubscribeNotificationDeveloperNotifyRequest) throws ToutiaoError2, ToutiaoError {
        FileDB fileDB = new FileDB("toutiao");
        String accessstoken = fileDB.get("getAccessToken", "token").value;
        String openid = fileDB.get("openid", "openid").value;
        apiAppsSubscribeNotificationDeveloperNotifyRequest.setAccess_token(accessstoken);
        apiAppsSubscribeNotificationDeveloperNotifyRequest.setOpen_id(openid);
        return sdk.apiappsSubscribeNotificationDeveloperNotify(apiAppsSubscribeNotificationDeveloperNotifyRequest);
    }

}
