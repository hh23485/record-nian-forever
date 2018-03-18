package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import cn.hhchat.record.model.Dream;
import cn.hhchat.record.util.ImgUtil;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class DreamHelper {


    public List<Dream> getAllBooks(OkHttpClient client) {

        String cookie = Config.COOKIE;
        List<Element> dreamHtmlList = null;
        do {
            dreamHtmlList = getAllBooksElement(client, cookie);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error(" => 线程出错 {}", e.getMessage());
            }
        } while (CollectionUtil.isEmpty(dreamHtmlList));

        return analizyerBooks(dreamHtmlList);

    }

    public List<Element> getAllBooksElement(OkHttpClient client, String cookie) {
        //Request request = new Request.Builder().header("Cookie", cookie).url(Config.getHtml("list.php")).build();
        //log.info("cookie: {}", client.cookieJar().loadForRequest(request.url()));

        HttpRequest httpRequest = new HttpRequest(Config.getHtml("list.php")).cookie(cookie).header("Cookie", cookie);
        HttpResponse httpResponse = httpRequest.execute();

        List<Element> dreamList;
        Response response;
        try {
            //response = client.newCall(request).execute();
            //String indexHtml = response.body().string();
            String indexHtml = httpResponse.body();
            if (indexHtml != null) {
                Document document = Jsoup.parse(indexHtml);
                if (!indexHtml.contains("album_holder")) {
                    return null;
                }
                dreamList = document.select("div[class=album_holder]");
                return dreamList;
            }

        } catch (Exception e) {
            log.error(" => 获取 梦想 列表失败: {}", e.getMessage());
        }
        return null;
    }


    private List<Dream> analizyerBooks(List<Element> dreamsHtml) {
        return dreamsHtml.stream().map(this::analizyer).collect(Collectors.toList());
    }

    /*
      解析记本
     */
    private Dream analizyer(Element dreamHtml) {
        Element albumBox = dreamHtml.selectFirst("div[class=album_box]");
        Element albumTitle = dreamHtml.selectFirst("div[class=album_title]");
        Element albumTitleId = dreamHtml.selectFirst("a[class=album_title]");

        String img = ImgUtil.getBackGroundImage(albumBox);
        String title = albumTitle.text();
        String nianIdUrl = albumTitleId.attr("href");

        Dream dream = new Dream();
        dream.setTitle(title);
        dream.setImg(img);
        dream.setNianId(nianIdUrl.substring(nianIdUrl.lastIndexOf("/") + 1));
        return dream;
    }
}
