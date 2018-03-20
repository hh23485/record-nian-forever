package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.User;
import cn.hhchat.record.util.HttpUtil;
import cn.hhchat.record.util.ImgUtil;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
@Data
public class LoginHelper {

    String cookie = null;

    /**
     * 登陆
     */
    public Boolean login(OkHttpClient client, String email, String password) {
        int count = 0;
        Boolean loginSuccess;
        do {
            loginSuccess = submitLogin(client, email, password);
            if (loginSuccess == null) {
                return null;
            }
        } while (!loginSuccess && count++ < 10);
        return loginSuccess;
    }


    /**
     * 提交登陆
     */
    private Boolean submitLogin(OkHttpClient client, String email, String password) {

        String loginPwd = SecureUtil.md5("n*A" + password);

        RequestBody requestBody = new FormBody.Builder().add("email", email).add("password", loginPwd).build();
        Request request = new Request.Builder().url(ApiHelper.getLoginUrl()).post(requestBody).build();

        Response response;
        try {
            response = client.newCall(request).execute();
            JSONObject jsonBody = HttpUtil.isOk(response);
            if (jsonBody != null && jsonBody.getInteger("error") == 0) {
                JSONObject jsonData = jsonBody.getJSONObject("data");
                log.info(" <= 登陆成功: {}", jsonData);
                Config.UID = jsonData.getString("uid");
                Config.SHELL = jsonData.getString("shell");
                Config.NAME = jsonData.getString("name");
                return true;
            } else if (jsonBody != null && jsonBody.getInteger("status") == 401) {
                log.error("账号或密码错误");
                return null;
            }
        } catch (IOException e) {
            log.error(" => 提交登陆失败", e.getMessage());
        }
        return false;
    }

}
