package cn.hhchat.record.model.item;

import cn.hhchat.record.util.MarkdownUtil;
import lombok.Data;

import java.util.Date;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Data
public class CommentItem {

    String uid;
    String username;
    String content;
    String createTime;

    public String toMD() {
        return MarkdownUtil.quote(this.username + "： " + this.content);
    }

}
