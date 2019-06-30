package com.liang.liangutils.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : Amarao
 * CreateAt : 16:16 2019/2/26
 * Describe :
 *
 * long getYesterDayStamp() 得到昨天0点的时间戳
 * String getCurrentTime() 获取当前时间
 * String convertStampToTime(long timeMillis) 将时间戳转换为时间
 */
public class LTimeUtils {

    /**
     * 描述：得到昨天0点的时间戳
     */
    public static long getYesterDayStamp() {
        // 今天00：00：00
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);//设置小时数，24小时制
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        //昨天 00:00:00
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        //Date lastDay = now.getTime();
        return now.getTimeInMillis();
    }

    /**
     * 描述：
     */
    public static String getCurrentTime() {
        Date day = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(day);
        return time;
    }

    /**
     * @param timeMillis 毫秒
     * @return 将时间戳转换为时间
     */
    public static String convertStampToTime(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
