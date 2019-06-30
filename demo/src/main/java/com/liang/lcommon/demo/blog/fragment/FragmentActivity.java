package com.liang.lcommon.demo.blog.fragment;

import android.app.Activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.liang.lcommon.R;
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean;
import com.liang.lcommon.exts.LRouter;

public class FragmentActivity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, FragmentActivity.class));
    }

    public static LBaseItemBean newItem() {
        String intro = "FragmentDemo界面";
        int src = R.drawable.demo_fragment;
        LBaseItemBean bean = new LBaseItemBean(intro, src);
        bean.setClickEvent(getClickEvent());
        bean.setType(LBaseItemBean.BeanType.BLOG);
        return bean;
    }

    public static LBaseItemBean.ClickEvent getClickEvent() {
        return LRouter::startFragmentDemo;
    }


    private Button  mButton;
    private Boolean isAnother = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_fragment_activity_a);
        mButton = findViewById(R.id.demo_left_btn);
        mButton.setOnClickListener(view -> {
            if (isAnother) {
                repleaceFragment(new RightFragment());
                isAnother = false;
            } else {
                repleaceFragment(new AnotherRightFragment());
                isAnother = true;
            }
        });

        repleaceFragment(new RightFragment());
    }

    private void repleaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.demo_right_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
