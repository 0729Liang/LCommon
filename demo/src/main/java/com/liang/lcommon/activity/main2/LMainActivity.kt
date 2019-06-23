package com.liang.lcommon.activity.main2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.liang.lcommon.R
import com.liang.lcommon.activity.demo.blog.eventbus.EventBusDemo
import com.liang.lcommon.activity.demo.blog.fragment.FragmentActivity
import com.liang.lcommon.activity.demo.utils.*
import com.liang.lcommon.activity.demo.view.CircleSeekBarDemo
import com.liang.lcommon.activity.demo.view.RockerActivityDemo
import com.liang.lcommon.activity.demo.view.SettingViewDemo
import com.liang.lcommon.activity.main.LBaseItemBean
import com.liang.lcommon.base.LBaseFragment
import com.liang.lcommon.activity.main2.fragment.BlogFragment
import com.liang.lcommon.activity.main2.fragment.UtilsFragment
import com.liang.lcommon.activity.main2.fragment.ViewFragment
import com.liang.lcommon.activity.main2.viewpager.LFragmentPagerAdapter
import com.liang.liangutils.utils.LLogX
import kotlinx.android.synthetic.main.activity_lmain.*
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

/**
 * Auther: amarao
 * CreateAt: 2019-06-23
 * Describer: 主页
 *
 * TabLayout+ViewPager+Fragment实现切页展示:https://blog.csdn.net/qq_34773981/article/details/82022647
 * TabLayout的属性介绍:https://www.jianshu.com/p/23863e4bbea1
 * 解决 Button 和 TabLayout 英文自动大写的问题:https://blog.csdn.net/zanjunrong/article/details/72636933
 * 记录ViewPager+fragment 内存泄露问题:https://blog.csdn.net/qq_32536991/article/details/88837924;https://blog.csdn.net/K_Hello/article/details/82996162;
 */
class LMainActivity : AppCompatActivity() {


    // 实例化三个Fragment页面
    val mUtilsFragment = UtilsFragment.newInstance("Utils")
    val mViewFragment = ViewFragment.newInstance("View")
    val mBlogFragment = BlogFragment.newInstance("Blog/Learn")

    // 存储Fragment的List
    val mTabItemList: MutableList<LBaseFragment> = ArrayList()

    // 实例化viewpager适配器
    val mViewPagerAdapter = LFragmentPagerAdapter(mTabItemList, supportFragmentManager)

    // 数据源
    val mItemList: MutableList<LBaseItemBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lmain)

        // 1. 存储fragment列表，强引用持有fragment，导致viewpager销毁fragment时内存泄漏
        mTabItemList.add(mUtilsFragment)
        mTabItemList.add(mViewFragment)
        mTabItemList.add(mBlogFragment)

        // 2. 后为viewpager设置一个适配器
        mainViewpager.adapter = mViewPagerAdapter

        // 3. 将ViewPager关联到Tablayout中
        mainTablayout.setupWithViewPager(mainViewpager);
        mainViewpager.offscreenPageLimit = 2 // 设置viewpager左右两边一次加载两个页面，防止内存泄漏

        // 5. 默认选中索引1的tab
        mainTablayout.getTabAt(1)?.select();

        // 添加数据
        dataFactory()

        // 分流
        shuntData()
    }

    /**
     * 描述：数据工厂，用于添加数据，然后分流给不同的fragment
     */
    fun dataFactory() {
        // 程序启动动画
        addItem(ActivityStartAnimDemo.newItem())

        // 圆形Seekbar
        addItem(CircleSeekBarDemo.newItem())

        // 设置相关按钮，箭头、Switch、Title
        addItem(SettingViewDemo.newItem())

        // 虚拟摇杆
        addItem(RockerActivityDemo.newItem())

        // 文件缓存策略对象序列化
        addItem(LKVMgrDemo.newItem())

        // 日志工具
        addItem(LLogDemo.newItem())

        // 状态栏，通知栏、导航栏
        addItem(BarUiDemo.newItem())

        // 自定义注解
        addItem(AnnotationDemo.newItem())

        // EventBus
        addItem(EventBusDemo.newItem())

        // fragment demo
        addItem(FragmentActivity.newItem())
    }

    /**
     * 描述：添加一项item
     */
    fun addItem(bean: LBaseItemBean) {
        mItemList.add(bean)
    }

    /**
     * 描述：将数据分配给对应的fragment
     */
    fun shuntData() {
        mItemList.forEach {
            when (it.type) {
                LBaseItemBean.BeanType.UTILS -> {
                    mUtilsFragment.forwardInsertItem(it)
                }
                LBaseItemBean.BeanType.VIEW -> {
                    mViewFragment.forwardInsertItem(it)
                }
                LBaseItemBean.BeanType.BLOG -> {
                    mBlogFragment.forwardInsertItem(it)
                }
            }
        }
    }
}
