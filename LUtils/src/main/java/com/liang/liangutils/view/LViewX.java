package com.liang.liangutils.view;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liang.liangutils.utils.LSizeX;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Field;

/**
 * @author : amarao
 * @date: 2019-06-30
 * describer: LViewX
 */
public class LViewX {




    /**
     * 设置 TabLayout Indicator
     *
     * @param tabLayout
     */
    public static void setIndicatorWidth(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(() -> {
            try {
                //拿到tabLayout的mTabStrip属性
                LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                int dp10 = LSizeX.dp2px(20);
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);
                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                    Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);
                    TextView mTextView = (TextView) mTextViewField.get(tabView);
                    tabView.setPadding(0, 0, 0, 0);
                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    int width;
                    width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }
                    //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width;
                    params.leftMargin = dp10;
                    params.rightMargin = dp10;
                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 参数leftdip， rightDip是设置tab左右的padding值，就是间距大小
     */
    public static void setIndicatorWidth(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (tabStrip == null) {
            return;
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        if (llTab == null) {
            return;
        }
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    /**
     * 初始化 SmartRefresh
     *
     * @param refreshLayout
     * @param loadMoreListener
     * @param refreshListener
     */
    public static void initSmartRefresh(SmartRefreshLayout refreshLayout,
                                        OnLoadMoreListener loadMoreListener,
                                        OnRefreshListener refreshListener) {
        refreshLayout.setEnableScrollContentWhenLoaded(true); // 是否在加载完成时滚动列表显示新的内容
        if (loadMoreListener != null) {
            refreshLayout.setFooterHeight(40);
            refreshLayout.setRefreshFooter(new LCustomRefreshFooter(refreshLayout.getContext()));
            refreshLayout.setOnLoadMoreListener(v -> v.getLayout().postDelayed(() ->
                    loadMoreListener.onLoadMore(v), 0));
            refreshLayout.setEnableLoadMore(true);
        } else {
            refreshLayout.setEnableLoadMore(false);
        }

        if (refreshListener != null) {
            refreshLayout.setRefreshHeader(new LCustomRefreshHeader(refreshLayout.getContext()));
            refreshLayout.setOnRefreshListener(refreshListener);
            refreshLayout.setEnableRefresh(true);
        } else {
            refreshLayout.setEnableRefresh(false);
        }
    }
}
