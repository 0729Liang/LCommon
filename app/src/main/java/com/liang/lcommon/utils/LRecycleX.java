package com.liang.lcommon.utils;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Handler;

import com.liang.lcommon.able.Destroyable;
import com.liang.lcommon.able.Recyclable;
import com.liang.lcommon.able.Releasable;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 回收资源的工具类
 */
public class LRecycleX {


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


    // 回收资源
    public static boolean recycle(Recyclable... recyclables) {
        for (Recyclable recyclable : recyclables) {
            if (recyclable != null && !recyclable.isRecycled()) {
                recyclable.recycle();
            }
        }
        return true;
    }

    /**
     * 回收资源
     *
     * @param recyclables releasable
     * @return 是否回收成功
     */
    public static boolean recycle(Releasable... recyclables) {
        for (Releasable recyclable : recyclables) {
            if (recyclable != null) {
                recyclable.release();
            }
        }
        return true;
    }

    /**
     * 回收资源
     *
     * @param destroyables Destroyable
     * @return 是否回收成功
     */
    public static boolean recycle(Destroyable... destroyables) {
        for (Destroyable destroyable : destroyables) {
            if (destroyable != null) {
                destroyable.onDestroy();
            }
        }
        return true;
    }


    /**
     * 取消 Handler 事件
     *
     * @param handler handler
     */
    public static void recycle(Handler handler) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
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
