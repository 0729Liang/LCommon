package com.liang.lcommon.able;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 可以被释放的
 */
public interface Recyclable {

    void recycle();

    boolean isRecycled();
}