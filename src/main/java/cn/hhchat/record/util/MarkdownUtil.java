package cn.hhchat.record.util;

import cn.hhchat.record.Config;

/**
 * Created this one by huminghao on 2018/3/18.
 */
public class MarkdownUtil {

    public static String header1(String item) {
        if (item == null) {
            item = "";
        }
        return "# " + item + "  \n";
    }


    public static String header2(String item) {
        if (item == null) {
            item = "";
        }
        return "# " + item + "  \n";
    }

    public static String header3(String item) {
        if (item == null) {
            item = "";
        }
        return "### " + item + "  \n";
    }

    public static String quote(String item) {
        if (item == null) {
            item = "";
        }
        return ">" + item + "  \n";
    }

    public static String smallImage(String name, String path, Boolean newLine) {
        if (name == null) {
            name = "";
        }
        if (newLine) {
            //if(path.startsWith("http")){
                return "<img src=\"" + path + "\" style=\"max-width: "+ 100 +"px\" />\n";
            //}
            //return "![" + name + "](" + path + ")  \n";
        }
        //if(path.startsWith("http")){
            return "<img src=\"" + path + "\" style=\"max-width: "+ 100 +"px\" />";
        //}else{
        //    return "![" + name + "](" + path + ") ";
        //}
    }

    public static String image(String name, String path, Boolean newLine) {
        if (name == null) {
            name = "";
        }
        if (newLine) {
            //if(path.startsWith("http")){
                return "<img src=\"" + path + "\" style=\"max-width: "+ Config.IMG_MAX_SIZE+"px\" />\n";
            //}
            //return "![" + name + "](" + path + ")  \n";
        }
        //if(path.startsWith("http")) {
            return "<img src=\"" + path + "\" style=\"max-width: " + Config.IMG_MAX_SIZE + "px\" />";
        //}
        //return "![" + name + "](" + path + ")";
    }

    public static String toc() {
        return "[TOC]  \n";
    }

    public static String hr() {
        return "<div class=\"hr\" style=\"height: 1px; border-bottom: 1px #ccc solid\"></div>\n";
    }

    public static String B(String item) {
        if (item == null) {
            item = "";
        }
        return "**" + item + "**";
    }

    public static String emptyLine() {
        return "  \n";
    }

    public static String emptyBrLine() {
        return "<br>\n";
    }


    public static String oneLine(String item) {
        item = item == null ? "" : item;
        return item + "  \n";
    }


}
