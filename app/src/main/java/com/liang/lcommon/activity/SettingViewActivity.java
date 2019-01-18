package com.liang.lcommon.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.liang.lcommon.R;
import com.liang.lcommon.utils.LLogX;
import com.liang.lcommon.utils.LResourceX;
import com.liang.lcommon.view.LSettingSwitch;

public class SettingViewActivity extends AppCompatActivity {

    public static void startActivity(Activity activity){
        activity.startActivity(new Intent(activity,SettingViewActivity.class));
    }
    //自定义switch
    private LSettingSwitch gravitySwitch;
    private LSettingSwitch     bluetoothSwitch;
    private LSettingSwitch     wifiSwitch;
    //系统开关
    private Button         modeButton1;
    private ImageButton    modeButton2;
    private ImageButton    backButton;
    //按钮选中开关
    private boolean isGravityOpen    = true;
    private boolean isBluetoothOpen  = true;
    private boolean isWifiOpen       = false;
    //返回内容的intent
    private Intent  returnDataIntent =  new Intent();
    //设置界面的本地数据
    SharedPreferences        sharedPreferences;
    SharedPreferences.Editor settingDataEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_view);

        initMyView();
        //初始化数据
        initSettingData();
        //得到本地设置
        initSetting();
    }

    //设置界面的初始化数据
    private void initSettingData() {
       LLogX.e( "设置界面初始化数据" );

        //得到本地sharedPreferences文件管理对象
        sharedPreferences = getSharedPreferences("settingData",MODE_PRIVATE);
        if(sharedPreferences.getString("isExists",null) != null){
           LLogX.e( "文件存在->数据恢复" );
        }else {
           LLogX.e( "文件不存在->初始化" );
            //1.获取一个Editor对象
            settingDataEditor = getSharedPreferences("settingData",MODE_PRIVATE).edit();
            //2.添加数据
            settingDataEditor.putString("isExists","exists");
            settingDataEditor.putBoolean("gravityData",true);
            settingDataEditor.putBoolean("bluetoothData",true);
            settingDataEditor.putBoolean("wifiData",false);
            //3.提交
            settingDataEditor.apply();
        }
    }
    //得到本地设置
    private void initSetting() {
        //得到本地sharedPreferences文件管理对象和编辑对象
        sharedPreferences = getSharedPreferences("settingData",MODE_PRIVATE);
        settingDataEditor = getSharedPreferences("settingData",MODE_PRIVATE).edit();
        isGravityOpen = sharedPreferences.getBoolean("gravityData",true);
        isBluetoothOpen = sharedPreferences.getBoolean("bluetoothData",true);
        isWifiOpen = sharedPreferences.getBoolean("wifiData",false);

        gravitySwitch.setSwitchChecked(isGravityOpen);
        bluetoothSwitch.setSwitchChecked(isBluetoothOpen);
        wifiSwitch.setSwitchChecked(isWifiOpen);

        if (isGravityOpen){
            gravitySwitch.setSwitchText("重力辅助(On)");
        }else {
            gravitySwitch.setSwitchText("重力辅助(Off)");
        }
        if (isBluetoothOpen){
            bluetoothSwitch.setSwitchText("蓝牙控制(On)");
        }else {
            bluetoothSwitch.setSwitchText("蓝牙控制(Off)");
        }
        if (isWifiOpen){
            wifiSwitch.setSwitchText("Wifi控制(On)");
        }else {
            wifiSwitch.setSwitchText("Wifi控制(Off)");
        }
    }
    //实例化
    private void initMyView(){
        /*自定义控件*/
        gravitySwitch = findViewById(R.id.gravity_switch);
        bluetoothSwitch = findViewById(R.id.bluetooth_switch);
        wifiSwitch = findViewById(R.id.wifi_switch);

        gravitySwitch.setListener(v -> {
            if(isGravityOpen == true){
                isGravityOpen = false;
                gravitySwitch.setSwitchThum(LResourceX.getDrawable(SettingViewActivity.this,R.drawable.gravity_off));
                gravitySwitch.setSwitchText("重力辅助(Off)");
                Toast.makeText(SettingViewActivity.this,"重力辅助(Off)",Toast.LENGTH_SHORT).show();
                settingDataEditor.putBoolean("gravityData",false);
                returnDataIntent.putExtra("setting_gravity","off");
            }else {
                isGravityOpen = true;
                gravitySwitch.setSwitchThum(LResourceX.getDrawable(SettingViewActivity.this,R.drawable.gravity_1));
                gravitySwitch.setSwitchText("重力辅助(On)");
                Toast.makeText(SettingViewActivity.this,"重力辅助(On)",Toast.LENGTH_SHORT).show();
                settingDataEditor.putBoolean("gravityData",true);
                returnDataIntent.putExtra("setting_gravity","on");
            }
            settingDataEditor.commit();//提交修改
        });
        bluetoothSwitch.setListener(v -> {

                if(isBluetoothOpen == true){
                    isBluetoothOpen = false;
                    bluetoothSwitch.setSwitchThum(LResourceX.getDrawable(SettingViewActivity.this,R.drawable.bluetooth_off));
                    bluetoothSwitch.setSwitchText("蓝牙控制(Off)");
                    Toast.makeText(SettingViewActivity.this,"蓝牙控制(Off)",Toast.LENGTH_SHORT).show();
                    settingDataEditor.putBoolean("bluetoothData",false);
                    returnDataIntent.putExtra("setting_bluetooth","off");
                }else {
                    isBluetoothOpen = true;
                    bluetoothSwitch.setSwitchThum(LResourceX.getDrawable(SettingViewActivity.this,R.drawable.bluetooth_1));
                    bluetoothSwitch.setSwitchText("蓝牙控制(On)");
                    Toast.makeText(SettingViewActivity.this,"蓝牙控制(On)",Toast.LENGTH_SHORT).show();
                    settingDataEditor.putBoolean("bluetoothData",true);
                    returnDataIntent.putExtra("setting_bluetooth","on");
                }
                settingDataEditor.commit();//提交修改

        });
        wifiSwitch.setListener(v -> {
                if(isWifiOpen == true){
                    isWifiOpen = false;
                    wifiSwitch.setSwitchThum(LResourceX.getDrawable(SettingViewActivity.this,R.drawable.wifi_off));
                    wifiSwitch.setSwitchText("Wifi控制(Off)");
                    Toast.makeText(SettingViewActivity.this,"Wifi控制(Off)",Toast.LENGTH_SHORT).show();
                    settingDataEditor.putBoolean("wifiData",false);
                    returnDataIntent.putExtra("setting_wifi","off");
                }else {
                    isWifiOpen = true;
                    wifiSwitch.setSwitchThum(LResourceX.getDrawable(SettingViewActivity.this,R.drawable.wifi_1));
                    wifiSwitch.setSwitchText("Wifi控制(On)");
                    Toast.makeText(SettingViewActivity.this,"Wifi控制(On)",Toast.LENGTH_SHORT).show();
                    settingDataEditor.putBoolean("wifiData",true);
                    returnDataIntent.putExtra("setting_wifi","on");
                }
                settingDataEditor.commit();//提交修改

        });
    }

    //重写系统返回键
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK,returnDataIntent);//返回数据
        settingDataEditor.commit();//提交修改
        super.onBackPressed();
    }
}
