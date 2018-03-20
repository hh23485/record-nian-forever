package cn.hhchat.record;

import cn.hhchat.record.helper.*;
import cn.hhchat.record.model.DreamItem;
import cn.hhchat.record.model.Nian;
import cn.hhchat.record.model.User;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created this one by huminghao on 2018/3/18.
 */

@Slf4j
public class Main {
    public static void main(String[] args) {
        Nian nian = null;

        File file = new File(".");
        Config.ROOT = file.getAbsolutePath() + "/";
        Config.ROOT = "/Users/huminghao/Desktop/nian.so/";


        if (!Config.load()) {
            log.error(" => 配置文件存在问题呢，请检查一下");
            return;
        }

        if (!Config.USE_JSON_DATA) {
            String email = Config.EMAIL;
            String password = Config.PASSWORD;

            if (StrUtil.isBlank(email) || StrUtil.isBlank(password)) {
                log.error(" => 必须输入邮箱和密码");
                return;
            }

            OkHttpClient client = buildClient();

            LoginHelper loginHelper = new LoginHelper();
            Boolean loginSuccess = loginHelper.login(client, email, password);
            if (loginSuccess == null) {
                return;
            }

            User user = new User();
            user.setId(Config.UID);
            user.setNickname(Config.NAME);
            log.info(" => 用户 {}", user.getNickname());

            DreamHelper dreamHelper = new DreamHelper();
            List<DreamItem> dreamItemList = dreamHelper.getAllBooks(client);
            log.info(" => 所有的记本 {}", dreamItemList);

            ProcessHelper processHelper = new ProcessHelper();
            processHelper.getAllProcess(client, dreamItemList);

            nian = new Nian();
            nian.setUser(user);
            nian.setDreamItemList(dreamItemList);

            log.info(" => 尝试写入 json");
            JsonHelper jsonHelper = new JsonHelper();
            jsonHelper.exportJsonFile(nian);
        } else {
            nian = JsonHelper.load();
        }

        if (nian == null) {
            log.error("获取数据失败");
            return;
        }

        try {
            log.info(" => 尝试生成 markdown");
            MDHelper MDHelper = new MDHelper();
            MDHelper.generateMarkdownFiles(nian);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" => 保存数据出错 {}", e.getMessage(), e);
            return;
        }

        log.info(" -- 完成，念爱你 --");
    }


    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookies = cookies.stream().filter(c -> !c.value().contains("deleted")).collect(Collectors.toList());
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<>();
            }
        });
        return builder.build();
    }
}

