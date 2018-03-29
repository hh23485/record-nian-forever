package cn.hhchat.record.util;


import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.InputStream;
import java.util.Base64;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class ImgUtil {

    private final static String background_prefix = "background-image:url(";

    public static String getImage(String path) {
        if (path.startsWith("http://")) {
            return path;
        }else{
            return Base64.getEncoder().encodeToString(FileUtil.readFile(path));
        }
    }

    public static String getBackGroundImage(String style) {
        if (StrUtil.isBlank(style) && !style.contains(background_prefix)) {
            return null;
        }
        String backgroundUrl = style.split(";")[0].trim();
        int end = backgroundUrl.lastIndexOf("!");
        if(end == -1){
            end = backgroundUrl.length() - 1;
        }
        return backgroundUrl.substring(background_prefix.length(), end);
    }

    public static String getImageFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("!"));
    }

    public static String getBackGroundImage(Element backBox) {
        return backBox.hasAttr("style") ? getBackGroundImage(backBox.attr("style")) : null;
    }

    public static boolean FetchImage(String url, String filePlace) {
        for (int i = 0; i < 10; i++) {
            try {
                long fileSize = HttpUtil.downloadFile(url, filePlace);
                return fileSize > 0;
            } catch (Exception e) {
                log.error(" => 下载图片失败: 文件夹名为： {} : {}", filePlace, e.getMessage());
            }
        }
        return false;
    }



}
