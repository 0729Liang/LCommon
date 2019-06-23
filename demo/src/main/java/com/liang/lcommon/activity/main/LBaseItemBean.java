package com.liang.lcommon.activity.main;

import android.app.Activity;

/**
 * @author : Amarao
 * CreateAt : 8:13 2019/1/19
 * Describe : Item数据类型
 */
public class LBaseItemBean {
    private String     mTitle = "";
    private String     mIntro;
    private int        mSrc;
    private BeanType   mType ;
    private ClickEvent mClickEvent;


    public enum BeanType {
        /**
         * 标记item类别，便于自动分流
         */
        UTILS,
        VIEW,
        BLOG
    }

    public interface ClickEvent {
        void onClick(Activity activity);
    }

    public LBaseItemBean(String intro, int src) {
        mIntro = intro;
        mSrc = src;
    }


    public LBaseItemBean(String title, String intro, int src) {
        mTitle = title;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public BeanType getType() {
        if(mType == null){
            mType = BeanType.UTILS;
        }
        return mType;
    }

    public void setType(BeanType type) {
        mType = type;
    }

    public ClickEvent getClickEvent() {
        return mClickEvent;
    }

    public void setClickEvent(ClickEvent clickEvent) {
        mClickEvent = clickEvent;
    }

}
