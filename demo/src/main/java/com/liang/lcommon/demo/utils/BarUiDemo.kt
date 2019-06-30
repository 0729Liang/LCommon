package com.liang.lcommon.demo.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.liang.lcommon.R
import com.liang.lcommon.base.LBaseActivity
import com.liang.lcommon.exts.LRouter
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean
import com.liang.liangutils.libs.utils.LBarUtilsX
import kotlinx.android.synthetic.main.demo_bar_ui_activity.*

class BarUiDemo : LBaseActivity() {
    companion object {

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, BarUiDemo::class.java))
        }

        @JvmStatic
        fun newItem(): LBaseItemBean {
            val title = "AndroidBar工具类"
            val intro = "通知栏导航栏相关\n参照 AndroidUtils"
            val src = R.drawable.demo_barui
            val bean = LBaseItemBean(title, intro, src)
            bean.clickEvent = getClickEvent()
            bean.type = LBaseItemBean.BeanType.UTILS
            return bean
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
        demoBarGetStatusBarHeight.setOnClickListener { Toast.makeText(this, "获取StatusBar高度" + LBarUtilsX.getStatusBarHeight(), Toast.LENGTH_SHORT).show(); }

        demoBarSetStatusBarLight.setOnCheckedChangeListener { buttonView, isChecked ->
            LBarUtilsX.setStatusBarLightMode(this, isChecked)
        }

        demoBarSetStatusBarVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            LBarUtilsX.setStatusBarVisibility(this, isChecked)
            Toast.makeText(this, "StatusBar显示+？" + LBarUtilsX.isStatusBarVisible(this), Toast.LENGTH_SHORT).show();
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

        demoBarGetActionBarHeight.setOnClickListener { Toast.makeText(this, "获取ActionBar高度" + LBarUtilsX.getActionBarHeight(), Toast.LENGTH_SHORT).show(); }

        ////////////////////////////////////NotificationBar///////////////////////////////////////////

        demoBarSetNotificationBarVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            LBarUtilsX.setStatusBarVisibility(this, isChecked)
            Toast.makeText(this, "NotificationBar显示+？" + LBarUtilsX.isStatusBarVisible(this), Toast.LENGTH_SHORT).show();
        }

        ////////////////////////////////////NavBar///////////////////////////////////////////
        val supportNavBar = LBarUtilsX.isSupportNavBar()

        demoBarIsSupportNavBar.setOnClickListener {
            Toast.makeText(this, "支持NavBar？" + LBarUtilsX.isSupportNavBar(), Toast.LENGTH_SHORT).show();
        }

        demoBarGetNavBarHeight.setOnClickListener {
            if (!supportNavBar) {
                return@setOnClickListener
            }
            Toast.makeText(this, "获取NavBar高度" + LBarUtilsX.getNavBarHeight(), Toast.LENGTH_SHORT).show();
        }

        demoBarSetNavBarVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!supportNavBar) {
                return@setOnCheckedChangeListener
            }
            LBarUtilsX.setNavBarVisibility(this, isChecked)
            Toast.makeText(this, "NavBar显示+？" + LBarUtilsX.isNavBarVisible(this), Toast.LENGTH_SHORT).show();
        }


        demoBarSetNavBarColor.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!supportNavBar) {
                return@setOnCheckedChangeListener
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Toast.makeText(this, "改变Nav颜色", Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    LBarUtilsX.setNavBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
                } else {
                    LBarUtilsX.setNavBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary))
                }
            } else {
                Toast.makeText(this, "SDK version < 21", Toast.LENGTH_SHORT).show();
            }

        }

    }

    /*setStatusBarColor4Drawer             : 为 DrawerLayout 设置状态栏颜色*/

}
