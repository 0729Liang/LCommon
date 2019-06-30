package com.liang.lcommon.demo.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.liang.lcommon.R
import com.liang.lcommon.base.LBaseActivity
import com.liang.lcommon.exts.LRouter
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean
import com.liang.liangutils.libs.utils.LLogX
import kotlinx.android.synthetic.main.demo_llog_activity.*

class LogDemo : LBaseActivity() {

    companion object {

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, LogDemo::class.java))
        }

        @JvmStatic
        fun newItem(): LBaseItemBean {
            val title = "日志工具"
            val intro = "日志工具\n摘抄自blank AndroidUtils"
            val src = R.drawable.demo_llogx
            val bean = LBaseItemBean(title, intro, src)
            bean.clickEvent = getClickEvent()
            bean.type = LBaseItemBean.BeanType.UTILS
            return bean
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startLLogDemoDemo(it) }
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
                LLogX.e(t.toString() + i)
            }
        }
    }

}
