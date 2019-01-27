package com.liang.lcommon.activity.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.liang.lcommon.R;
import com.liang.lcommon.activity.LBaseItemBean;
import com.liang.lcommon.adapter.LJsonAdapter;
import com.liang.lcommon.app.LAppActivity;
import com.liang.lcommon.exts.LRouter;
import com.liang.lcommon.mgrs.LKVMgr;
import com.liang.lcommon.utils.LLogX;
import com.liang.lcommon.view.LRockerViewV2;
import com.march.common.exts.LogX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RockerActivityDemo extends LAppActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, RockerActivityDemo.class));
    }

    public static LBaseItemBean getItem() {
        String intro = "虚拟摇杆\n1.仿王者荣耀摇杆\n2.自定义虚拟摇杆\n  锚点偏移级别 0-9\n  可监听角度变化\n摇杆方向可设置为二四八个方向";
        int src = R.mipmap.demo_rocker_view;
        return new LBaseItemBean(intro, src);
    }

    public static LBaseItemBean.ClickEvent getClickEvent() {
        return LRouter::startRockerActivity;
    }

    //虚拟摇杆
    private LRockerViewV2 mRockerViewXY;
    private LRockerViewV2 mRockerViewZ;
    private String        directionXY;
    private String        angleXY;
    private String        levelXY;
    private String        directionZ;
    private String        angleZ;
    private String        levelZ;
    //文本
    private TextView      directionXY_Text;
    private TextView      angleXY_Text;
    private TextView      levelXY_Text;
    private TextView      directionZ_Text;
    private TextView      angleZ_Text;
    private TextView      levelZ_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_rocker_activity);
        //界面初始化
        initView();
        //摇杆初始化
        initMyView();
        //摇杆点击事件
        initMyClick();
        //创建列表并添加数据

        String lkvmgrContent = LKVMgrDemo.Companion.getLKVMGR_CONTENT();
//        Map<String, Personal> map = LKVMgr.mmkv().getMap(lkvmgrContent, String.class, Personal.class);
//        for (Map.Entry<String,Personal> entry:map.entrySet()){
//            //Personal value = entry.getValue();
//            String value = entry.getValue().toString();
//            LLogX.e("name1 = "+ value);
//            Personal personal = LJsonAdapter.getAdapter().toObj(value, Personal.class);
//            LLogX.e("name = "+ personal.getName());
//        }
        String f1 = "QQQ";
        String f2 = "AAA";
        Personal aa = new Personal("aa", 11);
        Personal bb = new Personal("bb", 22);
        Personal cc = new Personal("cc", 33);
        List<Personal> list = new ArrayList<>();
        Map<String, Personal> map1 = new HashMap<>();
        Map<Integer, Personal> m2 = new HashMap<>();
        map1.put("B1", aa);
        map1.put("B2", bb);
        map1.put("B3", cc);
        m2.put(1, aa);
        m2.put(2, bb);
        m2.put(3, cc);
        list.add(aa);
        list.add(bb);
        list.add(cc);

// {"age":"11","name":"aa"}
// {age=11, name=aa}
//{"age":11,"name":"aa"}
// {age=22.0, name=bb}
// {"B2":{"age":22,"name":"bb"},"B3":{"age":33,"name":"cc"},"B1":{"age":11,"name":"aa"}}

        LLogX.e("存入 map");
        LKVMgr.mmkv().putMap(f1, m2,Integer.class,Personal.class);
        //foreachMap(map1);
        printMap(m2);

        String string = LKVMgr.mmkv().getString(f1);
        LLogX.e("读取 map = " + string);

        Map<String, Personal> map2 = LJsonAdapter.getAdapter().toStringKeyMap(string, Personal.class);
        Map<Integer, Personal> map3 = LKVMgr.mmkv().getMap(f1,Integer.class,Personal.class);

        printMap(map3);

    }

    private <K, V> void printMap(Map<K, V> map) {
        if (map == null) {
            LLogX.e("空的啊");
            return;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            K entryKey = entry.getKey();
            V value = entry.getValue();
            LogX.e(" key = " + entryKey + " value = " + value);
            if (value instanceof Personal) {
                LogX.e(" value2 : {"
                        + " name = " + ((Personal) value).age
                        + " age = " + ((Personal) value).name + " }");
            }
        }
    }

    private void foreachMap(Map<String, Personal> map2) {
        if (map2 == null) {
            LLogX.e("空的");
            return;
        }
        //LinkedTreeMap tm = (LinkedTreeMap) map2;
//        Iterator it = map2.keySet().iterator();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            Object o = map2.get(key);
//            LKVMgr.mmkv().putObj("YML", o);
//            Personal value = LKVMgr.mmkv().getObj("YML", Personal.class);
//            LLogX.e("ttt key = " + key + " value : name = " + value.name + " age = " + value.age);
//        }

        for (Map.Entry<String, Personal> entry : map2.entrySet()) {
            //String s = String.valueOf(entry.getValue());
            //LLogX.e(" test = " + entry.getValue() + " t2= " + s);
//            if (personal != null){
//                LLogX.e("t3 = name = "+personal.name+" age = "+personal.age);
//            }else {
//                LLogX.e("空的11");
//            }

            LLogX.e(" key = " + entry.getKey() + " value : {"
                    + " name = " + entry.getValue().name
                    + " age = " + entry.getValue().age + " }");
        }
    }

    class Personal {
        String name;
        int    age;

        public Personal(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {

            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    //界面初始化
    private void initView() {
        //文本
        directionXY_Text = (TextView) findViewById(R.id.directionXY_Text);
        angleXY_Text = (TextView) findViewById(R.id.angleXY_Text);
        levelXY_Text = (TextView) findViewById(R.id.levelXY_Text);

        directionZ_Text = (TextView) findViewById(R.id.directionZ_Text);
        angleZ_Text = (TextView) findViewById(R.id.angleZ_Text);
        levelZ_Text = (TextView) findViewById(R.id.levelZ_Text);
    }

    //摇杆初始化
    private void initMyView() {
        //方向有改变时回调
        mRockerViewXY = (LRockerViewV2) findViewById(R.id.rockerXY_View);//8方向
        mRockerViewZ = (LRockerViewV2) findViewById(R.id.rockerZ_View);//2方向
    }

    //摇杆点击事件
    private void initMyClick() {

        //xy轴
        //方向
        mRockerViewXY.setOnShakeListener(LRockerViewV2.DirectionMode.DIRECTION_8, new LRockerViewV2.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(LRockerViewV2.Direction direction) {
                if (direction == LRockerViewV2.Direction.DIRECTION_CENTER) {
                    directionXY = ("当前方向：中心");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN) {
                    directionXY = ("当前方向：下");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_LEFT) {
                    directionXY = ("当前方向：左");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_UP) {
                    directionXY = ("当前方向：上");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_RIGHT) {
                    directionXY = ("当前方向：右");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN_LEFT) {
                    directionXY = ("当前方向：左下");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN_RIGHT) {
                    directionXY = ("当前方向：右下");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_UP_LEFT) {
                    directionXY = ("当前方向：左上");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_UP_RIGHT) {
                    directionXY = ("当前方向：右上");
                }

                LLogX.e("XY轴" + directionXY);
                LLogX.e("-----------------------------------------------");
                directionXY_Text.setText(directionXY);
            }

            @Override
            public void onFinish() {

            }
        });
        //角度
        mRockerViewXY.setOnAngleChangeListener(new LRockerViewV2.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                angleXY = ("当前角度：" + angle);
                LLogX.e("XY轴" + angleXY);
                angleXY_Text.setText(angleXY);
            }

            @Override
            public void onFinish() {

            }
        });
        //级别
        mRockerViewXY.setOnDistanceLevelListener(new LRockerViewV2.OnDistanceLevelListener() {
            @Override
            public void onDistanceLevel(int level) {
                levelXY = ("当前距离级别：" + level);
                LLogX.e("XY轴" + levelXY);
                levelXY_Text.setText(levelXY);
            }
        });
        //z轴
        mRockerViewZ.setOnShakeListener(LRockerViewV2.DirectionMode.DIRECTION_2_VERTICAL, new LRockerViewV2.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(LRockerViewV2.Direction direction) {
                if (direction == LRockerViewV2.Direction.DIRECTION_UP) {
                    directionZ = ("当前方向：上");
                } else if (direction == LRockerViewV2.Direction.DIRECTION_DOWN) {
                    directionZ = ("当前方向：下");
                }
                LLogX.e("Z轴" + directionZ);
                LLogX.e("-----------------------------------------------");
                directionZ_Text.setText(directionZ);
            }

            @Override
            public void onFinish() {

            }
        });
        mRockerViewZ.setOnAngleChangeListener(new LRockerViewV2.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                angleZ = ("当前角度：" + angle);
                LLogX.e("Z轴" + angleZ);
                angleZ_Text.setText(angleZ);
            }

            @Override
            public void onFinish() {

            }
        });
        mRockerViewZ.setOnDistanceLevelListener(new LRockerViewV2.OnDistanceLevelListener() {
            @Override
            public void onDistanceLevel(int level) {
                levelZ = ("当前距离级别：" + level);
                LLogX.e("Z轴" + levelZ);
                levelZ_Text.setText(levelZ);
            }
        });
    }


}
