package com.liang.liangutils.msg;

import com.liang.liangutils.utils.LLogX;

import org.greenrobot.eventbus.EventBus;

/**
 * @author : Amarao
 * CreateAt : 14:44 2019/2/21
 * Describe :
 */
public class Exts {

    public static void registerEvent(Object host) {
        if (host == null) {
            return;
        }
        if (!EventBus.getDefault().isRegistered(host)) {
            try {
                EventBus.getDefault().register(host);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public static void unRegisterEvent(Object host) {
        if (host == null) {
            return;
        }
        if (EventBus.getDefault().isRegistered(host)) {
            try {
                EventBus.getDefault().unregister(host);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
