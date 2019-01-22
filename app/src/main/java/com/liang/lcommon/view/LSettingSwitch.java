package com.liang.lcommon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.liang.lcommon.R;

/**
 * Created by Amarao on 2018/4/4.
 */

public class LSettingSwitch extends RelativeLayout {

    //定义需要的控件
    ImageView leftPictureView;  //功能图片
    Switch    switchOption;        //switch选项

    //定义需要的控件
    private Drawable bigBackground; // 背景
    private Drawable leftPicture; // 左边图片
    private String   switchText; // 文本
    private Drawable switchThum; // 锚点
    private Boolean  switchChecked; // 选中状态

    //自定义监听
    public SwitchClickListener         listener;
    public SwitchCheckedChangeListener checkedChangeListener;

    //点击接口
    public interface SwitchClickListener {
        void switchListener(View v);
    }

    //状态改变接口
    public interface SwitchCheckedChangeListener {
        void switchCheckedChangeListener(CompoundButton buttonView, boolean isChecked);
    }

    //设置监听的方法
    public void setListener(SwitchClickListener listener) {
        this.listener = listener;
    }

    public void setSwitchCheckedChangeListener(SwitchCheckedChangeListener changelistener) {
        this.checkedChangeListener = changelistener;
    }

    //构造方法
    public LSettingSwitch(Context context) {
        super(context);
        initView(null);
    }

    public LSettingSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        //动态加载布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lsetting_switch, this);
        leftPictureView = (ImageView) view.findViewById(R.id.left_picture_view);
        switchOption = (Switch) view.findViewById(R.id.switch_button);
        //点击监听
        switchOption.setOnClickListener(v -> {
            if (listener != null) {
                listener.switchListener(v);
            }
        });

        //状态改变
        switchOption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkedChangeListener != null) {
                checkedChangeListener.switchCheckedChangeListener(buttonView, isChecked);
            }
        });

        if (attrs != null) {
            //获得xml中的属性，并赋给控件
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LSettingSwitch);

            //从TypedArray取出自定义属性值，并赋值给相应的变量
            bigBackground = typedArray.getDrawable(R.styleable.LSettingSwitch_bigBackground);
            leftPicture = typedArray.getDrawable(R.styleable.LSettingSwitch_leftPicture);
            switchText = typedArray.getString(R.styleable.LSettingSwitch_switchText);
            switchThum = typedArray.getDrawable(R.styleable.LSettingSwitch_switchThum);
            switchChecked = typedArray.getBoolean(R.styleable.LSettingSwitch_switchChecked, false);

            //把自定义属性赋值给控件
            //leftPictureView.setImageResource(leftPicture);
            leftPictureView.setBackground(leftPicture);
            switchOption.setThumbDrawable(switchThum);
            switchOption.setText(switchText);
            switchOption.setChecked(switchChecked);

            ///如果这是一个空的
            if (TextUtils.isEmpty(switchText)) {
                //默认
                switchOption.setText("请输入文字");
            }

            //设置switchPlus背景
            setBackground(bigBackground);

            //使用TypedArray后，要回收资源
            typedArray.recycle();
        }
    }

    public void setSwitchThum(Drawable thum) {
        this.switchThum = thum;
        switchOption.setThumbDrawable(switchThum);
    }

    public void setSwitchText(String str) {
        this.switchText = str;
        switchOption.setText(switchText);
    }

    public void setSwitchChecked(boolean flag) {
        this.switchChecked = flag;
        switchOption.setChecked(switchChecked);
    }

    public boolean isChecked() {
        return switchOption.isChecked();
    }
}
