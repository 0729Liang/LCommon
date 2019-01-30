package com.liang.lcommon.activity.demo

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import com.liang.lcommon.R
import com.liang.lcommon.activity.LBaseItemBean
import com.liang.lcommon.exts.LRouter
import com.liang.liangutils.utils.LBarUtilsX
import kotlinx.android.synthetic.main.demo_bar_ui_activity.*

class BarUiDemo : AppCompatActivity() {
    companion object {

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, BarUiDemo::class.java))
        }

        @JvmStatic
        fun getItem(): LBaseItemBean {
            val intro = "通知栏导航栏相关\n参照 AndroidUtils"
            val src = R.drawable.demo_barui
            return LBaseItemBean(intro, src)
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startBarUiDemo(it) }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_bar_ui_activity)

        LBarUtilsX.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LBarUtilsX.setNavBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        }
        ////////////////////////////////////StatusBar///////////////////////////////////////////
        demoBarGetStatusBarHeight.setOnClickListener { ToastUtils.showShort("获取StatusBar高度" + LBarUtilsX.getStatusBarHeight()) }

        demoBarSetStatusBarLight.setOnCheckedChangeListener { buttonView, isChecked ->
            LBarUtilsX.setStatusBarLightMode(this, isChecked)
        }

        demoBarSetStatusBarVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            LBarUtilsX.setStatusBarVisibility(this, isChecked)
            ToastUtils.showShort("StatusBar显示+？" + LBarUtilsX.isStatusBarVisible(this))
        }

        demoBarMarginTopBarHeight.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                LBarUtilsX.addMarginTopEqualStatusBarHeight(demoBarRootView)
            } else {
                LBarUtilsX.subtractMarginTopEqualStatusBarHeight(demoBarRootView)
            }
        }

        demoBarSetStatusBarColor.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                LBarUtilsX.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
            } else {
                LBarUtilsX.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary))
            }
        }

        ////////////////////////////////////ActionBar///////////////////////////////////////////

        demoBarGetActionBarHeight.setOnClickListener { ToastUtils.showShort("获取ActionBar高度" + LBarUtilsX.getActionBarHeight()) }

        ////////////////////////////////////NotificationBar///////////////////////////////////////////

        demoBarSetNotificationBarVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            LBarUtilsX.setStatusBarVisibility(this, isChecked)
            ToastUtils.showShort("NotificationBar显示+？" + LBarUtilsX.isStatusBarVisible(this))
        }

        ////////////////////////////////////NavBar///////////////////////////////////////////
        val supportNavBar = LBarUtilsX.isSupportNavBar()

        demoBarIsSupportNavBar.setOnClickListener {
            ToastUtils.showShort("支持NavBar？" + LBarUtilsX.isSupportNavBar())
        }

        demoBarGetNavBarHeight.setOnClickListener {
            if (!supportNavBar) {
                return@setOnClickListener
            }
            ToastUtils.showShort("获取NavBar高度" + LBarUtilsX.getNavBarHeight())
        }

        demoBarSetNavBarVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!supportNavBar) {
                return@setOnCheckedChangeListener
            }
            LBarUtilsX.setNavBarVisibility(this, isChecked)
            ToastUtils.showShort("NavBar显示+？" + LBarUtilsX.isNavBarVisible(this))
        }


        demoBarSetNavBarColor.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!supportNavBar) {
                return@setOnCheckedChangeListener
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ToastUtils.showShort("改变Nav颜色")
                if (isChecked) {
                    LBarUtilsX.setNavBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
                } else {
                    LBarUtilsX.setNavBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary))
                }
            } else {
                ToastUtils.showShort("SDK version < 21")
            }

        }

    }

    /*setStatusBarColor4Drawer             : 为 DrawerLayout 设置状态栏颜色*/

}
