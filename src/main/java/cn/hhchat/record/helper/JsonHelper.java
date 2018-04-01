package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.Nian;
import cn.hhchat.record.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.CharsetUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class JsonHelper {

    public Boolean exportJsonFile(Nian nian) {
        if(Config.DOWNLOAD_IMAGES){
            nian.localingImg();
        }
        String content = JSON.toJSONString(nian);
        FileUtil.writeNewFile(FileUtil.generateJSONPath(), content);
        return true;
    }

    public static Nian load() {
        String jsonFile = FileUtil.generateJSONPath();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            String jsonNian = reader.readLine();
            Nian nian = JSON.parseObject(jsonNian, Nian.class);
            Config.UID = nian.getUser().getId();
            return nian;
        } catch (IOException e) {
            log.info("获取json数据出错 {}，请检查json文件是否存在，以及是否被改动过", e.getMessage());
        }
        return null;
    }
}
