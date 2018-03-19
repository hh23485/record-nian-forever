package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.User;
import cn.hhchat.record.util.HttpUtil;
import cn.hhchat.record.util.ImgUtil;
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
    public String login(OkHttpClient client, String email, String password) {

        String cookie;
        do {
            cookie = requestForInitSession(client);
        } while (cookie == null);
        Config.COOKIE = cookie;

        Boolean ok;
        do {
            ok = submitLogin(client, cookie, email, password);
            if (ok == null) {
                return "error";
            }
        } while (!ok);

        String userId;
        do {
            userId = requestForUserId(client);
        } while (userId == null);

        return userId;
    }

    /**
     * 获取可以放在 cookie 的头部
     */
    private String cookieHeader(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        for (int i = 0, size = cookies.size(); i < size; i++) {
            if (i > 0) {
                cookieHeader.append("; ");
            }
            Cookie cookie = cookies.get(i);
            if (cookie.value().contains("deleted")) {
                continue;
            }
            cookieHeader.append(cookie.name()).append('=').append(cookie.value());
        }
        return cookieHeader.toString();
    }

    /**
     * 处理会话 id
     */
    private String cookieSessionId(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        for (int i = 0, size = cookies.size(); i < size; i++) {
            if (cookies.get(i).name().equals("PHPSESSID")) {
                return "PHPSESSID=" + cookies.get(i).value();
            }
        }
        return null;
    }

    /**
     * 初始化会话
     */
    public String requestForInitSession(OkHttpClient client) {
        log.info(" => 尝试创建会话");
        Request request = new Request.Builder().url("http://nian.so").build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            String body = HttpUtil.isOk(response);
            if (body != null) {
                List<Cookie> cookies = client.cookieJar().loadForRequest(request.url());
                if (!cookies.isEmpty()) {
                    return cookieSessionId(cookies);
                }
                return null;
            }
        } catch (IOException e) {
            log.error(" => 登陆失败, {}", e.getMessage());
        }
        log.error(" => 创建会话失败");
        return null;
    }

    /**
     * 获取用户id
     */
    public String requestForUserId(OkHttpClient client) {
        log.info(" => 尝试获取用户信息");
        String index = "";
        Request request = new Request.Builder().url("http://nian.so").build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String body = HttpUtil.isOk(response);
            if (body != null) {
                Document document = Jsoup.parse(body);
                Element headerElement = document.selectFirst("a[id=head]");
                if (headerElement == null) {
                    return "failed";
                }
                String userUrl = headerElement.attr("href").trim();
                return userUrl.substring(userUrl.lastIndexOf("/") + 1);
            }
        } catch (IOException e) {
            log.error(" => 获取用户 id 失败 {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取用户信息
     */
    public User getUser(OkHttpClient client, String userId) {
        User user;
        do {
            user = requestForUserInfo(client, userId);
        } while (user == null);
        return user;
    }

    /**
     * 提交登陆
     */
    private Boolean submitLogin(OkHttpClient client, String cookie, String email, String password) {

        String loginPwd = SecureUtil.md5("n*A" + password);

        RequestBody requestBody = new FormBody.Builder().add("em", email).add("pw", loginPwd).build();
        Request request = new Request.Builder().header("Cookie", cookie).url(Config.getHtml("login_check.php")).post(requestBody).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String body = HttpUtil.isOk(response);
            if (body != null) {
                log.info(" <= 获取了登陆的响应，如果是 1 则登陆成功 : {}", body);
                if(body.equals("1")){
                    List<Cookie> cookies = client.cookieJar().loadForRequest(request.url());
                    if (!cookies.isEmpty()) {
                        String cookieStr = cookieHeader(cookies);
                        Config.COOKIE = Config.COOKIE + "; " + cookieStr;
                    }
                    log.debug(" => cookie {}", Config.COOKIE);
                    return true;
                }else if(body.equals("4")){
                    return null;
                }
            }
        } catch (IOException e) {
            log.error(" => 提交登陆失败", e.getMessage());
        }
        return false;
    }

    /**
     * 请求用户信息
     */
    public User requestForUserInfo(OkHttpClient client, String userId) {
        Request request = new Request.Builder().url("http://nian.so/user.php?uid=" + userId + "&&game").build();
        try {
            Response response = client.newCall(request).execute();
            String body = HttpUtil.isOk(response);
            if (body != null) {
                Document document = Jsoup.parse(body);
                Element userElement = document.selectFirst("div[class=title]");
                Element headerElement = document.selectFirst("div[class=user_head]");
                String userName = userElement.text();
                String userHeader = ImgUtil.getBackGroundImage(headerElement);
                User user = new User();
                user.setImg(userHeader);
                user.setNickname(userName);
                user.setId(userId);
                return user;
            }

        } catch (IOException e) {
            log.info(" => 获取用户信息出错", e.getMessage());
        }
        return null;
    }

}
