package com.liang.lcommon.utils;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Handler;

import com.liang.lcommon.able.LDestroyable;
import com.liang.lcommon.able.LRecyclable;
import com.liang.lcommon.able.LReleasable;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 回收资源的工具类
 */
public class LRecycleX {

    /**
     * 销毁资源
     *
     * @param destroyables LDestroyable
     * @return 是否回收成功
     */
    public static boolean recycle(LDestroyable... destroyables) {
        for (LDestroyable destroyable : destroyables) {
            if (destroyable != null) {
                destroyable.onDestroy();
            }
        }
        return true;
    }

    /**
     * 回收资源
     *
     * @param recyclables LRecyclable
     * @return 是否回收成功
     */
    public static boolean recycle(LRecyclable... recyclables) {
        for (LRecyclable recyclable : recyclables) {
            if (recyclable != null && !recyclable.isRecycled()) {
                recyclable.recycle();
            }
        }
        return true;
    }

    /**
     * 释放资源
     *
     * @param recyclables LReleasable
     * @return 是否回收成功
     */
    public static boolean recycle(LReleasable... recyclables) {
        for (LReleasable recyclable : recyclables) {
            if (recyclable != null) {
                recyclable.release();
            }
        }
        return true;
    }


    /**
     * 关闭读写流
     *
     * @param closeable 读写流
     * @return 关闭成功
     */
    public static boolean recycle(Closeable... closeable) {
        for (Closeable close : closeable) {
            if (close != null) {
                try {
                    close.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 回收bitmap
     *
     * @param bitmaps bitmap list
     * @return 回收成功
     */
    public static boolean recycle(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        return true;
    }


    /**
     * 取消 Handler 所有消息（包含delay消息）
     *
     * @param handler handler
     */
    public static void recycle(Handler handler) {
        if (handler != null) {
            // 清空当前Handler队列所有消息
            handler.removeCallbacksAndMessages(null);
        }
    }


    /**
     * 取消handler 指定消息
     *
     * @param handler handler
     * @param what    消息what
     */
    public static void recycle(Handler handler, int what) {
        if (handler != null) {
            handler.removeMessages(what);
        }
    }

    /**
     * 取消动画播放
     *
     * @param animator handler
     */
    public static void recycle(Animator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

}
