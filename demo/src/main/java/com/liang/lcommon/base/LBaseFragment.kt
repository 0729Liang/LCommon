package com.liang.lcommon.base


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liang.lcommon.R
import com.liang.liangutils.msg.Exts
import java.lang.ref.WeakReference

open class LBaseFragment : Fragment() {

    var mName: String = ""
    lateinit var mReference:WeakReference<LBaseFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Exts.registerEvent(this)
        mReference = WeakReference(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_lbase, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Exts.unRegisterEvent(this)
        LApplication.getRefWatcher(getContext()).watch(this,"fragment");
    }

}
