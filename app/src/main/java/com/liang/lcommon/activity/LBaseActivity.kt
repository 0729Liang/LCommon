package com.liang.lcommon.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.liang.lcommon.R
import com.liang.lcommon.exts.LRouter
import com.liang.lcommon.utils.LLogX
import com.liang.lcommon.view.LCircleSeekBar
import kotlinx.android.synthetic.main.activity_lbase.*

class LBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lbase)
        lseekbar.setStartTouchListener { view: LCircleSeekBar? -> LLogX.e("开始") }
        lseekbar.setStopTouchListener { view: LCircleSeekBar? -> LLogX.e("结束") }
        lseekbar.setChangeListener { view, pro ->
            progressTv.text = "进度" + pro.toInt()

            if (pro > 20) {
                lseekbar.lineCap = LCircleSeekBar.LCap.LSEEKBAR_LINE_CAP_ROUND
            } else {
                lseekbar.lineCap = LCircleSeekBar.LCap.LSEEKBAR_LINE_CAP_SQUARE
            }
            if (pro > 40) {
                lseekbar.thumbPosition = LCircleSeekBar.LThumbPosition.LSEEKBAR_THUMB_POSITION_MIDDLE
            } else {
                lseekbar.thumbPosition = LCircleSeekBar.LThumbPosition.LSEEKBAR_THUMB_POSITION_BELOW
            }
            if (pro > 60) {
                lseekbar.thumDrawable = ContextCompat.getDrawable(this, R.drawable.icon_lseekbar_point)
            } else {
                lseekbar.thumDrawable = ContextCompat.getDrawable(this, R.color.white)
            }

            if (pro > 80) {
                lseekbar.shape = LCircleSeekBar.LShape.LSEEKBAR_SHAPE_CIRCLE
            } else {
                lseekbar.shape = LCircleSeekBar.LShape.LSEEKBAR_SHAPE_RING
            }
        }
        lseekbar.setScdChangeListener { view, pro ->
            if (pro > 80) {
                lseekbar.shape = LCircleSeekBar.LShape.LSEEKBAR_SHAPE_CIRCLE
            } else {
                lseekbar.shape = LCircleSeekBar.LShape.LSEEKBAR_SHAPE_RING
            }
        }

        fadeInToOutBtn0.setOnClickListener({ ActivityStartAnimDemo.startTextActivity(this, 0) })
        translateleftInLeftOutBtn1.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 1) };
        translateleftInRightOutBtn2.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 2) }
        translateRightInRightOutBtn3.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 3) }
        translateRightInLeftOutBtn4.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 4) }
        translateBottomInBottomOutBtn5.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 5) }
        translateBottomInTopOutBtn6.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 6) }
        translateTopInTopOutBtn7.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 7) }
        translateTopInBottomOutBtn8.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 8) }
        scaleInScaleOutBtn9.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 9) }
        rotatePositiveInOutBtn10.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 10) }
        rotateReverseInOutBtn11.setOnClickListener() { ActivityStartAnimDemo.startTextActivity(this, 11) }

        rockerView.setOnClickListener() { LRouter.startRockerActivity(this) }
        settingView.setOnClickListener(){LRouter.startSettingViewActivity(this)}
    }

}
