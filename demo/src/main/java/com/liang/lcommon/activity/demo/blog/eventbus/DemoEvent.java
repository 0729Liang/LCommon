package com.liang.lcommon.activity.demo.blog.eventbus;

import com.liang.liangutils.msg.BusEvent;

/**
 * @author : Amarao
 * CreateAt : 14:52 2019/2/21
 * Describe :
 */
public class DemoEvent extends BusEvent {
    public static final String EVENT_SEND_ONE   = "EVENT_SEND_ONE";
    public static final String EVENT_SEND_TWO   = "EVENT_SEND_TWO";
    public static final String EVENT_SEND_THREE = "EVENT_SEND_THREE";

    // 后缀
    private String mStringSuffix;
    private int    mNumberSuffix;

    public String getStringSuffix() {
        return mStringSuffix;
    }

    public int getNumberSuffix() {
        return mNumberSuffix;
    }

    public static void postEventBusDemoOne() {
        DemoEvent event = new DemoEvent();
        event.msg = EVENT_SEND_ONE;
        post(event);
    }

    public static void postEventBusDemoTwo(int number) {
        DemoEvent event = new DemoEvent();
        event.msg = EVENT_SEND_TWO;
        event.mNumberSuffix = number;
        post(event);
    }

    public static void postEventBusDemoThree(String msg) {
        DemoEvent event = new DemoEvent();
        event.msg = EVENT_SEND_THREE;
        event.mStringSuffix = msg;
        post(event);
    }


}
