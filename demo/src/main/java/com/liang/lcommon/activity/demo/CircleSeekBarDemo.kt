package com.liang.lcommon.activity.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.liang.lcommon.R
import com.liang.lcommon.activity.LBaseItemBean
import com.liang.lcommon.app.LAppActivity
import com.liang.lcommon.exts.LRouter
import com.liang.liangutils.utils.LLogX
import com.liang.liangutils.view.LCircleSeekBar
import kotlinx.android.synthetic.main.dmeo_circle_seek_bar.*

class CircleSeekBarDemo : LAppActivity() {

    companion object {
        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, CircleSeekBarDemo::class.java))
        }

        @JvmStatic
        fun getItem(): LBaseItemBean {
            val intro = "自定义SeekBar\n支持环形，圆形;\n锚点可隐藏，可修改位置;\n可以监听缓冲进度"
            val src = R.drawable.demo_circle_seek_bar
            return LBaseItemBean(intro, src)
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startCircleSeekBarDemo(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dmeo_circle_seek_bar)

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

    }
}
