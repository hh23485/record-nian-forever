package cn.hhchat.record.helper;

import cn.hhchat.record.Config;
import com.xiaoleilu.hutool.json.JSONObject;

/**
 * Created this one by huminghao on 2018/3/20.
 */
public class ApiHelper {

    public static String getLoginUrl() {
        return "http://api.nian.so/user/login";
    }

    public static String getAllDreamsUrl() {
        return "http://api.nian.so/user/" + Config.UID + "/dreams?" + getAuthority();
    }

    public static String getProcessFromDreamUrl(String dreamId, int page) {
        String sort = Config.REVERSE ? "asc" : "desc";
        return "http://api.nian.so/v2/multidream/" + dreamId + "?sort=" + sort + "&page=" + page + "&" + getAuthority();
    }

    public static String getProcessCommentUrl(String processId, int page) {
        return "http://api.nian.so/v2/step/" + processId + "/comments?page=" + page + "sid=" + processId + "&" + getAuthority();
    }

    public static String getUserDetailUrl() {
        return "http://api.nian.so/user/" + Config.UID + "?" + getAuthority();
    }

    public static String getAllPetsUrl() {
        return "http://api.nian.so/pets?" + getAuthority();
    }

    public static String getAllOwnPetsUrl() {
        return "http://api.nian.so/user/" + Config.UID + "/pets?" + getAuthority();
    }

    public static String getDreamDetailUrl(String dreamId) {
        return "http://api.nian.so/dream/" + dreamId + "?" + getAuthority();
    }

    public static String getDreamFollowerUrl(String dreamId, int page) {
        return "http://api.nian.so/multidream/" + dreamId + "/followers?page=" + page + "&" + getAuthority();
    }

    public static String getDreamLikerUrl(String dreamId, int page) {
        return "http://api.nian.so/multidream/" + dreamId + "/likes?page=" + page + "&" + getAuthority();
    }

    public static String getDreamMembersUrl(String dreamId, int page) {
        return "http://api.nian.so/multidream/" + dreamId + "/users?page=" + page + "&" + getAuthority();
    }

    public static String getAuthority() {
        return "uid=" + Config.UID + "&shell=" + Config.SHELL;
    }


    public static String getDreamImageUrl(String fileName) {
        return "http://img.nian.so/dream/" + fileName + "!dream";
    }

    public static String getProcessImageUrl(String fileName) {
        return "http://img.nian.so/step/" + fileName + "!large";
    }

    public static String getPetImageUrl(String fileName) {
        return "http://img.nian.so/pets/" + fileName + "!d";
    }

    public static String getHeaderImageUrl() {
        return "http://img.nian.so/head/" + Config.UID + ".jpg!head";
    }


}
