package com.liang.lcommon.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author : Amarao
 * CreateAt : 8:13 2019/1/19
 * Describe :
 */
public class LBaseItemBean {
    private String mIntro;
    private int    mSrc;


    public interface ClickEvent {
        void onClick(Activity activity);
    }

    public LBaseItemBean(String intro, int src) {
        mIntro = intro;
        mSrc = src;
    }


    public String getIntro() {
        return mIntro;
    }

    public void setIntro(String introduce) {
        mIntro = introduce;
    }

    public int getSrc() {
        return mSrc;
    }

    public void setSrc(int src) {
        mSrc = src;
    }


}
