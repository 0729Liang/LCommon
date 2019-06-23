package com.liang.lcommon.activity.demo.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.liang.lcommon.R
import com.liang.lcommon.activity.main.LBaseItemBean
import com.liang.lcommon.base.LAppActivity
import com.liang.lcommon.exts.LRouter
import com.liang.liangutils.mgrs.LKVMgr
import com.liang.liangutils.utils.LEmptyX
import kotlinx.android.synthetic.main.demo_lkv_mgr.*
import java.util.HashMap

class LKVMgrDemo : LAppActivity() {

    companion object {

        @JvmStatic
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, LKVMgrDemo::class.java))
        }

        @JvmStatic
        fun newItem(): LBaseItemBean {
            val title = "文件缓存策略"
            val intro = "文件缓存策略\n\t1.通过mmkv 和 sharePerfences实现\n\t2.可保存int float String boolean Object等基本类型，甚至是List，Map,Set等特殊类型;\n\t3.可保存到内存或本地;"
            val src = R.drawable.demo_lkvmgr
            val bean = LBaseItemBean(title, intro, src)
            bean.clickEvent = getClickEvent()
            bean.type = LBaseItemBean.BeanType.UTILS
            return bean
        }

        @JvmStatic
        fun getClickEvent(): LBaseItemBean.ClickEvent {
            return LBaseItemBean.ClickEvent { LRouter.startLKVMgrDemoActivity(it) }
        }

        val LKVMGR_SAVE_TYPE = "LKVMGR_SAVE_TYPE"// 保存方式
        val LKVMGR_WRITER_TYPE = "LKVMGR_WRITER_TYPE" // 保存类型
        val LKVMGR_CONTENT = "LKVMGR_CONTENT" // 保存内容

        // 保存方式：内存、mmkv、shareperfence
        enum class LKVMgrSaveType constructor(val value: Int) {
            SAVE_ON_MEMORY(0),
            SAVE_ON_MMKV(1),
            SAVE_ON_SP(2),
        }

        // 保存内容：字符，数字，boolean
        enum class LKVMgrWriteType constructor(val value: Int) {
            WRITE_STRING(0),
            WRITE_NUMBER(1),
            WRITE_BOOLEAN(2),
            WRITE_OBJ(3),
            WRITE_LIST(4),
            WRITE_MAP(5),
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
        onBooleanListener()
        confirmBtn.setOnClickListener {
            writeContent()
//            when (radioGroupWriterType.checkedRadioButtonId) {
//                R.id.lkvmgr_string, R.id.lkvmgr_number, R.id.lkvmgr_boolean -> writeContent()
//            }
        }
        readBtn.setOnClickListener { readContent() }
        clearBtn.setOnClickListener {
            radioGroupSaveType.clearCheck()
            radioGroupWriterType.clearCheck()
            contentTextView.text = ""
            contentEditText.setText("")
            radioGroupSaveType.check(R.id.lkvmgr_memory)
            radioGroupWriterType.check(R.id.lkvmgr_string)
            ToastUtils.showShort("清除成功")
        }
    }

    // 还原状态
    private fun restoryState() {
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value)
        mWriteType = LKVMgr.getInstance().getInt(LKVMGR_WRITER_TYPE, Companion.LKVMgrWriteType.WRITE_STRING.value)

        when (mSaveType) {
            Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value -> radioGroupSaveType.check(R.id.lkvmgr_memory)
            Companion.LKVMgrSaveType.SAVE_ON_MMKV.value -> radioGroupSaveType.check(R.id.lkvmgr_mmkv)
            Companion.LKVMgrSaveType.SAVE_ON_SP.value -> radioGroupSaveType.check(R.id.lkvmgr_sp)
        }//  when (mSaveType)

        lkvmgr_boolean_type.visibility = View.GONE
        when (mWriteType) {
            Companion.LKVMgrWriteType.WRITE_STRING.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_string)
            }
            Companion.LKVMgrWriteType.WRITE_NUMBER.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_number)
            }
            Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_boolean)
                lkvmgr_boolean_type.visibility = View.VISIBLE
            }
            Companion.LKVMgrWriteType.WRITE_OBJ.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_obj)
            }
            Companion.LKVMgrWriteType.WRITE_LIST.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_list)
            }
            Companion.LKVMgrWriteType.WRITE_MAP.value -> {
                radioGroupWriterType.check(R.id.lkvmgr_map)
            }
        }
    }

    // 保存类型监听
    fun onSaveTypeListener() {
        var type = Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value
        radioGroupSaveType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.lkvmgr_memory -> type = Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value
                R.id.lkvmgr_mmkv -> type = Companion.LKVMgrSaveType.SAVE_ON_MMKV.value
                R.id.lkvmgr_sp -> type = Companion.LKVMgrSaveType.SAVE_ON_SP.value
            }
            LKVMgr.getInstance().putInt(LKVMGR_SAVE_TYPE, type)
        }
    }

    // 写入类型监听
    fun onWriterTypeListener() {
        var type = Companion.LKVMgrWriteType.WRITE_STRING.value
        radioGroupWriterType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.lkvmgr_string -> {
                    type = Companion.LKVMgrWriteType.WRITE_STRING.value
                    lkvmgr_boolean_type.visibility = View.GONE
                    contentEditText.inputType = InputType.TYPE_CLASS_TEXT
                    contentEditText.visibility = View.VISIBLE
                    contentEditText.setText("")
                }
                R.id.lkvmgr_number -> {
                    type = Companion.LKVMgrWriteType.WRITE_NUMBER.value
                    lkvmgr_boolean_type.visibility = View.GONE
                    contentEditText.inputType = InputType.TYPE_CLASS_NUMBER
                    contentEditText.visibility = View.VISIBLE
                    contentEditText.setText("")
                }
                R.id.lkvmgr_boolean -> {
                    type = Companion.LKVMgrWriteType.WRITE_BOOLEAN.value
                    lkvmgr_boolean_type.visibility = View.VISIBLE
                    contentEditText.visibility = View.GONE
                    contentEditText.setText(getWriterBooleanVaule().toString())
                }
                R.id.lkvmgr_obj -> {
                    type = Companion.LKVMgrWriteType.WRITE_OBJ.value
                    writeObj()
                }
                R.id.lkvmgr_list -> {
                    type = Companion.LKVMgrWriteType.WRITE_LIST.value
                    writeList()
                }
                R.id.lkvmgr_map -> {
                    type = Companion.LKVMgrWriteType.WRITE_MAP.value
                    writeMap()
                }

            }
            LKVMgr.getInstance().putInt(LKVMGR_WRITER_TYPE, type)
        }
    }

    // 布尔值监听
    fun onBooleanListener() {
        lkvmgr_boolean_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.lkvmgr_boolean_type_true -> contentEditText.setText(true.toString())
                else ->
                    contentEditText.setText(false.toString())
            }
        }
    }

    // 判断布尔值
    fun getWriterBooleanVaule(): Boolean {
        return lkvmgr_boolean_type.checkedRadioButtonId.equals(R.id.lkvmgr_boolean_type_true)
    }

    // 写入
    fun writeContent() {
        when (radioGroupWriterType.checkedRadioButtonId) {
            R.id.lkvmgr_obj, R.id.lkvmgr_list, R.id.lkvmgr_map -> {
                ToastUtils.showShort("请选择其他类型保存")
                return
            }
        }

        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        mWriteType = LKVMgr.getInstance().getInt(LKVMGR_WRITER_TYPE, 0)

        if (LEmptyX.isEmpty(contentEditText.text)) {
            ToastUtils.showShort("请输入要保存的内容")
            return
        } else {
            mContentText = contentEditText.text.toString()
        }

        when (mSaveType) {
            Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                when (mWriteType) {
                    Companion.LKVMgrWriteType.WRITE_STRING.value -> LKVMgr.memory().putString(LKVMGR_CONTENT, mContentText)
                    Companion.LKVMgrWriteType.WRITE_NUMBER.value -> LKVMgr.memory().putFloat(LKVMGR_CONTENT, mContentText.toFloat())
                    Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.memory().putBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                    }
                }
            }
            Companion.LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                when (mWriteType) {
                    Companion.LKVMgrWriteType.WRITE_STRING.value -> LKVMgr.mmkv().putString(LKVMGR_CONTENT, mContentText)
                    Companion.LKVMgrWriteType.WRITE_NUMBER.value -> LKVMgr.mmkv().putFloat(LKVMGR_CONTENT, mContentText.toFloat())
                    Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.mmkv().putBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                    }
                }
            }
            Companion.LKVMgrSaveType.SAVE_ON_SP.value -> {
                when (mWriteType) {
                    Companion.LKVMgrWriteType.WRITE_STRING.value -> LKVMgr.sp().putString(LKVMGR_CONTENT, mContentText)
                    Companion.LKVMgrWriteType.WRITE_NUMBER.value -> LKVMgr.sp().putFloat(LKVMGR_CONTENT, mContentText.toFloat())
                    Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.sp().putBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                    }
                }
            }
        }//  when (mSaveType)
        ToastUtils.showShort("保存成功")
    } // onSaveType


    lateinit var list: MutableList<Personal>
    lateinit var map: MutableMap<String, Personal>

    // 读取
    fun readContent() {
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value)
        mWriteType = LKVMgr.getInstance().getInt(LKVMGR_WRITER_TYPE, Companion.LKVMgrWriteType.WRITE_STRING.value)

        val personal: Personal

        when (mSaveType) {
            Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                when (mWriteType) {
                    Companion.LKVMgrWriteType.WRITE_STRING.value -> mContentText = LKVMgr.memory().getString(LKVMGR_CONTENT, mContentText)
                    Companion.LKVMgrWriteType.WRITE_NUMBER.value -> mContentText = LKVMgr.memory().getFloat(LKVMGR_CONTENT, 0f).toString()
                    Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        mContentText = LKVMgr.memory().getBool(LKVMGR_CONTENT, getWriterBooleanVaule()).toString()
                    }
                    Companion.LKVMgrWriteType.WRITE_OBJ.value -> {
                        personal = LKVMgr.memory().getObj(LKVMGR_CONTENT, Personal::class.java)
                        mContentText = "对象：" + personal.name + personal.age
                    }
                    Companion.LKVMgrWriteType.WRITE_LIST.value -> {
                        mContentText = listToString(LKVMgr.memory().getList(LKVMGR_CONTENT, Personal::class.java))
                    }
                    Companion.LKVMgrWriteType.WRITE_MAP.value -> {
                        mContentText = mapToString(LKVMgr.memory().getMap(LKVMGR_CONTENT, String::class.java, Personal::class.java))
                    }
                }
            }
            Companion.LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                when (mWriteType) {
                    Companion.LKVMgrWriteType.WRITE_STRING.value -> mContentText = LKVMgr.mmkv().getString(LKVMGR_CONTENT, mContentText)
                    Companion.LKVMgrWriteType.WRITE_NUMBER.value -> mContentText = LKVMgr.mmkv().getFloat(LKVMGR_CONTENT, 0f).toString()
                    Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        mContentText = LKVMgr.mmkv().getBool(LKVMGR_CONTENT, getWriterBooleanVaule()).toString()
                    }
                    Companion.LKVMgrWriteType.WRITE_OBJ.value -> {
                        personal = LKVMgr.mmkv().getObj(LKVMGR_CONTENT, Personal::class.java)
                        mContentText = "对象：" + personal.name + personal.age
                    }
                    Companion.LKVMgrWriteType.WRITE_LIST.value -> {
                        mContentText = listToString(LKVMgr.mmkv().getList(LKVMGR_CONTENT, Personal::class.java))
                    }
                    Companion.LKVMgrWriteType.WRITE_MAP.value -> {
                        mContentText = mapToString(LKVMgr.mmkv().getMap(LKVMGR_CONTENT, String::class.java, Personal::class.java))
                    }
                }
            }
            Companion.LKVMgrSaveType.SAVE_ON_SP.value -> {
                when (mWriteType) {
                    Companion.LKVMgrWriteType.WRITE_STRING.value -> mContentText = LKVMgr.sp().getString(LKVMGR_CONTENT, mContentText)
                    Companion.LKVMgrWriteType.WRITE_NUMBER.value -> mContentText = LKVMgr.sp().getFloat(LKVMGR_CONTENT, 0f).toString()
                    Companion.LKVMgrWriteType.WRITE_BOOLEAN.value -> {
                        LKVMgr.sp().getBool(LKVMGR_CONTENT, getWriterBooleanVaule())
                        //mContentText = LKVMgr.sp().getBool(LKVMGR_CONTENT, getWriterBooleanVaule()).toString()
                    }
                    Companion.LKVMgrWriteType.WRITE_OBJ.value -> {
                        personal = LKVMgr.sp().getObj(LKVMGR_CONTENT, Personal::class.java)
                        mContentText = "对象：" + personal.name + personal.age
                    }
                    Companion.LKVMgrWriteType.WRITE_LIST.value -> {
                        mContentText = listToString(LKVMgr.sp().getList(LKVMGR_CONTENT, Personal::class.java))
                    }
                    Companion.LKVMgrWriteType.WRITE_MAP.value -> {
                        mContentText = mapToString(LKVMgr.sp().getMap(LKVMGR_CONTENT, String::class.java, Personal::class.java))
                    }
                }
            }
        }//  when (mSaveType)
        if (LEmptyX.isEmpty(mContentText)) {
            ToastUtils.showShort("没有读取到内容")
        } else {
            contentEditText.setText(mContentText)
            contentTextView.text = mContentText
            ToastUtils.showShort("读取成功")
        }

    } // readContent


    private fun mapToString(map: Map<String, Personal>?): String {
        if (LEmptyX.isEmpty(map)) {
            return ""
        }
        val build: StringBuilder = StringBuilder()
        map?.forEach { key, value ->
            build.append(key + "-")
            build.append(value.name + "-" + value.age)
            build.append(" ")
        }
        return build.toString()
    }

    private fun listToString(list: MutableList<Personal>?): String {
        if (LEmptyX.isEmpty(list)) {
            return ""
        }
        if (list == null) {
            return ""
        }
        val build: StringBuilder = StringBuilder()
        for (p: Personal in list) {
            build.append(p.name + "-" + p.age + " ")
        }
        return build.toString()
    }

    fun writeObj() {
        val p1 = Personal("aa", 11)
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        when (mSaveType) {
            Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                LKVMgr.memory().putObj(LKVMGR_CONTENT, p1)
            }
            Companion.LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                LKVMgr.mmkv().putObj(LKVMGR_CONTENT, p1)
            }
            Companion.LKVMgrSaveType.SAVE_ON_SP.value -> {
                LKVMgr.sp().putObj(LKVMGR_CONTENT, p1)
            }
        }//  when (mSaveType)
        ToastUtils.showShort("保存成功")
    }

    fun writeList() {
        val p1 = Personal("aa", 11)
        val p2 = Personal("bb", 22)
        val p3 = Personal("cc", 33)
        val p4 = Personal("dd", 44)
        val p5 = Personal("ee", 55)
        val list = ArrayList<Personal>()
        list.add(p1)
        list.add(p2)
        list.add(p3)
        list.add(p4)
        list.add(p5)
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        when (mSaveType) {
            Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                LKVMgr.memory().putList(LKVMGR_CONTENT, list)
            }
            Companion.LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                LKVMgr.mmkv().putList(LKVMGR_CONTENT, list)
            }
            Companion.LKVMgrSaveType.SAVE_ON_SP.value -> {
                LKVMgr.sp().putList(LKVMGR_CONTENT, list)
            }
        }//  when (mSaveType)
        ToastUtils.showShort("保存成功")
    }

    fun writeMap() {
        val p1 = Personal("aa", 11)
        val p2 = Personal("bb", 22)
        val p3 = Personal("cc", 33)
        val p4 = Personal("dd", 44)
        val p5 = Personal("ee", 55)
        val map = HashMap<String, Personal>()
        map.put("A1", p1)
        map.put("A2", p2)
        map.put("A3", p3)
        map.put("A4", p4)
        map.put("A5", p5)
        mSaveType = LKVMgr.getInstance().getInt(LKVMGR_SAVE_TYPE, 0)
        when (mSaveType) {
            Companion.LKVMgrSaveType.SAVE_ON_MEMORY.value -> {
                LKVMgr.memory().putMap(LKVMGR_CONTENT, map)
            }
            Companion.LKVMgrSaveType.SAVE_ON_MMKV.value -> {
                LKVMgr.mmkv().putMap(LKVMGR_CONTENT, map)
            }
            Companion.LKVMgrSaveType.SAVE_ON_SP.value -> {
                LKVMgr.sp().putMap(LKVMGR_CONTENT, map)
            }
        }//  when (mSaveType)
        ToastUtils.showShort("保存成功")
    }

    class Personal(var name: String, var age: Int)
}
