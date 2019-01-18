package com.liang.lcommon.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.liang.lcommon.R;
import com.liang.lcommon.utils.LLogX;
import com.liang.lcommon.view.MyRockerView;

public class RockerActivity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, RockerActivity.class));
    }

    //虚拟摇杆
    private MyRockerView mRockerViewXY;
    private MyRockerView mRockerViewZ;
    private String       directionXY;
    private String       angleXY;
    private String       levelXY;
    private String       directionZ;
    private String       angleZ;
    private String       levelZ;
    //文本
    private TextView     directionXY_Text;
    private TextView     angleXY_Text;
    private TextView     levelXY_Text;
    private TextView     directionZ_Text;
    private TextView     angleZ_Text;
    private TextView     levelZ_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocker_activity);
        //界面初始化
        initView();
        //摇杆初始化
        initMyView();
        //摇杆点击事件
        initMyClick();
    }

    //界面初始化
    private void initView() {
        //文本
        directionXY_Text = (TextView) findViewById(R.id.directionXY_Text);
        angleXY_Text = (TextView) findViewById(R.id.angleXY_Text);
        levelXY_Text = (TextView) findViewById(R.id.levelXY_Text);

        directionZ_Text = (TextView) findViewById(R.id.directionZ_Text);
        angleZ_Text = (TextView) findViewById(R.id.angleZ_Text);
        levelZ_Text = (TextView) findViewById(R.id.levelZ_Text);
    }

    //摇杆初始化
    private void initMyView() {
        //方向有改变时回调
        mRockerViewXY = (MyRockerView) findViewById(R.id.rockerXY_View);//8方向
        mRockerViewZ = (MyRockerView) findViewById(R.id.rockerZ_View);//2方向
    }

    //摇杆点击事件
    private void initMyClick() {

        //xy轴
        //方向
        mRockerViewXY.setOnShakeListener(MyRockerView.DirectionMode.DIRECTION_8, new MyRockerView.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(MyRockerView.Direction direction) {
                if (direction == MyRockerView.Direction.DIRECTION_CENTER) {
                    directionXY = ("当前方向：中心");
                } else if (direction == MyRockerView.Direction.DIRECTION_DOWN) {
                    directionXY = ("当前方向：下");
                } else if (direction == MyRockerView.Direction.DIRECTION_LEFT) {
                    directionXY = ("当前方向：左");
                } else if (direction == MyRockerView.Direction.DIRECTION_UP) {
                    directionXY = ("当前方向：上");
                } else if (direction == MyRockerView.Direction.DIRECTION_RIGHT) {
                    directionXY = ("当前方向：右");
                } else if (direction == MyRockerView.Direction.DIRECTION_DOWN_LEFT) {
                    directionXY = ("当前方向：左下");
                } else if (direction == MyRockerView.Direction.DIRECTION_DOWN_RIGHT) {
                    directionXY = ("当前方向：右下");
                } else if (direction == MyRockerView.Direction.DIRECTION_UP_LEFT) {
                    directionXY = ("当前方向：左上");
                } else if (direction == MyRockerView.Direction.DIRECTION_UP_RIGHT) {
                    directionXY = ("当前方向：右上");
                }

                LLogX.e("XY轴" + directionXY);
                LLogX.e("-----------------------------------------------");
                directionXY_Text.setText(directionXY);
            }

            @Override
            public void onFinish() {

            }
        });
        //角度
        mRockerViewXY.setOnAngleChangeListener(new MyRockerView.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                angleXY = ("当前角度：" + angle);
                LLogX.e("XY轴" + angleXY);
                angleXY_Text.setText(angleXY);
            }

            @Override
            public void onFinish() {

            }
        });
        //级别
        mRockerViewXY.setOnDistanceLevelListener(new MyRockerView.OnDistanceLevelListener() {
            @Override
            public void onDistanceLevel(int level) {
                levelXY = ("当前距离级别：" + level);
                LLogX.e("XY轴" + levelXY);
                levelXY_Text.setText(levelXY);
            }
        });
        //z轴
        mRockerViewZ.setOnShakeListener(MyRockerView.DirectionMode.DIRECTION_2_VERTICAL, new MyRockerView.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(MyRockerView.Direction direction) {
                if (direction == MyRockerView.Direction.DIRECTION_UP) {
                    directionZ = ("当前方向：上");
                } else if (direction == MyRockerView.Direction.DIRECTION_DOWN) {
                    directionZ = ("当前方向：下");
                }
                LLogX.e("Z轴" + directionZ);
                LLogX.e("-----------------------------------------------");
                directionZ_Text.setText(directionZ);
            }

            @Override
            public void onFinish() {

            }
        });
        mRockerViewZ.setOnAngleChangeListener(new MyRockerView.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                angleZ = ("当前角度：" + angle);
                LLogX.e("Z轴" + angleZ);
                angleZ_Text.setText(angleZ);
            }

            @Override
            public void onFinish() {

            }
        });
        mRockerViewZ.setOnDistanceLevelListener(new MyRockerView.OnDistanceLevelListener() {
            @Override
            public void onDistanceLevel(int level) {
                levelZ = ("当前距离级别：" + level);
                LLogX.e("Z轴" + levelZ);
                levelZ_Text.setText(levelZ);
            }
        });
    }


}
