package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.Dream;
import cn.hhchat.record.util.ImgUtil;
import cn.hhchat.record.model.ProcessItem;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class ProcessHelper {

    public List<Dream> getAllProcess(OkHttpClient client, List<Dream> dreamList) {
        getAllProcessFromBookList(client, dreamList);
        return dreamList;
    }

    public void getAllProcessFromBookList(OkHttpClient client, List<Dream> dreamList) {

        for (Dream dream : dreamList) {
            getAllProcessFromBook(client, dream);
            boolean test = false;
            if (test) {
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.info(" => 线程出错");
            }
        }

        //CountDownLatch countDownLatch = new CountDownLatch(dreamList.size());
        //for (Dream dream : dreamList) {
        //    new Thread(() -> {
        //        try{
        //            getAllProcessFromBook(client, dream);
        //        }catch (Exception e){
        //            log.error("获取Book - {} 进展出错",dream.getTitle());
        //        }finally {
        //            countDownLatch.countDown();
        //        }
        //    });
        //}
        //try {
        //    countDownLatch.await();
        //} catch (InterruptedException e) {
        //    log.error("多线程获取进展出错: {}", e.getMessage());
        //}

    }

    private void getAllProcessFromBook(OkHttpClient client, Dream dream) {
        List<ProcessItem> processItemList = new ArrayList<>();
        Integer page = 0;
        while (page != null && page != -1) {
            log.info(" => 获取第 {} 页进展", page);
            page = getPage(client, dream, page, processItemList);
        }
        dream.setProcessList(processItemList);
    }

    public Integer getPage(OkHttpClient client, Dream dream, Integer page, List<ProcessItem> processItemList) {
        Request request = null;
        if (page == 0) {
            request = new Request.Builder().url(dream.getPageUrl()).header("Cookie", Config.COOKIE).build();
        } else {
            // add all page info
            RequestBody body = new FormBody.Builder().add("id", dream.getNianId()).add("page", page.toString()).build();
            request = new Request.Builder().url(Config.getHtml("more_step.php")).post(body).build();
        }
        Integer next = null;
        do {
            next = getPage(client, request, processItemList);
        } while (next == null);
        return next;
    }


    private Integer getPage(OkHttpClient client, Request request, List<ProcessItem> processItemList) {
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                String body = response.body().string();
                Document processPage = Jsoup.parse(body);
                processPage.outputSettings().prettyPrint(false);
                List<ProcessItem> list = analizyerProcessList(processPage.select("div[class=step]"));
                if (list != null) {
                    processItemList.addAll(list);
                    return hasNext(processPage);
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            log.error("get page failed: {}", e.getMessage());
        }
        return null;
    }


    public Integer hasNext(Document document) {
        Element readMore = document.selectFirst("div[class=step_more]");
        if (readMore == null) {
            return -1;
        }
        String readMoreMethod = readMore.attr("onclick");
        int pageEnd = readMoreMethod.lastIndexOf("'");
        int pageStart;
        for (pageStart = pageEnd - 1; pageStart > readMoreMethod.lastIndexOf(","); pageStart--) {
            if (readMoreMethod.charAt(pageStart) == '\'') {
                break;
            }
        }
        String pageNumberStr = readMoreMethod.substring(pageStart + 1, pageEnd);
        return Integer.valueOf(pageNumberStr);
    }

    public List<ProcessItem> analizyerProcessList(List<Element> processElementList) {
        return processElementList.stream().map(this::analizyer).collect(Collectors.toList());
    }

    public ProcessItem analizyer(Element processElement) {
        String processId = processElement.id();
        String createTime = processElement.selectFirst("div[class=step_time]").text();

        Element stepCoolMeElement = processElement.selectFirst("div[step_cool_me]");
        String coolCnt = stepCoolMeElement == null ? "赞 (0)" : stepCoolMeElement.text();
        List<String> imageList = processImageList(processElement.select("a[class=img]"));
        List<String> textContent = processTextContent(processElement);
        log.info(" => 获取到文字记录: {}", textContent);
        ProcessItem process = new ProcessItem();
        process.setId(processId.substring(4));
        process.setImageList(imageList);
        process.setText(textContent);
        process.setCreateTime(createTime);
        process.setCoolCnt(coolCnt);
        return process;
    }

    public List<String> processImageList(List<Element> imageElementList) {
        return imageElementList.stream().map(ImgUtil::getBackGroundImage).collect(Collectors.toList());
    }

    private List<String> processTextContent(Element element) {
        if (element.selectFirst("div[class=step_inner]") != null) {
            element = element.selectFirst("div[class=step_inner]");
        }
        List<TextNode> nodes = element.textNodes();
        String contentStr = "";
        for (TextNode node : nodes) {
            String nodeStr = node.getWholeText().trim();
            if (!nodeStr.startsWith("<") && !nodeStr.isEmpty()) {
                contentStr = node.toString().trim();
            }
        }
        String[] strLines = contentStr.split("\\n");
        List<String> lineList = new ArrayList<>();
        for (String line : strLines) {
            lineList.add(line.trim());
        }
        return lineList;
    }
}


