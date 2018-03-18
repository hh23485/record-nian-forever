package cn.hhchat.record.model;

import lombok.Data;

import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Data
public class Nian {

    User user;
    List<Dream> dreamList;

    public void localingImg() {
        for (Dream dream : dreamList) {
            dream.saveCoverToLocal();
            for (ProcessItem processItem : dream.getProcessList()) {
                processItem.saveImagesToLocal(dream.getNianId());
            }
        }
        user.saveHeaderImageToLocal();
    }
}
