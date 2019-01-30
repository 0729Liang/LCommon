package com.liang.lcommon.demo.activity.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.liang.lcommon.R
import com.liang.lcommon.demo.activity.LBaseItemBean
import com.liang.lcommon.demo.app.LAppActivity
import com.liang.lcommon.demo.exts.LRouter
import com.liang.liangutils.utils.LLogX
import kotlinx.android.synthetic.main.demo_llog_activity.*

class LLogDemo : LAppActivity() {

    companion object {

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, LLogDemo::class.java))
        }

        @JvmStatic
        fun getItem(): LBaseItemBean {
            val intro = "日志工具\n摘抄自blank AndroidUtils"
            val src = R.drawable.demo_llogx
            return LBaseItemBean(intro, src)
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startLLogDemoAcvtvity(it) }
        }

    }

    private var mMyselfLog = true
    private var mContent = "我是日志工具o"
    private val TAG = "LAING"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_llog_activity)

        demoLogChangeLogUtilBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            mMyselfLog = isChecked
        }

        demoLogOneLogbtn.setOnClickListener { printLog(mMyselfLog, mContent, 1) }
        demoLogTwoLogbtn.setOnClickListener { printLog(mMyselfLog, mContent, 2) }
        demoLogThreeLogbtn.setOnClickListener { printLog(mMyselfLog, mContent, 3) }

    }

    fun <T> printLog(myselfLog: Boolean, t: T, count: Int) {
        if (myselfLog) {
            for (i in 1..count) {
                LLogX.e(t.toString() + i)
            }
        } else {
            for (i in 1..count) {
                LogUtils.e(t.toString() + i)
            }
        }
    }

}
