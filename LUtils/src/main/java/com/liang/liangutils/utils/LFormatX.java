package com.liang.liangutils.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 格式化
 */
public class LFormatX {

    private final static ThreadLocal<SimpleDateFormat> sYyyyMmDdSdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };
    public static        long                          ONE_MINUTE   = 3_600; // 一分钟
    public static        long                          ONE_HOUR     = 60 * ONE_MINUTE; // 一小时
    public static        long                          ONE_DAY      = 24 * ONE_HOUR; // 一天
    public static        long                          ONE_MONTH    = 30 * ONE_DAY; // 一月
    public static        long                          ONE_YEAR     = 12 * ONE_MONTH; // 一年

    /**
     * 格式化播放时间
     *
     * @param seconds 秒数
     *                时间＜1小时显示分秒，显示样式 00:20
     *                时间≥1小时显示时分秒，显示样式 01:11:12
     */
    public static String formatSeconds(long seconds) {
        String standardTime;
        if (seconds <= 0) {
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }

    /**
     * 格式化播放时间
     *
     * @param seconds 秒数
     *                时间＜1小时显示分秒，显示样式 00:20
     *                时间≥1小时显示时分秒，显示样式 01:11:12
     * @param detail 是否详细显示，true详细显示，会显示时分秒三位，false精简显示-只显示两部分
     */
    public static String formatSeconds(long seconds,boolean detail) {
        String standardTime;
        if (detail){
            if (seconds <= 0) {
                standardTime = "00:00:00";
            } else if (seconds < 60) {
                standardTime = String.format(Locale.getDefault(), "00:00:%02d", seconds % 60);
            } else if (seconds < 3600) {
                standardTime = String.format(Locale.getDefault(), "00:%02d:%02d", seconds / 60, seconds % 60);
            } else {
                standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
            }
        }else {
            if (seconds <= 0) {
                standardTime = "00:00";
            } else if (seconds < 60) {
                standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
            } else if (seconds < 3600) {
                standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
            } else {
                standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
            }
        }
        return standardTime;
    }

    /**
     * 播放量
     *
     * @param playCount:后台返回的播放量（单位：个） 播放量 ＜ 1万，显示样式 1、10、1000
     *                                 播放量 ≥ 1万，显示样式 1.2万 1.23万
     *                                 播放量 ≥ 1亿，显示样式 1.2亿 1.23亿
     */
    public static String formatPlayCount(long playCount) {
        String standardPlayCount = "";
        if (playCount <= 0) {
            standardPlayCount = "0";
        } else if (playCount < 10000) {
            standardPlayCount = String.valueOf(playCount);
        } else if (playCount < 100000000) {
            standardPlayCount = String.format(Locale.getDefault(), "%d.%02d万", playCount / 10000, playCount % 10000 / 100);
        } else if (playCount > 100000000) {
            standardPlayCount = String.format(Locale.getDefault(), "%d.%02d亿", playCount / 100000000, playCount % 100000000 / 1000000);
        }
        return standardPlayCount;
    }

    /**
     * 获取时间显示
     * 0<评论时间<1分钟
     * 刚刚
     * 1分钟<=评论时间<1小时
     * 1分钟；2分钟···
     * 1小时<=评论时间<1天
     * 1小时，2小时···
     * 1天<=评论时间<1月
     * 昨天；前天；3天前···
     * 1月<=评论时间<1年
     * 1月前；2月前···
     * 1年<=评论时间
     * 2016年；2017年····
     *
     * @param time 需要格式化的时间
     * @return 格式化后的时间
     */
    public static String formatFriendlyTime(long time) {
        String result;
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = sYyyyMmDdSdf.get().format(cal.getTime());
        String paramDate = sYyyyMmDdSdf.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time) / 3600000);
            if (hour == 0) {
                result = Math.max((cal.getTimeInMillis() - time) / 60000, 1) + "分钟前";
            } else {
                result = hour + "小时前";
            }
            return result;
        }

        long lt = time / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time) / 3600000);
            if (hour == 0) {
                result = Math.max((cal.getTimeInMillis() - time) / 60000, 1) + "分钟前";
            } else {
                result = hour + "小时前";
            }
        } else if (days == 1) {
            result = "昨天";
        } else if (days == 2) {
            result = "前天 ";
        } else if (days > 2 && days < 31) {
            result = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            result = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            result = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            result = "3个月前";
        } else {
            result = sYyyyMmDdSdf.get().format(time);
        }
        return result;
    }

    /**
     * 格式化毫秒时间
     *
     * @param millis 毫秒数
     *               curYear 年
     *               curMonth 月
     *               curDay 日
     */
    public static List<Integer> formatMillisSeconds(long millis) {
        List<Integer> dataList = new ArrayList<>();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = mSimpleDateFormat.format(millis);
        Integer curYear = Integer.parseInt(currentDate.substring(0, 4));
        Integer curMonth = Integer.parseInt(currentDate.substring(0, 4));
        Integer curDay = Integer.parseInt(currentDate.substring(8, 10));
        dataList.add(curYear);
        dataList.add(curMonth);
        dataList.add(curDay);
        return dataList;
    }
}

