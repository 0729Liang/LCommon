package com.liang.liangutils.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.liangutils.R;

/**
 * @author : Amarao
 * CreateAt : 8:13 2019/1/20
 * Describe : 个人中心的箭头组件
 */
public class LSettingArrow extends ConstraintLayout {

    private ImageView mTitleIconIv;
    private TextView  mTitleIntroTv;
    private TextView  mNumberTv;
    private ImageView mArrowIv;
    private View      mLineView;

    private Drawable mSrc;
    private String   mText;
    private String   mNumber;
    private Drawable mArrow;

    public LSettingArrow(Context context) {
        this(context, null);
    }

    public LSettingArrow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        initAttr(context, attrs);
    }

    public LSettingArrow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lsetting_arrow_layout, this);
        mTitleIconIv = view.findViewById(R.id.larrow_title_icon);
        mTitleIntroTv = view.findViewById(R.id.larrow_title_intro);
        mNumberTv = view.findViewById(R.id.larrow_number);
        mArrowIv = view.findViewById(R.id.larrow_icon);
    }


    private void initAttr(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LSettingArrow);
        mSrc = typedArray.getDrawable(R.styleable.LSettingArrow_lsetting_arrow_title_icon);
        mText = typedArray.getString(R.styleable.LSettingArrow_lsetting_arrow_intro);
        mNumber = typedArray.getString(R.styleable.LSettingArrow_lsetting_arrow_number);
        mArrow = typedArray.getDrawable(R.styleable.LSettingArrow_lsetting_arrow_icon);
        typedArray.recycle();

        if (mSrc != null) {
            mTitleIconIv.setImageDrawable(mSrc);
        }
        if (mText != null) {
            mTitleIntroTv.setText(mText);
        }
        if (mNumber != null) {
            mNumberTv.setText(mNumber);
        }
        if (mArrow != null) {
            mArrowIv.setImageDrawable(mArrow);
        }
    }

    public ImageView getTitleIconIv() {
        return mTitleIconIv;
    }

    public TextView getTitleIntroTv() {
        return mTitleIntroTv;
    }

    public TextView getNumberTv() {
        return mNumberTv;
    }

    public ImageView getArrowIv() {
        return mArrowIv;
    }

    public View getLineView() {
        return mLineView;
    }


    public void setTitleIcon(int resId) {
        mTitleIconIv.setImageResource(resId);
    }

    public void setTitle(String text) {
        mTitleIntroTv.setText(text);
    }

    public void setNumber(String text) {
        mNumberTv.setText(text);
    }

    public void setArrow(int resId) {
        mArrowIv.setImageResource(resId);
    }
}
