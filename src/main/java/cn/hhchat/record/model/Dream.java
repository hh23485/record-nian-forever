package cn.hhchat.record.model;

import cn.hhchat.record.Config;
import cn.hhchat.record.util.FileUtil;
import cn.hhchat.record.util.ImgUtil;
import cn.hhchat.record.util.MarkdownUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Data
@Slf4j
public class Dream {

    public String title;
    public List<String> userList;
    public String owner;
    public String img;
    public String imgLocal;
    public List<ProcessItem> processList;
    public String nianId;
    public String introduce;

    public String getPageUrl() {
        return Config.getHtml("thing.php?id=" + nianId);
    }

    public Boolean saveCoverToLocal() {
        log.info("尝试下载梦想 {} 的封面", this.title);
        String localFileName = FileUtil.generateCoverImgPath(this.getTitle(), ImgUtil.getImageFileName(this.getImg()));
        int count = 10;
        while (!ImgUtil.FetchImage(this.img + "!dream", localFileName) && count-- > 0) {
            log.warn("下载 {} 的封面图失败，重试中", this.title);
        }
        if (count == -1) {
            return false;
        }
        this.imgLocal = localFileName;
        return true;
    }

    public String toMD(User user) {
        String img = this.img;
        if (Config.DOWNLOAD_IMAGES || this.imgLocal!=null) {
            img = this.imgLocal;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MarkdownUtil.header1(this.title)).append(MarkdownUtil.emptyLine());

        if (img != null) {
            sb.append(MarkdownUtil.smallImage("cover", img, true))
                    .append(MarkdownUtil.emptyLine());
        }

        sb.append(MarkdownUtil.oneLine(user.getNickname()))
                .append(MarkdownUtil.emptyLine())
                .append(MarkdownUtil.quote("生成时间 " + DateUtil.format(new Date(), "yyyy-MM-dd")))
                .append(MarkdownUtil.emptyLine())
                .append(MarkdownUtil.emptyBrLine());

        if (processList == null) {
            return sb.toString();
        }
        int count = 0;
        if (Config.REVERSE) {
            for (int i = 0; i < this.processList.size(); i++) {
                log.info("生成第 {}/{} 个进展的 markdown", ++count, this.processList.size());
                sb.append(processList.get(i).toMD())
                        .append(MarkdownUtil.emptyLine())
                        .append(MarkdownUtil.emptyBrLine())
                        .append(MarkdownUtil.hr())
                        .append(MarkdownUtil.emptyBrLine())
                        .append(MarkdownUtil.emptyLine());
            }
        } else {
            for (int i = this.processList.size() - 1; i >= 0; i--) {
                log.info("生成第 {}/{} 个进展的 markdown", ++count, this.processList.size());
                sb.append(processList.get(i).toMD())
                        .append(MarkdownUtil.emptyLine())
                        .append(MarkdownUtil.emptyBrLine())
                        .append(MarkdownUtil.hr())
                        .append(MarkdownUtil.emptyBrLine())
                        .append(MarkdownUtil.emptyLine());
            }
        }

        sb.append(MarkdownUtil.emptyLine())
                .append(MarkdownUtil.quote("到底啦! 这个梦想一共 " + this.processList.size() + " 条进展呢"))
                .append(MarkdownUtil.oneLine("念爱你"));
        return sb.toString();
    }

}
