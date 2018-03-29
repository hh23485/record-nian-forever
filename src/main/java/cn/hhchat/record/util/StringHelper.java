package cn.hhchat.record.util;

/**
 * Created this one by huminghao on 2018/3/23.
 */
public class StringHelper {
    public static String removeEscape(String str) {
        str = str.replaceAll("&lt;br&gt;", "\n");
        str = str.replaceAll("<br>", "\n");
        str =  str.replaceAll("(&nbsp;nbsp;)", " ");
        str =  str.replaceAll("(&amp;nbsp;)", " ");
        str =  str.replaceAll("(&nbsp;&nbsp;)", " ");
        str =  str.replaceAll("(&nbsp;)", " ");
        str =  str.replaceAll("(&amp;)", " ");
        return str;
    }
}
