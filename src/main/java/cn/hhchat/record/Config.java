package cn.hhchat.record;

import com.xiaoleilu.hutool.collection.CollectionUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Data
@Slf4j
public class Config {

    public static final String DOMAIN = "http://nian.so/";
    public static String NAME = "";
    public static String COOKIE = "";
    public static String SHELL = "";
    public static String UID = "";
    public static String ROOT = "./";
    public static String IMG_MAX_SIZE = "500";
    public static String EMAIL;
    public static Boolean TEST;
    public static String PASSWORD;
    public static Boolean REVERSE = false;
    public static Boolean DOWNLOAD_IMAGES = false;
    public static Boolean USE_JSON_DATA = false;
    public static Boolean WANT_COMMENTS = false;
    public static Boolean WANT_MULTIDREAM = true;
    public static List<String> WHITE_LIST = new ArrayList<>();
    public static List<String> BLACK_LIST = new ArrayList<>();


    public static String getHtml(String path) {
        return DOMAIN + path;
    }

    public static Boolean inWhiteList(String title) {
        if(CollectionUtil.isEmpty(WHITE_LIST)){
            return true;
        }
        for (String str : WHITE_LIST) {
            if (title.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean inBlackList(String title) {
        for (String str : BLACK_LIST) {
            if (str.equals(title)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean load() {
        try {
            Path path = Paths.get(new URI("file:"+ROOT + "config.json"));
            if (Files.exists(path)) {
                List<String> configStrList = Files.readAllLines(path);
                StringBuilder stringBuilder = new StringBuilder();
                for (String string : configStrList) {
                    stringBuilder.append(string);
                }
                JSONObject config = JSONUtil.parseObj(stringBuilder.toString());
                IMG_MAX_SIZE = String.valueOf(config.get("IMG_MAX_SIZE"));
                EMAIL = String.valueOf(config.get("EMAIL"));
                PASSWORD = String.valueOf(config.get("PASSWORD"));
                PASSWORD = String.valueOf(config.get("PASSWORD"));
                REVERSE = Boolean.valueOf(String.valueOf(config.get("REVERSE")));
                TEST = Boolean.valueOf(String.valueOf(config.get("TEST")));
                DOWNLOAD_IMAGES = Boolean.valueOf(String.valueOf(config.get("DOWNLOAD_IMAGES")));
                USE_JSON_DATA = Boolean.valueOf(String.valueOf(config.get("USE_JSON_DATA")));
                WANT_COMMENTS = Boolean.valueOf(String.valueOf(config.get("WANT_COMMENTS")));
                WANT_MULTIDREAM = Boolean.valueOf(String.valueOf(config.get("WANT_MULTIDREAM")));
                WHITE_LIST = config.getJSONArray("WHITE_LIST").toList(String.class);
                BLACK_LIST = config.getJSONArray("BLACK_LIST").toList(String.class);
                return true;
            }else{
                log.error(" => 没有找到配置文件文件");
            }
        } catch (URISyntaxException e) {
            log.error(" => 配置文件路径错误: {}", e.getMessage());
        } catch (Exception e) {
            log.error(" => 读取配置文件失败: {}", e.getMessage());
        }
        return false;
    }

}
