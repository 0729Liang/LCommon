package com.liang.lcommon.demo.blog.lineChart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.liang.lcommon.R;
import com.liang.lcommon.exts.LRouter;
import com.liang.lcommon.moudle.main2.bean.LBaseItemBean;
import com.liang.lcommon.web.LWebActivity;
import com.liang.liangutils.utils.LColorUtils;
import com.liang.liangutils.utils.LFixInputMethodManagerLeak;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : amarao
 * @date: 2019-06-30
 * described: LineChartDemo
 */
public class LineChartDemo extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, LineChartDemo.class));
    }

    public static LBaseItemBean newItem() {
        String title = "LineChart";
        String intro = "MpAndroidChart-LineChart 折线图使用\n动态添加点\n动态添加曲线";
        int src = R.drawable.demo_linechart;
        LBaseItemBean bean = new LBaseItemBean(title, intro, src);
        bean.setClickEvent(getClickEvent());
        bean.setType(LBaseItemBean.BeanType.BLOG);
        bean.setUrl("https://blog.csdn.net/jinmie0193/article/details/90239935");
        return bean;
    }

    public static LBaseItemBean.ClickEvent getClickEvent() {
        return LRouter::startLineChartDemo;
    }

    public static final int MSG_START = 1; // handler消息，开始添加点

    // 折线编号
    public static final int LINE_NUMBER_1 = 0;

    public static final int LINE_NUMBER_2 = 1;

    public static final int LINE_NUMBER_3 = 2;

    Button mBtnStart;   // 开始添加点

    Button mBtnPause;   // 暂停添加点

    TextView mBlogTextView; //博客介绍

    CheckBox mCheckBox1;

    CheckBox mCheckBox2;

    CheckBox mCheckBox3;

    List<CheckBox> mCheckBoxList = new ArrayList<>();

    LineChart mLineChart; // 折线表，存线集合

    LineData mLineData; // 线集合，所有折现以数组的形式存到此集合中

    XAxis mXAxis; //X轴

    YAxis mLeftYAxis; //左侧Y轴

    YAxis mRightYAxis; //右侧Y轴

    Legend mLegend; //图例

    LimitLine mLimitline; //限制线

    //  Y值数据链表
    List<Float> mList1 = new ArrayList<>();

    List<Float> mList2 = new ArrayList<>();

    List<Float> mList3 = new ArrayList<>();

    // Chart需要的点数据链表
    List<Entry> mEntries1 = new ArrayList<>();

    List<Entry> mEntries2 = new ArrayList<>();

    List<Entry> mEntries3 = new ArrayList<>();

    // LineDataSet:点集合,即一条线
    LineDataSet mLineDataSet1 = new LineDataSet(mEntries1, "折线1");

    LineDataSet mLineDataSet2 = new LineDataSet(mEntries2, "折线2");

    LineDataSet mLineDataSet3 = new LineDataSet(mEntries3, "折线3");

    private DemoHandler mDemoHandler; // 自定义Handler

    private Random mRandom = new Random(); // 随机产生点

    private DecimalFormat mDecimalFormat = new DecimalFormat("#.00");   // 格式化浮点数位两位小数

    /**
     * 功能：启动方式
     */
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LineChartDemo.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_line_chart);

        mDemoHandler = new DemoHandler(this);
        initView();
        initLineChart();
    }


    /**
     * 功能：产生随机数（小数点两位）
     */
    public Float getRandom(Float seed) {
        return Float.valueOf(mDecimalFormat.format(mRandom.nextFloat() * seed));
    }

    /**
     * 功能：初始化基本控件，button，checkbox
     */
    public void initView() {
        mBtnStart = findViewById(R.id.demo_start);
        mBtnPause = findViewById(R.id.demo_pause);
        mBlogTextView = findViewById(R.id.demo_blog);
        mCheckBox1 = findViewById(R.id.demo_checkbox1);
        mCheckBox2 = findViewById(R.id.demo_checkbox2);
        mCheckBox3 = findViewById(R.id.demo_checkbox3);
        mCheckBoxList.add(mCheckBox1);
        mCheckBoxList.add(mCheckBox2);
        mCheckBoxList.add(mCheckBox3);

        mBtnStart.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mCheckBox1.setOnClickListener(this);
        mCheckBox2.setOnClickListener(this);
        mCheckBox3.setOnClickListener(this);

        mBlogTextView.setOnClickListener(v -> {
            LWebActivity.startSystemBrowser(this, newItem().getUrl());
            //LWebActivity.startActivityStartHttp(this, newItem().getUrl(), "asda");
        });
    }

    /**
     * 功能：初始化LineChart
     */
    public void initLineChart() {
        mLineChart = findViewById(R.id.demo_linechart);
        mXAxis = mLineChart.getXAxis(); // 得到x轴
        mLeftYAxis = mLineChart.getAxisLeft(); // 得到侧Y轴
        mRightYAxis = mLineChart.getAxisRight(); // 得到右侧Y轴
        mLegend = mLineChart.getLegend(); // 得到图例
        mLineData = new LineData();
        mLineChart.setData(mLineData);

        // 设置图标基本属性
        setChartBasicAttr(mLineChart);

        // 设置XY轴
        setXYAxis(mLineChart, mXAxis, mLeftYAxis, mRightYAxis);

        // 添加线条
        initLine();

        // 设置图例
        createLegend(mLegend);

        // 设置MarkerView
        setMarkerView(mLineChart);
    }

    /**
     * 功能：设置图标的基本属性
     */
    void setChartBasicAttr(LineChart lineChart) {
        /***图表设置***/
        lineChart.setDrawGridBackground(false); //是否展示网格线
        lineChart.setDrawBorders(true); //是否显示边界
        lineChart.setDragEnabled(true); //是否可以拖动
        lineChart.setScaleEnabled(true); // 是否可以缩放
        lineChart.setTouchEnabled(true); //是否有触摸事件
        //设置XY轴动画效果
        //lineChart.animateY(2500);
        lineChart.animateX(1500);
    }

    /**
     * 功能：设置XY轴
     */
    void setXYAxis(LineChart lineChart, XAxis xAxis, YAxis leftYAxis, YAxis rightYAxis) {
        /***XY轴的设置***/
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //X轴设置显示位置在底部
        xAxis.setAxisMinimum(0f); // 设置X轴的最小值
        xAxis.setAxisMaximum(20); // 设置X轴的最大值
        xAxis.setLabelCount(20, false); // 设置X轴的刻度数量，第二个参数表示是否平均分配
        xAxis.setGranularity(1f); // 设置X轴坐标之间的最小间隔
        lineChart.setVisibleXRangeMaximum(5);// 当前统计图表中最多在x轴坐标线上显示的总量
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);
        rightYAxis.setAxisMaximum(100f);
        leftYAxis.setGranularity(1f);
        rightYAxis.setGranularity(1f);
        leftYAxis.setLabelCount(20);
        lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.LEFT);// 当前统计图表中最多在Y轴坐标线上显示的总量
        lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.RIGHT);// 当前统计图表中最多在Y轴坐标线上显示的总量
        leftYAxis.setEnabled(false);

        //        leftYAxis.setCenterAxisLabels(true);// 将轴标记居中
        //        leftYAxis.setDrawZeroLine(true); // 原点处绘制 一条线
        //        leftYAxis.setZeroLineColor(Color.RED);
        //        leftYAxis.setZeroLineWidth(1f);
    }

    /**
     * 功能：对图表中的曲线初始化，添加三条，并且默认显示第一条
     */
    void initLine() {

        createLine(mList1, mEntries1, mLineDataSet1, LColorUtils.Colors.RED.getColor(), mLineData, mLineChart);
        createLine(mList2, mEntries2, mLineDataSet2, LColorUtils.Colors.ORANGE.getColor(), mLineData, mLineChart);
        createLine(mList3, mEntries3, mLineDataSet3, LColorUtils.Colors.YELLOW.getColor(), mLineData, mLineChart);

        // mLineData.getDataSetCount() 总线条数
        // mLineData.getEntryCount() 总点数
        // mLineData.getDataSetByIndex(index).getEntryCount() 索引index处折线的总点数
        // 每条曲线添加到mLineData后，从索引0处开始排列
        for (int i = 0; i < mLineData.getDataSetCount(); i++) {
            mLineChart.getLineData().getDataSets().get(i).setVisible(false); //
        }
        showLine(LINE_NUMBER_1);
    }

    /**
     * 功能：根据索引显示或隐藏指定线条
     */
    public void showLine(int index) {
        mLineChart
                .getLineData()
                .getDataSets()
                .get(index)
                .setVisible(mCheckBoxList.get(index).isChecked());
        mLineChart.invalidate();
    }

    /**
     * 功能：动态创建一条曲线
     */
    private void createLine(List<Float> dataList, List<Entry> entries, LineDataSet lineDataSet, int color, LineData lineData, LineChart lineChart) {
        for (int i = 0; i < dataList.size(); i++) {
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, dataList.get(i));// Entry(x,y)
            entries.add(entry);
        }

        // 初始化线条
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);

        if (lineData == null) {
            lineData = new LineData();
            lineData.addDataSet(lineDataSet);
            lineChart.setData(lineData);
        } else {
            lineChart.getLineData().addDataSet(lineDataSet);
        }

        lineChart.invalidate();
    }

    /**
     * 曲线初始化设置,一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color); // 设置曲线颜色
        lineDataSet.setCircleColor(color);  // 设置数据点圆形的颜色
        lineDataSet.setDrawCircleHole(false);// 设置曲线值的圆点是否是空心
        lineDataSet.setLineWidth(1f); // 设置折线宽度
        lineDataSet.setCircleRadius(3f); // 设置折现点圆点半径
        lineDataSet.setValueTextSize(10f);

        lineDataSet.setDrawFilled(true); //设置折线图填充
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }

    }

    /**
     * 功能：创建图例
     */
    private void createLegend(Legend legend) {
        /***折线图例 标签 设置***/
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView(LineChart lineChart) {
        LineChartMarkViewDemo mv = new LineChartMarkViewDemo(this);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    /**
     * 动态添加数据
     * 在一个LineChart中存放的折线，其实是以索引从0开始编号的
     *
     * @param yValues y值
     */
    public void addEntry(LineData lineData, LineChart lineChart, float yValues, int index) {

        // 通过索引得到一条折线，之后得到折线上当前点的数量
        int xCount = lineData.getDataSetByIndex(index).getEntryCount();

        Entry entry = new Entry(xCount, yValues); // 创建一个点
        lineData.addEntry(entry, index); // 将entry添加到指定索引处的折线中

        //通知数据已经改变
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();

        //把yValues移到指定索引的位置
        lineChart.moveViewToAnimated(xCount - 4, yValues, YAxis.AxisDependency.LEFT, 1000);// TODO: 2019/5/4 内存泄漏，异步 待修复
        lineChart.invalidate();
    }

    /**
     * 功能：第1条折线添加一个点
     */
    public void addLine1Data(float yValues) {
        addEntry(mLineData, mLineChart, yValues, LINE_NUMBER_1);
    }

    /**
     * 功能：第2条折线添加一个点
     */
    public void addLine2Data(float yValues) {
        addEntry(mLineData, mLineChart, yValues, LINE_NUMBER_2);
    }

    /**
     * 功能：第3条折线添加一个点
     */
    public void addLine3Data(float yValues) {
        addEntry(mLineData, mLineChart, yValues, LINE_NUMBER_3);
    }

    /**
     * 功能：发送开始
     */
    void sendStartAddEntry() {
        if (!mDemoHandler.hasMessages(MSG_START)) { // 判断是否有消息队列此消息，如果没有则发送
            mDemoHandler.sendEmptyMessageDelayed(MSG_START, 1000);
        }
    }

    /**
     * 功能：暂停添加点，即移除所有消息
     */
    void sendPauseAddEntry() {
        mDemoHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清空消息
        mDemoHandler.removeCallbacksAndMessages(null);
        mDemoHandler = null;

        LFixInputMethodManagerLeak.fixLeak(this);
        // moveViewToAnimated 移动到某个点，有内存泄漏，暂未修复，希望网友可以指着
        mLineChart.clearAllViewportJobs();
        mLineChart.removeAllViewsInLayout();
        mLineChart.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.demo_start:
                sendStartAddEntry();
                break;
            case R.id.demo_pause:
                sendPauseAddEntry();
                break;
            case R.id.demo_checkbox1:
                showLine(LINE_NUMBER_1);
                break;
            case R.id.demo_checkbox2:
                showLine(LINE_NUMBER_2);
                break;
            case R.id.demo_checkbox3:
                showLine(LINE_NUMBER_3);
                break;
            default:
        }
    }

    /**
     * 功能：自定义Handler，通过弱引用的方式防止内存泄漏
     */
    private static class DemoHandler extends Handler {

        WeakReference<LineChartDemo> mReference;

        DemoHandler(LineChartDemo activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LineChartDemo lineChartDemo = mReference.get();
            if (lineChartDemo == null) {
                return;
            }
            switch (msg.what) {
                case MSG_START:
                    lineChartDemo.addLine1Data(lineChartDemo.getRandom(30f));
                    lineChartDemo.addLine2Data(lineChartDemo.getRandom(20f));
                    lineChartDemo.addLine3Data(lineChartDemo.getRandom(10f));
                    lineChartDemo.sendStartAddEntry();
                    break;
                default:
            }
        }
    }
}
