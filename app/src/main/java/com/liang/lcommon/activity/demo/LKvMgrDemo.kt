package com.liang.lcommon.activity.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.blankj.utilcode.util.ToastUtils
import com.liang.lcommon.R
import com.liang.lcommon.activity.LBaseItemBean
import com.liang.lcommon.app.LAppActivity
import com.liang.lcommon.exts.LRouter
import com.liang.lcommon.mgrs.LKVMgr
import com.liang.lcommon.utils.LEmptyX
import com.liang.lcommon.utils.LLogX
import kotlinx.android.synthetic.main.demo_lkv_mgr.*

class LKvMgrDemo : LAppActivity() {

    companion object {

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, LKvMgrDemo::class.java))
        }

        @JvmStatic
        fun getItem(): LBaseItemBean {
            val intro = "文件缓存策略\n通过mmkv 和 sharePerfences实现\n可保存int float String boolean Object等类型;\n可保存到内存或本地;"
            val src = R.mipmap.demo_lkvmgr
            return LBaseItemBean(intro, src)
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startLKVMgrDemoActivity(it) }
        }

        val LKVMGR_SAVE_TYPE = "LKVMGR_SAVE_TYPE";// 0  1 2
        val LKVMGR_WRITER_TYPE = "LKVMGR_WRITER_TYPE"; // 0 1 2
        val LKVMGR_CONTENT = "LKVMGR_CONTENT";

        enum class LKVMgrSaveType constructor(val value: Int) {
            SAVE_ON_MEMORY(0),
            SAVE_ON_MMKV(1),
            SAVE_ON_SP(2),
        }

        enum class LKVMgrWriteType constructor(val value: Int) {
            WRITE_STRING(0),
            WRITE_NUMBER(1),
            WRITE_BOOLEAN(2),
        }

    }

    private var mSaveType: Int = 0
    private var mWriteType: Int = 0
    private var mContentText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_lkv_mgr)

        restoryState()
        onSaveTypeListener()
        onWriterTypeListener()
        onBooleanListener();
        confirmBtn.setOnClickListener() { writeContent() }
        readBtn.setOnClickListener() { readContent() }
        clearBtn.setOnClickListener() {
            radioGroupSaveType.clearCheck()
            radioGroupWriterType.clearCheck()
            contentTextView.setText("")
            contentEditText.setText("")
            radioGroupSaveType.check(R.id.lkvmgr_memory)
            radioGroupWriterType.check(R.id.lkvmgr_string)
            ToastUtils.showShort("清除成功")
        }

    }

    private fun restoryState() {
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        mWriteType = LKVMgr.getInstance().getInt(LKVMGR_WRITER_TYPE, 0)

        when (mSaveType) {
            LKVMgrSaveType.SAVE_ON_MEMORY.value -> radioGroupSaveType.check(R.id.lkvmgr_memory)
            LKVMgrSaveType.SAVE_ON_MMKV.value -> radioGroupSaveType.check(R.id.lkvmgr_mmkv)
            LKVMgrSaveType.SAVE_ON_SP.value -> radioGroupSaveType.check(R.id.lkvmgr_sp)
        }//  when (mSaveType)

        when (mWriteType) {
            LKVMgrWriteType.WRITE_STRING.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_string)
                lkvmgr_boolean_type.visibility = View.GONE
            }
            LKVMgrWriteType.WRITE_NUMBER.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_number)
                lkvmgr_boolean_type.visibility = View.GONE
            }
            LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_boolean)
                lkvmgr_boolean_type.visibility = View.VISIBLE
            }
        }
    }

    fun onSaveTypeListener() {
        LLogX.e("onSaveTypeListener1")
        var type = 0;
        radioGroupSaveType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.lkvmgr_memory -> type = LKVMgrSaveType.SAVE_ON_MEMORY.value
                R.id.lkvmgr_mmkv -> type = LKVMgrSaveType.SAVE_ON_MMKV.value
                R.id.lkvmgr_sp -> type = LKVMgrSaveType.SAVE_ON_SP.value
            }
            LLogX.e("onSaveTypeListener2")
            LKVMgr.getInstance().putInt(LKVMGR_SAVE_TYPE, type)
        }

    }

    fun onWriterTypeListener() {
        var type = 0;

        radioGroupWriterType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.lkvmgr_string -> {
                    type = LKVMgrWriteType.WRITE_STRING.value
                    lkvmgr_boolean_type.visibility = View.GONE
                    contentEditText.inputType = InputType.TYPE_NULL
                    contentEditText.visibility = View.VISIBLE
                    contentEditText.setText("")
                }
                R.id.lkvmgr_number -> {
                    type = LKVMgrWriteType.WRITE_NUMBER.value
                    lkvmgr_boolean_type.visibility = View.GONE
                    contentEditText.inputType = InputType.TYPE_CLASS_NUMBER
                    contentEditText.visibility = View.VISIBLE
                    contentEditText.setText("")
                }
                R.id.lkvmgr_boolean -> {
                    type = LKVMgrWriteType.WRITE_BOOLEAN.value
                    lkvmgr_boolean_type.visibility = View.VISIBLE
                    contentEditText.visibility = View.GONE
                    contentEditText.setText(getWriterBooleanVaule().toString())
                }
            }
            LKVMgr.getInstance().putInt(LKVMGR_WRITER_TYPE, type)

        }
    }

    fun onBooleanListener() {
        lkvmgr_boolean_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.lkvmgr_boolean_type_true -> contentEditText.setText(true.toString())
                else ->
                    contentEditText.setText(false.toString())
            }
        }
    }

    fun getWriterBooleanVaule(): Boolean {
        return lkvmgr_boolean_type.checkedRadioButtonId.equals(R.id.lkvmgr_boolean_type_true)
    }

    fun writeContent() {
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        mWriteType = LKVMgr.getInstance().getInt(LKVMGR_WRITER_TYPE, 0)

        if (LEmptyX.isEmpty(contentEditText.text)) {
            ToastUtils.showShort("请输入要保存的内容");
            return
        } else {
            mContentText = contentEditText.text.toString()
        }

        when (mSaveType) {
            LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                when (mWriteType) {
                    LKVMgrWriteType.WRITE_STRING.value -> LKVMgr.memory().putString(LKVMGR_CONTENT, mContentText)
                    LKVMgrWriteType.WRITE_NUMBER.value -> LKVMgr.memory().putFloat(LKVMGR_CONTENT, mContentText.toFloat())
                    LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.memory().putBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                    }
                }
            }
            LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                when (mWriteType) {
                    LKVMgrWriteType.WRITE_STRING.value -> LKVMgr.mmkv().putString(LKVMGR_CONTENT, mContentText)
                    LKVMgrWriteType.WRITE_NUMBER.value -> LKVMgr.mmkv().putFloat(LKVMGR_CONTENT, mContentText.toFloat())
                    LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.mmkv().putBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                    }
                }
            }
            LKVMgrSaveType.SAVE_ON_SP.value -> {
                when (mWriteType) {
                    LKVMgrWriteType.WRITE_STRING.value -> LKVMgr.sp().putString(LKVMGR_CONTENT, mContentText)
                    LKVMgrWriteType.WRITE_NUMBER.value -> LKVMgr.sp().putFloat(LKVMGR_CONTENT, mContentText.toFloat())
                    LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.sp().putBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                    }
                }
            }
        }//  when (mSaveType)
        ToastUtils.showShort("保存成功")
    } // onSaveType

    fun readContent() {
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        mWriteType = LKVMgr.getInstance().getInt(LKVMGR_WRITER_TYPE, 0)

        when (mSaveType) {
            LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                when (mWriteType) {
                    LKVMgrWriteType.WRITE_STRING.value -> mContentText = LKVMgr.memory().getString(LKVMGR_CONTENT, mContentText)
                    LKVMgrWriteType.WRITE_NUMBER.value -> mContentText = LKVMgr.memory().getFloat(LKVMGR_CONTENT, mContentText.toFloat()).toString()
                    LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        mContentText = LKVMgr.memory().getBool(LKVMGR_CONTENT, getWriterBooleanVaule()).toString()
                    }
                }
            }
            LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                when (mWriteType) {
                    LKVMgrWriteType.WRITE_STRING.value -> mContentText = LKVMgr.mmkv().getString(LKVMGR_CONTENT, mContentText)
                    LKVMgrWriteType.WRITE_NUMBER.value -> mContentText = LKVMgr.mmkv().getFloat(LKVMGR_CONTENT, mContentText.toFloat()).toString()
                    LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        mContentText = LKVMgr.mmkv().getBool(LKVMGR_CONTENT, getWriterBooleanVaule()).toString()
                    }
                }
            }
            LKVMgrSaveType.SAVE_ON_SP.value -> {
                when (mWriteType) {
                    LKVMgrWriteType.WRITE_STRING.value -> mContentText = LKVMgr.sp().getString(LKVMGR_CONTENT, mContentText)
                    LKVMgrWriteType.WRITE_NUMBER.value -> mContentText = LKVMgr.sp().getFloat(LKVMGR_CONTENT, mContentText.toFloat()).toString()
                    LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        mContentText = LKVMgr.sp().getBool(LKVMGR_CONTENT, getWriterBooleanVaule()).toString()
                    }
                }
            }
        }//  when (mSaveType)
        if (LEmptyX.isEmpty(mContentText)) {
            ToastUtils.showShort("没有读取到内容")
        } else {
            contentEditText.setText(mContentText)
            contentTextView.setText(mContentText)
            ToastUtils.showShort("读取成功")
        }

    } // readContent


}
