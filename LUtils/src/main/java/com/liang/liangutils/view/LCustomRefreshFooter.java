package com.liang.liangutils.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liang.liangutils.R;
import com.liang.liangutils.utils.LSizeX;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author : amarao
 * @date: 2019-06-30
 * describer: LCustomRefreshFooter
 */
public class LCustomRefreshFooter extends LinearLayout implements RefreshFooter {

    private GifImageView mGifImageView;
    private TextView     mNoMoreDataTv;

    public LCustomRefreshFooter(Context context) {
        super(context, null, 0);

        View view = View.inflate(context, R.layout.lcomm_refresh_footer, this);

        mGifImageView = view.findViewById(R.id.refresh_footer_iv);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mGifImageView.getLayoutParams();
        layoutParams.width = (int) (LSizeX.WIDTH * 63f / 360);
        layoutParams.height = (int) (LSizeX.HEIGHT * 13f / 640);
        mGifImageView.setLayoutParams(layoutParams);

    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
