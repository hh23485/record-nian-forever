package cn.hhchat.record.helper;

import cn.hhchat.record.model.DreamItem;
import cn.hhchat.record.model.Nian;
import cn.hhchat.record.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import static cn.hhchat.record.util.FileUtil.writeNewFile;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class MDHelper {

    public void generateMarkdownFiles(Nian nian){
        for (DreamItem d : nian.getDreamItemList()) {
            if(!saveToFile(d, d.toMD(nian.getUser()))){
                log.warn(" => 梦想 {} 生成 markdown 失败", d.getTitle());
            }else{
                log.warn(" => 梦想 {} 生成 markdown 成功", d.getTitle());
            }
        }
    }

    public Boolean saveToFile(DreamItem dreamItem, String md)  {
        String filePath = FileUtil.generateMDPath(dreamItem.getTitle());
        return writeNewFile(filePath, md);
    }

}
