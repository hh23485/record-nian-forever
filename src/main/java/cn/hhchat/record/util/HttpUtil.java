package cn.hhchat.record.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created this one by huminghao on 2018/3/18.
 */
@Slf4j
public class HttpUtil {

    public static String isOk(Response response) {
        if(!response.isSuccessful()){
            return null;
        }
        String body = null;
        try {
            body = response.body().string();
            if(body.contains("An error occurred")){
                return null;
            }
            return body;
        } catch (IOException e) {
            log.error(" => 获取响应失败 {}", e.getMessage());
            return null;
        }
    }
}
