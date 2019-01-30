package com.liang.lcommon.activity

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.liang.lcommon.R
import com.liang.lcommon.activity.demo.*
import com.liang.lcommon.app.LAppActivity
import kotlinx.android.synthetic.main.activity_lbase.*
import java.util.ArrayList

class LBaseActivity : LAppActivity() {

    //    private var clickMap = SparseArray<LBaseItemBean.ClickEvent>()
    private var clickMap = ArrayList<LBaseItemBean.ClickEvent>()
    private val mList = ArrayList<LBaseItemBean>()
    private var mAdapter: LBaseAdapter = LBaseAdapter(mList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lbase)

        initView()

        addItem(ActivityStartAnimDemo.getItem(), ActivityStartAnimDemo.getClickEvent())

        addItem(CircleSeekBarDemo.getItem(), CircleSeekBarDemo.getClickEvent())

        addItem(SettingViewDemo.getItem(), SettingViewDemo.getClickEvent())

        addItem(RockerActivityDemo.getItem(), RockerActivityDemo.getClickEvent())

        addItem(LKVMgrDemo.getItem(), LKVMgrDemo.getClickEvent())

        addItem(LLogDemo.getItem(), LLogDemo.getClickEvent())

        addItem(BarUiDemo.getItem(), BarUiDemo.getClickEvent())
    }

    private fun initView() {
        val layout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layout
        recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            // ?. == if not null ?: == else
            clickMap.get(position).onClick(mActivity)
        }
    }

    private fun addItem(bean: LBaseItemBean, click: LBaseItemBean.ClickEvent) {
        mList.add(0, bean)
        mAdapter.notifyItemInserted(0)
        clickMap.add(0, click)
    }

    private fun addItem(intro: String, src: Int, click: LBaseItemBean.ClickEvent) {
        val index = mList.size
        val bean = LBaseItemBean(intro, src)
        mList.add(0, bean)
        mAdapter.notifyItemInserted(0)
        clickMap.add(0, click)
    }
}
