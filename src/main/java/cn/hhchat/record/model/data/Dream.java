package cn.hhchat.record.model.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created this one by huminghao on 2018/3/20.
 */
@Data
public class Dream {
    private String id;
    private String uid;
    private String user;
    private String title;
    private String content;
    private Integer percent;
    private String image;
    private Integer privateDream;
    private String step;
    private List<String> tags = new ArrayList<>();
}
