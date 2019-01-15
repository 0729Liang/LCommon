package com.liang.lcommon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.liang.lcommon.utils.LActivityAnimX;

public class TestActivity extends AppCompatActivity {

    public static final String ANIM_TYPE = "ANIM_TYPE";

    public static void startTextActivity(Activity activity, int type) {
        Intent intent = new Intent(activity, TestActivity.class);
        intent.putExtra(ANIM_TYPE, type);
        activity.startActivity(intent);
        activityAnim(type, true, activity);
    }


    public enum Anim {
        /**
         * 程序的启动动画
         */
        ANIM_TRANSLATE,
        ANIM_FADE,
        ANIM_BOTTOM;

        private Anim() {

        }
    }

    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mType = getIntent().getIntExtra(ANIM_TYPE, 0);

    }

    @Override
    public void finish() {
        super.finish();
        activityAnim(mType, false, this);
    }

    private static void activityAnim(int anim, boolean start, Activity activity) {
        switch (anim) {
            case 0:
                //LActivityAnimX.fadeInFadeOutActivity(activity);
                if (start) {
                    LActivityAnimX.fadeInAnim(activity);
                } else {
                    LActivityAnimX.fadeOutAnim(activity);
                }
                break;
            case 1:
                if (start) {
                    LActivityAnimX.translateLeftIntAnim(activity);
                } else {
                    LActivityAnimX.translateLeftOutAnim(activity);
                }
                break;
            case 2:
                if (start) {
                    LActivityAnimX.translateLeftIntAnim(activity);
                } else {
                    LActivityAnimX.translateRightOutAnim(activity);
                }
                break;
            case 3:
                if (start) {
                    LActivityAnimX.translateRightInAnim(activity);
                } else {
                    LActivityAnimX.translateRightOutAnim(activity);
                }
                break;
            case 4:
                if (start) {
                    LActivityAnimX.translateRightInAnim(activity);
                } else {
                    LActivityAnimX.translateLeftOutAnim(activity);
                }
                break;
            case 5:
                if (start) {
                    LActivityAnimX.translateBottomInAnim(activity);
                } else {
                    LActivityAnimX.translateBottomOutAnim(activity);
                }
                break;
            case 6:
                if (start) {
                    LActivityAnimX.translateBottomInAnim(activity);
                } else {
                    LActivityAnimX.translateTopOutAnim(activity);
                }
                break;
            case 7:
                if (start) {
                    LActivityAnimX.translateTopInAnim(activity);
                } else {
                    LActivityAnimX.translateTopOutAnim(activity);
                }
                break;
            case 8:
                if (start) {
                    LActivityAnimX.translateTopInAnim(activity);
                } else {
                    LActivityAnimX.translateBottomOutAnim(activity);
                }
                break;
            case 9:
                if (start) {
                    LActivityAnimX.scaleInAnim(activity);
                } else {
                    LActivityAnimX.scaleOutAnim(activity);
                }
                break;
            case 10:
                if (start) {
                    LActivityAnimX.rotatePositiveInAnim(activity);
                } else {
                    LActivityAnimX.rotatePositiveOutAnim(activity);
                }
                break;
            case 11:
                if (start) {
                    LActivityAnimX.rotateReverseInAnim(activity);
                } else {
                    LActivityAnimX.rotateReverseOutAnim(activity);
                }
                break;
            default:
        }
    }
}
