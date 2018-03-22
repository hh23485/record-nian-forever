package cn.hhchat.record.model;

import cn.hhchat.record.helper.ApiHelper;
import cn.hhchat.record.util.FileUtil;
import cn.hhchat.record.util.ImgUtil;
import cn.hhchat.record.util.MarkdownUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
@Data
public class User {

    String birthDay;
    String nickname;
    String imgLocal;
    String img;
    String id;
    //List<User> watchList;
    //List<User> followList;

    public void setId(String id) {
        this.id = id;
        this.img = ApiHelper.getHeaderImageUrl();
    }

    public String toMD() {
        StringBuilder sb = new StringBuilder();
        sb.append(MarkdownUtil.quote(nickname));
        return sb.toString();
    }

    public void saveHeaderImageToLocal() {
        log.info(" => 下载头像");
        String localFileName = FileUtil.generateHeadImgPath(nickname, ImgUtil.getImageFileName(this.getImg()));
        int count = 10;
        while (!ImgUtil.FetchImage(img, localFileName) && count-- > 0) {
            log.warn("下载 {} 的头像图失败，重试中", img);
        }
        if (count == -1) {
            imgLocal = "";
            this.imgLocal = localFileName;
        }
    }
}


