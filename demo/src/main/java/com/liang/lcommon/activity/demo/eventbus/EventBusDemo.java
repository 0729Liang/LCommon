package com.liang.lcommon.activity.demo.eventbus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liang.lcommon.R;
import com.liang.lcommon.activity.LBaseItemBean;
import com.liang.lcommon.activity.demo.RockerActivityDemo;
import com.liang.lcommon.app.LAppActivity;
import com.liang.lcommon.exts.LRouter;
import com.liang.liangutils.msg.Exts;
import com.liang.liangutils.utils.LLogX;
import com.squareup.haha.perflib.Main;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusDemo extends LAppActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, EventBusDemo.class));
    }

    public static LBaseItemBean getItem() {
        String intro = "EventBus/n";
        int src = R.drawable.demo_eventbus;
        return new LBaseItemBean(intro, src);
    }

    public static LBaseItemBean.ClickEvent getClickEvent() {
        return LRouter::startEventBusDemo;
    }

    Button   mOneBtn;
    Button   mTwoBtn;
    Button   mThreeBtn;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_event_bus_activty);
        initView();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Exts.registerEvent(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Exts.unRegisterEvent(this);
//    }

    void initView() {
        mOneBtn = findViewById(R.id.demoSendEventBtn1);
        mTwoBtn = findViewById(R.id.demoSendEventBtn2);
        mThreeBtn = findViewById(R.id.demoSendEventBtn3);
        mTextView = findViewById(R.id.demoDisplayText);

        mOneBtn.setOnClickListener(v -> {
            DemoEvent.postEventBusDemoOne();
        });

        mTwoBtn.setOnClickListener(v -> {
            DemoEvent.postEventBusDemoTwo(123);
        });

        mThreeBtn.setOnClickListener(v -> {
            DemoEvent.postEventBusDemoThree("String");
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusDemoEvent(DemoEvent event) {
        String msg = "等待事件:";
        switch (event.msg) {
            case DemoEvent.EVENT_SEND_ONE:
                mTextView.setText(msg + " one 无后缀信息");
                break;
            case DemoEvent.EVENT_SEND_TWO:
                mTextView.setText(msg + " two " + event.getNumberSuffix());
                break;
            case DemoEvent.EVENT_SEND_THREE:
                mTextView.setText(msg + " three " + event.getStringSuffix());
                break;
            default:
                mTextView.setText("错误");
        }
    }
}
