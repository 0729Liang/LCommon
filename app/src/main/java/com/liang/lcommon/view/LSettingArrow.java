package com.liang.lcommon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.lcommon.R;

/**
 * CreateAt : 2018/11/7
 * Describe :
 *
 * @author hubery
 */
public class LSettingArrow extends ConstraintLayout {

    private ImageView mCoverIv;
    private TextView  mTitleTv;
    private TextView  mContentTv;
    private ImageView mArrowIv;

    private String   mText;
    private Drawable mSrc;
    private String   mNumber;
    private Drawable mArrow;


    public LSettingArrow(Context context) {
        this(context, null);
    }

    public LSettingArrow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public LSettingArrow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        initViews(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PersonalBodyView);
        mSrc = typedArray.getDrawable(R.styleable.PersonalBodyView_mSrc);
        mText = typedArray.getString(R.styleable.PersonalBodyView_mText);
        mNumber = typedArray.getString(R.styleable.PersonalBodyView_mNumber);
        mArrow = typedArray.getDrawable(R.styleable.PersonalBodyView_mArrow);
        typedArray.recycle();

//        if (isInEditMode()) {
//            return;
//        }

        if (mSrc != null) {
            mCoverIv.setImageDrawable(mSrc);
        }
        if (mText != null) {
            mTitleTv.setText(mText);
        }
        if (mNumber != null) {
            mContentTv.setText(mNumber);
        }
        if (mArrow != null) {
            mArrowIv.setImageDrawable(mArrow);
        }
    }

    private void initViews(Context context) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lsetting_arrow_layout, this);
        //inflate(context, R.layout.personal_body_item, this);
        mCoverIv = view.findViewById(R.id.personal_icon);
        mTitleTv = view.findViewById(R.id.personal_title_tv);
        mContentTv = view.findViewById(R.id.personal_content_tv);
        mArrowIv = view.findViewById(R.id.personal_end_tv);
    }

    public void setCoverIv(int resId) {
        mCoverIv.setImageResource(resId);
    }

    public void setTitle(String text) {
        mTitleTv.setText(text);
    }

    public void setContent(String text) {
        mContentTv.setText(text);
    }

    public void hideArrow() {
        mArrowIv.setVisibility(View.GONE);
    }

    public void hideCount() {
        mContentTv.setVisibility(View.GONE);
    }

    public void hideCover() {
        mContentTv.setVisibility(View.GONE);
    }
}
