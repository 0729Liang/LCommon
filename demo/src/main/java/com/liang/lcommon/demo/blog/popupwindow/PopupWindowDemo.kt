package com.liang.lcommon.demo.blog.popupwindow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liang.lcommon.R
import com.liang.lcommon.exts.LRouter
import com.liang.lcommon.moudle.main2.ToastUtils
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean
import kotlinx.android.synthetic.main.demo_popup_window.*

class PopupWindowDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_popup_window)

        val window = LPopupWindow.create(this)
                .addTestPauseClickEvent { ToastUtils.showShort("暂停") }
                .addTestResumeClickEvent { ToastUtils.showShort("继续") }
                .addStopClickEvent { ToastUtils.showShort("停止") }
                .addQueryDataClickEvent { ToastUtils.showShort("查询数据") }
                .addQueryChannelStatusClickEvent { ToastUtils.showShort("查询状态") }

        demo_popup_window_text.setOnClickListener {
            if (window.isShow) {
                window.hide()
            } else {
                window.show(it)
            }
        }
    }


    companion object {
        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, PopupWindowDemo::class.java))
        }

        @JvmStatic
        fun newItem(): LBaseItemBean {
            val intro = "一个简单的自定义 PopupWindow"
            val src = R.drawable.demo_popupwindow
            val bean = LBaseItemBean(intro, src)
            bean.clickEvent = getClickEvent()
            bean.type = LBaseItemBean.BeanType.BLOG
            return bean
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startPopupWindowDemo(it) }
        }
    }
}
