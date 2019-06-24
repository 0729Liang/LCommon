package com.liang.liangutils.msg;

import org.greenrobot.eventbus.EventBus;

/**
 * @author : Amarao
 * CreateAt : 14:44 2019/2/21
 * Describe : eventBus 基类
 */
public class BusEvent {

    public String msg;
    public int    code;

    protected BusEvent() {
    }

    protected BusEvent(int code) {
        this.code = code;
    }

    protected BusEvent(String msg) {
        this.msg = msg;
    }

    protected static void post(BusEvent event) {
        EventBus.getDefault().post(event);
    }

    protected static void postSticky(BusEvent event) {
        EventBus.getDefault().postSticky(event);
    }
}
