package cn.hhchat.record.util;

import cn.hhchat.record.Config;
import com.xiaoleilu.hutool.io.IoUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class FileUtil {

    public static String generateProcessImgPath(String id, String processId, String fileName) {
        return Config.ROOT + "nian/image/" +  id + "/process/" + processId + "-" + fileName;
    }

    public static String generateHeadImgPath(String nickname, String fileName) {
        return Config.ROOT + "nian/image/header/" + nickname + "-" + fileName;
    }

    public static String generateCoverImgPath(String id, String fileName) {
        return Config.ROOT + "nian/image/" + id + "/cover-" + fileName;
    }

    public static String generateMDPath(String title) {
        return Config.ROOT + "nian/md/"+ title + "/dream - " + title + ".md";
    }

    public static String generateJSONPath() {
        return Config.ROOT + "nian/json/data.json";
    }

    public static boolean writeNewFile(String path, String content) {
        File file = new File(path);
        if(!ensureFile(path)){
            return false;
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);
            return true;
        } catch (IOException e) {
            log.error(" => 写入文件失败 {} :", file.getAbsolutePath(), e);
        }
        return false;
    }

    public static boolean ensureFile(String filePath) {
        File file = new File(filePath);
        File folder = file.getParentFile();
        while (!folder.exists()) {
            log.info(" => 尝试创建文件夹： {}", folder.getAbsolutePath());
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error(" => 创建文件失败 {} :", file.getAbsolutePath(), e);
                return false;
            }
        }
        return true;
    }

    public static byte[] readFile(String filePath) {
        try {
            byte[] imageContents = null;
            imageContents = com.xiaoleilu.hutool.io.FileUtil.readBytes(new File(filePath));
            return imageContents;
        } catch ( Exception e) {
            log.error(" => 读取图片失败 {}", e.getMessage());
        }
        return null;
    }

}
