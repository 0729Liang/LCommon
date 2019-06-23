package com.liang.lcommon.activity.main2.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.liang.lcommon.R
import com.liang.lcommon.activity.main.LBaseAdapter
import com.liang.lcommon.activity.main.LBaseItemBean
import com.liang.lcommon.base.LBaseFragment
import kotlinx.android.synthetic.main.fragment_view.*
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * Auther: amarao
 * CreateAt: 2019-06-23
 * Describer:view界面
 *
 * Kotlin中Fragment 控件初始化报错 XXX must not be null:https://blog.csdn.net/wfs31415926/article/details/85318743
 */
class ViewFragment : LBaseFragment() {

    private val mList = ArrayList<LBaseItemBean>()  // 数据源
    private var mAdapter: LBaseAdapter = LBaseAdapter(mList) // 适配器
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.getItem(position) as LBaseItemBean
            item.clickEvent.onClick(mReference.get()?.activity)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化控件
        mRecyclerView = recyclerView
        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = mAdapter
    }

    /**
     * 描述：在数据源index=0处添加一项item
     */
    public fun forwardInsertItem(bean: LBaseItemBean) {
        mList.add(0, bean)
        mAdapter.notifyItemInserted(0)
    }


    companion object {
        @JvmStatic
        fun newInstance(name: String) = ViewFragment().apply { mName = name }

    }

}
