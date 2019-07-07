package com.liang.liangutils.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.liang.liangutils.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * @author Amarao
 * @date 2018/9/12
 * 下拉刷新动画
 * 调用举例:smartRefresh.setRefreshHeader(new CustomRefreshHeader(this));
 */
public class LCustomRefreshHeader extends LinearLayout implements RefreshHeader {

    private  LinearLayout mLayout;
    private ImageView mImage;
    private AnimationDrawable pullDownAnim;
    private AnimationDrawable refreshingAnim;

    private boolean hasSetPullDownAnim = false;

    public LCustomRefreshHeader(Context context) {
        super(context, null, 0);
        View view = View.inflate(context, R.layout.lcomm_refresh_header, this);
        mImage = (ImageView) view.findViewById(R.id.hot_iv_refresh_header);
        mLayout =  view.findViewById(R.id.layout_refresh_header);
    }

    public LCustomRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LCustomRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.lcomm_refresh_header, this);
        mImage = (ImageView) view.findViewById(R.id.hot_iv_refresh_header);
        mLayout =  view.findViewById(R.id.layout_refresh_header);
    }


    /**
     * 获取真实视图（必须返回，不能为null）
     */
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    /**
     * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    /**
     * 设置主题颜色 （如果自定义的Header没有注意颜色，本方法可以什么都不处理）
     *
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Override
    public void setPrimaryColors(int... colors) {

    }


    /**
     * 尺寸定义初始化完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     *
     * @param kernel        RefreshKernel 核心接口（用于完成高级Header功能）
     * @param height        HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    /**
     * 开始动画（开始刷新或者开始加载动画）
     *
     * @param refreshLayout RefreshLayout
     * @param height        HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    /**
     * 状态改变时调用。在这里切换第三阶段的动画卖萌小人
     *
     * @param refreshLayout
     * @param oldState
     * @param newState
     */
    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh:
                /*
                 * 下拉刷新开始。正在下拉还没松手时调用
                 * 每次重新下拉时，将图片资源重置为小人的大脑袋*/
                mImage.setImageResource(R.drawable.anim_pull_end);
                break;
            case Refreshing:
                /*
                 * 正在刷新。只调用一次
                 * 状态切换为正在刷新状态时，设置图片资源为小人卖萌的动画并开始执行*/
                mImage.setImageResource(R.drawable.anim_pull_refreshing);
                refreshingAnim = (AnimationDrawable) mImage.getDrawable();
                refreshingAnim.start();
                break;
            case ReleaseToRefresh:
                break;
            default:
        }
    }

    /**
     * 手指拖动下拉（会连续多次调用，添加isDragging并取代之前的onPulling、onReleasing）
     *
     * @param isDragging    true 手指正在拖动 false 回弹动画
     * @param percent       下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+maxDragHeight) / footerHeight )
     * @param offset        下拉的像素偏移量  0 - offset - (footerHeight+maxDragHeight)
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        //LogX.e("percent: " + percent);

        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent < 1) {
            mImage.setScaleX(percent);
            mImage.setScaleY(percent);

            //是否执行过翻跟头动画的标记
            if (hasSetPullDownAnim) {
                hasSetPullDownAnim = false;
            }
        }

        //当下拉的高度达到Header高度100%时，开始加载正在下拉的初始动画，即翻跟头
        if (percent >= 1.0) {
            //因为这个方法是不停调用的，防止重复
            if (!hasSetPullDownAnim) {
                mImage.setImageResource(R.drawable.anim_pull_end);
                pullDownAnim = (AnimationDrawable) mImage.getDrawable();
                pullDownAnim.start();

                hasSetPullDownAnim = true;
            }
        }
    }

    /**
     * 动画结束
     *
     * @param refreshLayout RefreshLayout
     * @param success       数据是否成功刷新或加载
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        // 结束动画
        if (pullDownAnim != null && pullDownAnim.isRunning()) {
            pullDownAnim.stop();
        }
        if (refreshingAnim != null && refreshingAnim.isRunning()) {
            refreshingAnim.stop();
        }
        //重置状态
        hasSetPullDownAnim = false;

        /*if (success ){
            LogX.e("TRUE");
        }else {
            LogX.e("FALSE");
        }*/
        return 0;
    }

    /**
     * 释放时刻（调用一次，将会触发加载）
     *
     * @param refreshLayout RefreshLayout
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    /**
     * 是否支持水平方向的拖动（将会影响到onHorizontalDrag的调用）
     *
     * @return 水平拖动需要消耗更多的时间和资源，所以如果不支持请返回false
     */
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

}
