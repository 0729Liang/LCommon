package com.liang.lcommon.moudle.main

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.liang.lcommon.R
import com.liang.lcommon.base.LBaseActivity
import com.liang.lcommon.demo.blog.eventbus.EventBusDemo
import com.liang.lcommon.demo.blog.fragment.FragmentActivity
import com.liang.lcommon.demo.utils.*
import com.liang.lcommon.demo.view.CircleSeekBarDemo
import com.liang.lcommon.demo.view.RockerActivityDemo
import com.liang.lcommon.demo.view.SettingViewDemo
import com.liang.lcommon.moudle.main2.adapter.LBaseAdapter
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean
import kotlinx.android.synthetic.main.activity_lbase.*
import java.util.*

class LBaseActivity : LBaseActivity() {

    //    private var clickMap = SparseArray<LBaseItemBean.ClickEvent>()
    private var clickMap = ArrayList<LBaseItemBean.ClickEvent>()
    private val mList = ArrayList<LBaseItemBean>()
    private var mAdapter: LBaseAdapter = LBaseAdapter(mList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lbase)

        initView()

        // 程序启动动画
        forwardInsertItem(ActivityStartAnimDemo.newItem(), ActivityStartAnimDemo.getClickEvent())

        // 圆形Seekbar
        forwardInsertItem(CircleSeekBarDemo.newItem(), CircleSeekBarDemo.getClickEvent())

        // 设置相关按钮，箭头、Switch、Title
        forwardInsertItem(SettingViewDemo.newItem(), SettingViewDemo.getClickEvent())

        // 虚拟摇杆
        forwardInsertItem(RockerActivityDemo.newItem(), RockerActivityDemo.getClickEvent())

        // 文件缓存策略对象序列化
        forwardInsertItem(LKVMgrDemo.newItem(), LKVMgrDemo.getClickEvent())

        // 日志工具
        forwardInsertItem(LogDemo.newItem(), LogDemo.getClickEvent())

        // 状态栏，通知栏、导航栏
        forwardInsertItem(BarUiDemo.newItem(), BarUiDemo.getClickEvent())

        // 自定义注解
        forwardInsertItem(AnnotationDemo.newItem(), AnnotationDemo.getClickEvent())

        // EventBus
        forwardInsertItem(EventBusDemo.newItem(), EventBusDemo.getClickEvent())

        // fragment demo
        forwardInsertItem(FragmentActivity.newItem(), FragmentActivity.getClickEvent())
    }

    private fun initView() {

        val layout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layout
        recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            // ?. == if not null ?: == else

            clickMap.get(position).onClick(mActivity.get())
        }
    }

    // 前插
    private fun forwardInsertItem(bean: LBaseItemBean, click: LBaseItemBean.ClickEvent) {
        mList.add(0, bean)
        mAdapter.notifyItemInserted(0)
        clickMap.add(0, click)
    }


    private fun forwardInsertItem(intro: String, src: Int, click: LBaseItemBean.ClickEvent) {
        val index = mList.size
        val bean = LBaseItemBean(intro, src)
        mList.add(0, bean)
        mAdapter.notifyItemInserted(0)
        clickMap.add(0, click)
    }
}
