package com.liang.lcommon.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liang.lcommon.R;

/**
 * @author : Amarao
 * CreateAt : 15:17 2019/1/10
 * Describe :
 */
public class LTitleView extends LinearLayout {

    private View      mParentView;
    private View      mLineView;
    private ImageView mLeftView;
    private TextView  mRightTextView;
    private ImageView mRightImgView;
    private TextView  mCenterView;

    public LTitleView(Context context) {
        this(context, null);
    }

    public LTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.lsetting_title, this);
        if (isInEditMode()) {
            return;
        }
        initViews();
    }

    private void initViews() {
        setOrientation(LinearLayout.VERTICAL);
        mParentView = findViewById(R.id.title_bar_parent);
        mLineView = findViewById(R.id.line_view);
        mLeftView = findViewById(R.id.title_left_tv);
        mRightTextView = findViewById(R.id.title_right_tv);
        mCenterView = findViewById(R.id.tv_titlebar_center);
        mRightImgView = findViewById(R.id.title_right_image);
        mLineView.setVisibility(GONE); // 去掉所有分割线

        mRightTextView.setVisibility(INVISIBLE);
        mRightImgView.setVisibility(INVISIBLE);
    }

    public View getParentView() {
        return mParentView;
    }

    public View getLineView() {
        return mLineView;
    }

    public ImageView getLeftView() {
        return mLeftView;
    }

    public TextView getRightTextView() {
        return mRightTextView;
    }

    public TextView getCenterView() {
        return mCenterView;
    }

    /**
     * 设置title标题字体大小
     */
    public void setCenterViewSize(float size) {
        mCenterView.setTextSize(size);
    }

    public ImageView getRightImgView() {
        return mRightImgView;
    }

    public void setRightImgIconRes(int res) {
        mRightImgView.setVisibility(VISIBLE);
        mRightImgView.setImageResource(res);
        mRightTextView.setVisibility(GONE);
    }

    public void setRightText(String text) {
        mRightTextView.setVisibility(VISIBLE);
        mRightTextView.setText(text);
        mRightImgView.setVisibility(GONE);
    }

    public void setClickLeftFinish(Activity activity) {
        mLeftView.setOnClickListener((v) -> activity.finish());
    }

    public void setTitle(String title) {
        mCenterView.setText(title);
    }
}

