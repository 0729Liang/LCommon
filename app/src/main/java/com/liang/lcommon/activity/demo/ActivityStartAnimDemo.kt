package com.liang.lcommon.activity.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.liang.lcommon.R
import com.liang.lcommon.activity.LBaseItemBean
import com.liang.lcommon.app.LAppActivity
import com.liang.lcommon.exts.LRouter
import com.liang.lcommon.utils.LActivityAnimX
import com.liang.lcommon.utils.LResourceX
import kotlinx.android.synthetic.main.demo_activity_start_anim.*

class ActivityStartAnimDemo : LAppActivity() {

    companion object {

        val ANIM_TYPE = "ANIM_TYPE"

        fun startActivity(activity: Activity, anim: Anim) {
            val intent = Intent(activity, ActivityStartAnimDemo::class.java)
            intent.putExtra(ANIM_TYPE, anim.type)
            activity.startActivity(intent)
            activityAnim(anim.type, true, activity)
        }

        fun getItem(): LBaseItemBean {
            val intro = "这是一个Activity的启动动画"
            val src = R.drawable.demo_activity_start_anim
            return LBaseItemBean(intro, src)
        }

        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startActivityAnimDemo(it) }
        }

        private fun activityAnim(type: Int, start: Boolean, activity: Activity) {
            when (type) {
                0 -> if (start) {
                    LActivityAnimX.fadeInAnim(activity)
                } else {
                    LActivityAnimX.fadeOutAnim(activity)
                }
                1 -> if (start) {
                    LActivityAnimX.translateLeftIntAnim(activity)
                } else {
                    LActivityAnimX.translateLeftOutAnim(activity)
                }
                2 -> if (start) {
                    LActivityAnimX.translateLeftIntAnim(activity)
                } else {
                    LActivityAnimX.translateRightOutAnim(activity)
                }
                3 -> if (start) {
                    LActivityAnimX.translateRightInAnim(activity)
                } else {
                    LActivityAnimX.translateRightOutAnim(activity)
                }
                4 -> if (start) {
                    LActivityAnimX.translateRightInAnim(activity)
                } else {
                    LActivityAnimX.translateLeftOutAnim(activity)
                }
                5 -> if (start) {
                    LActivityAnimX.translateBottomInAnim(activity)
                } else {
                    LActivityAnimX.translateBottomOutAnim(activity)
                }
                6 -> if (start) {
                    LActivityAnimX.translateBottomInAnim(activity)
                } else {
                    LActivityAnimX.translateTopOutAnim(activity)
                }
                7 -> if (start) {
                    LActivityAnimX.translateTopInAnim(activity)
                } else {
                    LActivityAnimX.translateTopOutAnim(activity)
                }
                8 -> if (start) {
                    LActivityAnimX.translateTopInAnim(activity)
                } else {
                    LActivityAnimX.translateBottomOutAnim(activity)
                }
                9 -> if (start) {
                    LActivityAnimX.scaleInAnim(activity)
                } else {
                    LActivityAnimX.scaleOutAnim(activity)
                }
                10 -> if (start) {
                    LActivityAnimX.rotatePositiveInAnim(activity)
                } else {
                    LActivityAnimX.rotatePositiveOutAnim(activity)
                }
                11 -> if (start) {
                    LActivityAnimX.rotateReverseInAnim(activity)
                } else {
                    LActivityAnimX.rotateReverseOutAnim(activity)
                }
            }
        }
    }

    private var mType: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_start_anim)
        mType = intent.getIntExtra(ANIM_TYPE, -1)
        if (mType != -1) {
            contentPanel.setBackgroundColor(LResourceX.getColor(this, R.color.redPrimary))
        } else {
            contentPanel.setBackgroundColor(LResourceX.getColor(this, R.color.white))
        }


        fadeInToOutBtn0.setOnClickListener({ LRouter.startActivityAnimDemo(this, Anim.ANIM_FADE_IN_FADE_OUT) })
        translateleftInLeftOutBtn1.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_LEFT_INT_LEFT_OUT) };
        translateleftInRightOutBtn2.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_LEFT_IN_RIGHT_OUT) }
        translateRightInRightOutBtn3.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_RIGHT_IN_RIGHT_OUT) }
        translateRightInLeftOutBtn4.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_RIGHT_IN_LEFT_OUT) }
        translateBottomInBottomOutBtn5.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_BOTTOM_IN_BOTTOM_OUT) }
        translateBottomInTopOutBtn6.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_BOTTOM_IN_TOP_OUT) }
        translateTopInTopOutBtn7.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_TOP_IN_TOP_OUT) }
        translateTopInBottomOutBtn8.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_TOP_IN_BOTTOM_OUT) }
        scaleInScaleOutBtn9.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_SCALE_IN_SCALE_OUT) }
        rotatePositiveInOutBtn10.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_POS_ROTATE_IN_OUT) }
        rotateReverseInOutBtn11.setOnClickListener() { LRouter.startActivityAnimDemo(this, Anim.ANIM_REV_ROTATE_IN_OUT) }

    }

    enum class Anim(val type: Int) {

        /**
         * 程序的启动动画
         */
        ANIM_FADE_IN_FADE_OUT(0),
        ANIM_LEFT_INT_LEFT_OUT(1),
        ANIM_LEFT_IN_RIGHT_OUT(2),
        ANIM_RIGHT_IN_RIGHT_OUT(3),
        ANIM_RIGHT_IN_LEFT_OUT(4),
        ANIM_BOTTOM_IN_BOTTOM_OUT(5),
        ANIM_BOTTOM_IN_TOP_OUT(6),
        ANIM_TOP_IN_TOP_OUT(7),
        ANIM_TOP_IN_BOTTOM_OUT(8),
        ANIM_SCALE_IN_SCALE_OUT(9),
        ANIM_POS_ROTATE_IN_OUT(10),
        ANIM_REV_ROTATE_IN_OUT(11)
    }

    override fun finish() {
        super.finish()
        activityAnim(mType, false, this)
    }
}
