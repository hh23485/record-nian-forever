package cn.hhchat.record.model.data;

import lombok.Data;

import java.util.List;

/**
 * Created this one by huminghao on 2018/3/20.
 */
@Data
public class Step {
    String sid;
    String content;
    String uid;
    String image;
    Long lastdate;
    Integer likes;
    String user;
    Integer comments;
    String dream;
    List<StepImage> images;
    String type;
    String member;

    @Data
    public static class StepImage {
        Integer width;
        Integer height;
        String path;
    }
}
