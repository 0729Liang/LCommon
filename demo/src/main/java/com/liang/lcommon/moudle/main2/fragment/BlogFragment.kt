package com.liang.lcommon.moudle.main2.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.liang.lcommon.R
import com.liang.lcommon.moudle.main2.adapter.LBaseAdapter
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean
import com.liang.lcommon.base.LBaseFragment
import kotlinx.android.synthetic.main.fragment_view.*
import java.util.ArrayList

class BlogFragment : LBaseFragment() {

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
        return inflater.inflate(R.layout.fragment_blog, container, false)
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
        //mAdapter.get()?.notifyItemInserted(0)
        mAdapter.notifyItemInserted(0)
    }


    companion object {

        @JvmStatic
        fun newInstance(name: String) = BlogFragment().apply { mName = name }

    }
}
