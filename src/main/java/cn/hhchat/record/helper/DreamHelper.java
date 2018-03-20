package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.DreamItem;
import cn.hhchat.record.model.data.Dream;
import cn.hhchat.record.util.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class DreamHelper {

    private OkHttpClient client;

    public DreamHelper(OkHttpClient client) {
        this.client = client;
    }

    public List<DreamItem> getAllBooks() {
        JSONArray dreamJsonList;
        do {
            dreamJsonList = getAllBooksElement();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error(" => 线程出错 {}", e.getMessage());
            }
        } while (CollectionUtil.isEmpty(dreamJsonList));

        return analyzeBooks(dreamJsonList);

    }

    public JSONArray getAllBooksElement() {
        Request request = new Request.Builder().url(ApiHelper.getAllDreamsUrl()).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            JSONObject jsonResponse = HttpUtil.isOk(response);
            if (jsonResponse != null && jsonResponse.getInteger("error") == 0) {
                JSONObject jsonData = jsonResponse.getJSONObject("data");
                return jsonData.getJSONArray("dreams");
            }
        } catch (Exception e) {
            log.error(" => 获取 梦想 列表失败: {}", e.getMessage());
        }
        return null;
    }


    private List<DreamItem> analyzeBooks(JSONArray dreamJSONList) {
        List<DreamItem> dreamItemList = new ArrayList<>();
        for (int i = 0; i < dreamJSONList.size(); i++) {
            DreamItem dreamItem = analyze(dreamJSONList.getJSONObject(i));
            dreamItemList.add(dreamItem);
        }
        return dreamItemList.stream().filter(d -> Config.WANT_MULTIDREAM || Config.UID.equals(d.getOwnerId())).filter(d -> Config.inWhiteList(d.getTitle())).filter(d -> !Config.inBlackList(d.getTitle())).collect(Collectors.toList());
    }


    /*
      解析记本
     */
    private DreamItem analyze(JSONObject jsonObject) {

        // 获取 记本信息
        String dreamId = jsonObject.getString("id");
        JSONObject detailJsonObject = null;
        int count = 10;

        while (detailJsonObject == null && count-- > 0) {
            detailJsonObject = getDreamDetail(dreamId);
        }

        //用更详细的替换
        if(detailJsonObject!=null){
            jsonObject = detailJsonObject;
        }

        Dream dream = jsonObject.toJavaObject(Dream.class);
        DreamItem dreamItem = new DreamItem();
        dreamItem.setTitle(dream.getTitle());
        dreamItem.setImg(ApiHelper.getDreamImageUrl(dream.getImage()));
        dreamItem.setId(dream.getId());
        dreamItem.setOwnerId(dream.getUid());
        dreamItem.setOwnerName(dream.getUser());
        dreamItem.setIntroduce(dream.getContent());
        dreamItem.setTags(dream.getTags());
        dreamItem.setFinished(dream.getPercent() != null && dream.getPercent() == 1);
        dreamItem.setPrivateDream(dream.getPrivateDream() != null && dream.getPercent() == 1);
        return dreamItem;

    }

    public JSONObject getDreamDetail(String dreamId) {
        Request request = new Request.Builder().url(ApiHelper.getDreamDetailUrl(dreamId)).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            JSONObject jsonResponse = HttpUtil.isOk(response);
            if (jsonResponse != null && jsonResponse.getInteger("error") == 0) {
                JSONObject jsonData = jsonResponse.getJSONObject("data");
                return jsonData.getJSONObject("dream");
            }
        } catch (Exception e) {
            log.error(" => 获取 梦想 详情失败: {}", dreamId, e.getMessage());
        }
        return null;
    }
}
