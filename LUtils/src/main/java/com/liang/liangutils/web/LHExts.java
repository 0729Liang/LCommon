package com.liang.liangutils.web;

/**
 * CreateAt : 2018/10/18
 * Describe :
 *
 * @author chendong
 */
public class LHExts {

    public static String fixUrl(String url) {
        if (url.startsWith("www")) {
            url = "http://" + url;
        }
        return url;
    }
}
