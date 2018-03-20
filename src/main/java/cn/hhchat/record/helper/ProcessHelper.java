package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.DreamItem;
import cn.hhchat.record.model.data.Comment;
import cn.hhchat.record.model.data.Step;
import cn.hhchat.record.model.item.CommentItem;
import cn.hhchat.record.util.HttpUtil;
import cn.hhchat.record.model.item.ProcessItem;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class ProcessHelper {

    private OkHttpClient client;

    public ProcessHelper(OkHttpClient client) {
        this.client = client;
    }

    public List<DreamItem> getAllProcess(List<DreamItem> dreamItemList) {
        getAllProcessFromBookList(dreamItemList);
        return dreamItemList;
    }

    public void getAllProcessFromBookList(List<DreamItem> dreamItemList) {

        for (DreamItem dreamItem : dreamItemList) {
            getAllProcessFromBook(dreamItem);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.info(" => 线程出错");
            }
            Boolean test = false;
            if (test) {
                return;
            }
        }
    }

    private void getAllProcessFromBook(DreamItem dreamItem) {
        List<ProcessItem> processItemList = new ArrayList<>();
        Integer page = 1;
        while (page != null && page != -1) {
            log.info(" => 获取第 {} 页进展", page);
            page = getPageProcess(dreamItem, page, processItemList);
        }
        // deal comment
        dreamItem.setProcessList(processItemList);
        if (Config.WANT_COMMENTS) {
            dealProcessComments(processItemList);
        }
    }

    //多线程 下载
    public void dealProcessComments(List<ProcessItem> processItems) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(processItems.size());
        for (ProcessItem item : processItems) {
            pool.submit(() -> {
                try {
                    getAllComments(item);
                } catch (Exception e) {
                    log.error("线程出错");
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            pool.shutdown();
        } catch (InterruptedException e) {
            log.error("等待出错");
        }
    }

    public Integer getPageProcess(DreamItem dreamItem, Integer page, List<ProcessItem> processItemList) {
        Request request = null;
        request = new Request.Builder().url(ApiHelper.getProcessFromDreamUrl(dreamItem.getId(), page)).header("Cookie", Config.COOKIE).build();
        Boolean next = null;
        do {
            next = getPageProcess(request, processItemList);
        } while (next == null);
        return !next ? -1 : page + 1;
    }


    private Boolean getPageProcess(Request request, List<ProcessItem> processItemList) {
        try {
            Response response = client.newCall(request).execute();
            JSONObject respJson = HttpUtil.isOk(response);
            if (respJson != null && respJson.getInteger("error") == 0) {
                JSONObject jsonData = respJson.getJSONObject("data");
                JSONArray jsonSteps = jsonData.getJSONArray("steps");

                List<ProcessItem> list = analyzeProcessList(jsonSteps);
                if (list != null) {
                    processItemList.addAll(list);
                    return processHasNext(jsonSteps);
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            log.error("get page failed: {}", e.getMessage());
        }
        return null;
    }


    private Boolean processHasNext(JSONArray stepArrays) {
        return stepArrays.size() > 0;
    }

    private List<ProcessItem> analyzeProcessList(JSONArray jsonStepArray) {
        List<ProcessItem> processItemList = new ArrayList<>();
        for (int i = 0; i < jsonStepArray.size(); i++) {
            ProcessItem processItem = analyzeProcess(jsonStepArray.getJSONObject(i));
            processItemList.add(processItem);
        }
        return processItemList;
    }

    private ProcessItem analyzeProcess(JSONObject jsonStep) {
        Step step = jsonStep.toJavaObject(Step.class);
        String createTime = DateUtil.date(step.getLastdate() * 1000).toString("yyyy-MM-dd HH:mm");

        List<String> imageList = processImageList(step);
        List<String> textContent = processTextContent(step);
        log.info(" => 获取到文字记录: {}", textContent);
        ProcessItem process = new ProcessItem();
        process.setId(step.getSid());
        process.setImageList(imageList);
        process.setText(textContent);
        process.setCreateTime(createTime);
        process.setCoolCnt(step.getLikes());
        process.setUsername(step.getUser());
        process.setUid(step.getUid());
        return process;
    }


    public List<String> processImageList(Step step) {
        return step.getImages().stream().map(img -> ApiHelper.getProcessImageUrl(img.getPath())).collect(Collectors.toList());
    }

    private List<String> processTextContent(Step step) {
        String[] strLines = step.getContent().trim().split("\\n");
        List<String> lineList = new ArrayList<>();
        for (String line : strLines) {
            lineList.add(line.trim());
        }
        return lineList;
    }


    public List<CommentItem> getAllComments(ProcessItem processItem) {
        List<CommentItem> commentItemList = new ArrayList<>();
        Integer page = 1;
        while (page != null && page != -1) {
            log.info(" => 获取进展 {} 评论", processItem.getId());
            page = getPageComment(processItem, page, commentItemList);
        }
        processItem.setCommentItemList(commentItemList);
        return commentItemList;
    }

    public Integer getPageComment(ProcessItem processItem, Integer page, List<CommentItem> commentItemList) {
        Request request = null;
        request = new Request.Builder().url(ApiHelper.getProcessCommentUrl(processItem.getId(), page)).header("Cookie", Config.COOKIE).build();
        Boolean next = null;
        do {
            next = getPageComment(request, commentItemList);
        } while (next == null);
        return !next ? -1 : page + 1;
    }


    private Boolean getPageComment(Request request, List<CommentItem> commentItemList) {
        try {
            Response response = client.newCall(request).execute();
            JSONObject respJson = HttpUtil.isOk(response);
            if (respJson != null && respJson.getInteger("error") == 0) {
                JSONObject jsonData = respJson.getJSONObject("data");
                JSONArray jsonComment = jsonData.getJSONArray("comments");
                List<CommentItem> list = analyzerCommentList(jsonComment);
                if (list != null) {
                    commentItemList.addAll(list);
                    return commentHasNext(jsonComment);
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            log.error("get page failed: {}", e.getMessage());
        }
        return null;
    }


    public List<CommentItem> analyzerCommentList(JSONArray jsonCommentArray) {
        List<CommentItem> commentItemList = new ArrayList<>();
        for (int i = 0; i < jsonCommentArray.size(); i++) {
            CommentItem commentItem = analyzeComment(jsonCommentArray.getJSONObject(i));
            commentItemList.add(commentItem);
        }
        return commentItemList;
    }

    public Boolean commentHasNext(JSONArray commentArrays) {
        return commentArrays.size() > 0;
    }

    public CommentItem analyzeComment(JSONObject jsonComment) {
        Comment comment = jsonComment.toJavaObject(Comment.class);
        CommentItem commentItem = new CommentItem();
        commentItem.setCreateTime(DateUtil.date(comment.getLastdate() * 1000).toString("yyyy-MM-dd HH:mm"));
        commentItem.setContent(comment.getContent());
        commentItem.setUid(comment.getUid());
        commentItem.setUsername(comment.getUser());
        return commentItem;
    }


}


