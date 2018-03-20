package cn.hhchat.record.model.data;

import lombok.Data;

/**
 * Created this one by huminghao on 2018/3/20.
 */
@Data
public class Comment {

    String id;
    String content;
    String uid;
    String user;
    Long lastdate;

}
