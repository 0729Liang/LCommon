package com.liang.lcommon.able;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 可以被回收的
 */
public interface LRecyclable {

    void recycle();

    boolean isRecycled();
}
