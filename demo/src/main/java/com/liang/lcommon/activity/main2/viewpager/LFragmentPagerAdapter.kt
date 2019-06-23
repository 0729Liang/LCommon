package com.liang.lcommon.activity.main2.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.liang.lcommon.activity.main2.fragment.UtilsFragment
import com.liang.lcommon.base.LBaseFragment
import java.lang.ref.WeakReference

/**
 * Auther: amarao
 * CreateAt: 2019-06-23
 * Describer: LFragmentPagerAdapter
 *
 * FragmentPagerAdapter与FragmentStatePagerAdapter区别:https://www.jianshu.com/p/a976bbefaeec
 */
class LFragmentPagerAdapter(val fragmentList: List<LBaseFragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
        //return UtilsFragment.newInstance("123")
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    /**
     * 描述：定义的新适配器需要重写getpagerTitle这个方法，不然标题显示不出来。
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList.get(position).mName
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}