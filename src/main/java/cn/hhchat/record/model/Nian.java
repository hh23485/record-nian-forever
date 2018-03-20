package cn.hhchat.record.model;

import cn.hhchat.record.model.item.ProcessItem;
import lombok.Data;

import java.util.List;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Data
public class Nian {

    User user;
    List<DreamItem> dreamItemList;

    public void localingImg() {
        for (DreamItem dreamItem : dreamItemList) {
            dreamItem.saveCoverToLocal();
            for (ProcessItem processItem : dreamItem.getProcessList()) {
                processItem.saveImagesToLocal(dreamItem.getTitle());
            }
        }
    }
}
