package com.liang.lcommon.demo.blog.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import com.liang.lcommon.R;
import com.liang.liangutils.able.LCustomerNoParam;
import com.liang.liangutils.utils.LSizeX;

/**
 * @author : Amarao
 * CreateAt : 16:22 2019/3/4
 * Describe : 更多操作popop window demo
 */
public class LPopupWindow extends PopupWindow {

    private boolean mIsShowing = false;

    private LPopupWindow(Context context) {
        super(context);
    }

    public static LPopupWindow create(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_popup_window, null);
        LPopupWindow window = new LPopupWindow(context);
        window.setContentView(view); // 设置内容
        //window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        return window;
    }

    public LPopupWindow show(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        this.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);// 需要先测量，才可以得到宽高getMeasuredWidth
        // 默认显示在view的正下方
        this.showAsDropDown(view, 0 - this.getContentView().getMeasuredWidth() / 4, -this.getContentView().getMeasuredHeight() - view.getHeight() - LSizeX.dp2px(5));
        mIsShowing = true;
        return this;
    }

    public LPopupWindow hide() {
        this.dismiss();
        mIsShowing = false;
        return this;
    }

    public boolean isShow(){
        return mIsShowing;
    }
    public LPopupWindow addTestPauseClickEvent(LCustomerNoParam customer) {
        View view = this.getContentView().findViewById(R.id.mvWindowTestPause);
        view.setOnClickListener(v -> customer.accept());
        return this;
    }

    public LPopupWindow addTestResumeClickEvent(LCustomerNoParam customer) {
        View view = this.getContentView().findViewById(R.id.mvWindowTestResume);
        view.setOnClickListener(v -> customer.accept());
        return this;
    }

    public LPopupWindow addStopClickEvent(LCustomerNoParam customer) {
        View view = this.getContentView().findViewById(R.id.mvWindowStop);
        view.setOnClickListener(v -> customer.accept());
        return this;
    }

    public LPopupWindow addQueryDataClickEvent(LCustomerNoParam customer) {
        View view = this.getContentView().findViewById(R.id.mvWindowQueryData);
        view.setOnClickListener(v -> customer.accept());
        return this;
    }

    public LPopupWindow addQueryChannelStatusClickEvent(LCustomerNoParam customer) {
        View view = this.getContentView().findViewById(R.id.mvWindowQueryChannelStatus);
        view.setOnClickListener(v -> customer.accept());
        return this;
    }
}
