package cn.hhchat.record.helper;

import cn.hhchat.record.model.Dream;
import cn.hhchat.record.model.Nian;
import cn.hhchat.record.model.User;
import cn.hhchat.record.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static cn.hhchat.record.util.FileUtil.writeNewFile;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class MDHelper {

    public void generateMarkdownFiles(Nian nian){
        for (Dream d : nian.getDreamList()) {
            if(!saveToFile(d, d.toMD(nian.getUser()))){
                log.warn(" => 梦想 {} 生成 markdown 失败", d.title);
            }else{
                log.warn(" => 梦想 {} 生成 markdown 成功", d.title);
            }
        }
    }

    public Boolean saveToFile(Dream dream, String md)  {
        String filePath = FileUtil.generateMDPath(dream.title);
        return writeNewFile(filePath, md);
    }

}
