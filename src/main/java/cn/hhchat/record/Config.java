package cn.hhchat.record;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xiaoleilu.hutool.json.JSON;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Data
@Slf4j
public class Config {

    public static final String DOMAIN = "http://nian.so/";
    public static String COOKIE = "";
    public static String ROOT = "./";
    public static String IMG_MAX_SIZE = "500";
    public static String EMAIL;
    public static String PASSWORD;
    public static Boolean REVERSE = false;
    public static Boolean DOWNLOAD_IMAGES = false;
    public static Boolean USE_JSON_DATA = false;


    public static String getHtml(String path) {
        return DOMAIN + path;
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
                REVERSE = Boolean.valueOf(String.valueOf(config.get("REVERSE")));
                DOWNLOAD_IMAGES = Boolean.valueOf(String.valueOf(config.get("DOWNLOAD_IMAGES")));
                USE_JSON_DATA = Boolean.valueOf(String.valueOf(config.get("USE_JSON_DATA")));
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
