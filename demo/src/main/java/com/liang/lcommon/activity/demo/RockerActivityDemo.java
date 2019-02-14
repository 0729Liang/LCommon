package com.liang.lcommon.activity.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.liang.lcommon.R;
import com.liang.lcommon.activity.LBaseItemBean;
import com.liang.lcommon.ant.ClassLog;
import com.liang.lcommon.app.LAppActivity;
import com.liang.lcommon.exts.LRouter;
import com.liang.liangutils.view.LRockerViewV2;
import com.march.common.exts.LogX;

@ClassLog
public class RockerActivityDemo extends LAppActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, RockerActivityDemo.class));
    }

    public static LBaseItemBean getItem() {
        String intro = "虚拟摇杆\n1.仿王者荣耀摇杆\n2.自定义虚拟摇杆\n  锚点偏移级别 0-9\n  可监听角度变化\n摇杆方向可设置为二四八个方向";
        int src = R.drawable.demo_rocker_view;
        return new LBaseItemBean(intro, src);
    }

    public static LBaseItemBean.ClickEvent getClickEvent() {
        return LRouter::startRockerActivity;
    }

    //虚拟摇杆
    private LRockerViewV2 mRockerViewXY;
    private LRockerViewV2 mRockerViewZ;
    private String        directionXY;
    private String        angleXY;
    private String        levelXY;
    private String        directionZ;
    private String        angleZ;
    private String        levelZ;
    //文本
    private TextView      directionXY_Text;
    private TextView      angleXY_Text;
    private TextView      levelXY_Text;
    private TextView      directionZ_Text;
    private TextView      angleZ_Text;
    private TextView      levelZ_Text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_rocker_activity);
        //界面初始化
        initView();
        //摇杆初始化
        initMyView();
        //摇杆点击事件
        initMyClick();
        //创建列表并添加数据
    }

    //界面初始化
    private void initView() {
        //文本
        directionXY_Text = findViewById(R.id.directionXY_Text);
        angleXY_Text = findViewById(R.id.angleXY_Text);
        levelXY_Text = findViewById(R.id.levelXY_Text);

        directionZ_Text = findViewById(R.id.directionZ_Text);
        angleZ_Text = findViewById(R.id.angleZ_Text);
        levelZ_Text = findViewById(R.id.levelZ_Text);
    }

    //摇杆初始化
    public void initMyView() {
        //方向有改变时回调
        mRockerViewXY = findViewById(R.id.rockerXY_View);//8方向
        mRockerViewZ = findViewById(R.id.rockerZ_View);//2方向
    }

    //摇杆点击事件
    private void initMyClick() {

        //xy轴
        //方向
        mRockerViewXY.setOnShakeListener(LRockerViewV2.DirectionMode.DIRECTION_8, new LRockerViewV2.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(LRockerViewV2.Direction direction) {
                if (direction == LRockerViewV2.Direction.DIRECTION_CENTER) {
                    directionXY = ("当前方向：中心");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN) {
                    directionXY = ("当前方向：下");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_LEFT) {
                    directionXY = ("当前方向：左");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_UP) {
                    directionXY = ("当前方向：上");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_RIGHT) {
                    directionXY = ("当前方向：右");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN_LEFT) {
                    directionXY = ("当前方向：左下");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN_RIGHT) {
                    directionXY = ("当前方向：右下");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_UP_LEFT) {
                    directionXY = ("当前方向：左上");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_UP_RIGHT) {
                    directionXY = ("当前方向：右上");
                }

                LogX.e("XY轴" + directionXY);
                LogX.e("-----------------------------------------------");
                directionXY_Text.setText(directionXY);
            }

            @Override
            public void onFinish() {

            }
        });
        //角度
        mRockerViewXY.setOnAngleChangeListener(new LRockerViewV2.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                angleXY = ("当前角度：" + angle);
                LogX.e("XY轴" + angleXY);
                angleXY_Text.setText(angleXY);
            }

            @Override
            public void onFinish() {

            }
        });
        //级别
        mRockerViewXY.setOnDistanceLevelListener(level -> {
            levelXY = ("当前距离级别：" + level);
            LogX.e("XY轴" + levelXY);
            levelXY_Text.setText(levelXY);
        });
        //z轴
        mRockerViewZ.setOnShakeListener(LRockerViewV2.DirectionMode.DIRECTION_2_VERTICAL, new LRockerViewV2.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(LRockerViewV2.Direction direction) {
                if (direction == LRockerViewV2.Direction.DIRECTION_UP) {
                    directionZ = ("当前方向：上");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN) {
                    directionZ = ("当前方向：下");
                }
                LogX.e("Z轴" + directionZ);
                LogX.e("-----------------------------------------------");
                directionZ_Text.setText(directionZ);
            }

            @Override
            public void onFinish() {

            }
        });
        mRockerViewZ.setOnAngleChangeListener(new LRockerViewV2.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                angleZ = ("当前角度：" + angle);
                LogX.e("Z轴" + angleZ);
                angleZ_Text.setText(angleZ);
            }

            @Override
            public void onFinish() {

            }
        });
        mRockerViewZ.setOnDistanceLevelListener(level -> {
            levelZ = ("当前距离级别：" + level);
            LogX.e("Z轴" + levelZ);
            levelZ_Text.setText(levelZ);
        });
    }


}
