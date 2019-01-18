package com.liang.lcommon.view.remoteview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.liang.lcommon.R;

/**
 * Created by leeby on 2017/7/19.
 */

public class RemoteControlView extends View {

    private float scale = this.getResources().getDisplayMetrics().density;
    private Paint mPaint;
    //父布局宽高
    private int   mWidth;
    private int   mHeight;
    //虚拟摇杆中心坐标
    private float posX;
    private float posY;

    //控制旋转的矩阵
    private Matrix matrix;
    private Rect   src;//外层图片的绘制区域
    private Rect   srcNoActive;//外层图片无指针的绘制区域
    private Rect   dst;//外层图片触摸时的绘制区域
    private Rect   dstNoActive;//外层图片未触摸时的绘制区域
    private Rect   srcInner;//中心悬浮球绘制区域
    private Rect   dstInner;//中心悬浮球，实时测量绘制区域

    private Bitmap bitmap;//外层图箭头
    private Bitmap bitmapInner;//中心悬浮球
    private Bitmap bitmap1;//外侧边缘指针图
    private Bitmap bitmap2;//外层边缘无指针图

    //外侧边缘指针图片的高度和宽度
    private int nBitmapWidth;
    private int nBitmapHeight;
    //
    private int RockerCircleX;
    private int RockerCircleY;

    private int   minRadius = 0;
    private float tempRad   = 0;

    private RemoteViewBg innerBg;
    private RemoteViewBg remoteViewBg;

    private boolean isStart = false;

    public RemoteControlView(Context context) {
        super(context);
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //外层图箭头
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.control_rocker_arrow);
        //中心悬浮球
        bitmapInner = BitmapFactory.decodeResource(getResources(), R.drawable.control_rocker_paws);
        //外侧边缘指针图
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.control_rocker_bg);
        //外层边缘无指针图
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.control_rocker_not_active);

        //  获取图片高度和宽度
        nBitmapWidth = bitmap1.getWidth();
        nBitmapHeight = bitmap1.getHeight();

        //外层图片有指针的绘制区域
        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        //外侧图片无指针的绘制区域
        srcNoActive = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        //中心悬浮球绘制区域
        srcInner = new Rect(0, 0, bitmapInner.getWidth(), bitmapInner.getHeight());

        //外层背景
        remoteViewBg = new RemoteViewBg(bitmap);
        //内圆
        innerBg = new RemoteViewBg(bitmapInner);
        matrix = new Matrix();
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //onMeasure测量时，通过bitmap宽高，获取外层图片的显示区域（指定图片在屏幕上显示的区域）
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //得到View需要的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //初始化坐标
        posX = mWidth / 2;
        posY = mHeight / 2;
        minRadius = mWidth / 6;

        //触摸
        dst = new Rect((int) mWidth / 2 - bitmap.getWidth() / 2, (int) mHeight / 2 - bitmap.getHeight() / 2, (int) mWidth / 2 + bitmap.getWidth() / 2, (int) mHeight / 2 + bitmap.getHeight() / 2);
        //未触摸
        dstNoActive = new Rect((int) mWidth / 2 - bitmap2.getWidth() / 2, (int) mHeight / 2 - bitmap2.getHeight() / 2, (int) mWidth / 2 + bitmap2.getWidth() / 2, (int) mHeight / 2 + bitmap2.getHeight() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();

        remoteViewBg.draw(canvas, mPaint, src, dst);

        //对中心悬浮球，实时测量绘制区域
        dstInner = new Rect((int) posX - minRadius, (int) posY - minRadius, (int) posX + minRadius, (int) posY + minRadius);
        innerBg.draw(canvas, mPaint, srcInner, dstInner);

        //通过Matrix矩阵移动及旋转，计算控制外层指针的旋转角度，实现跟随手指方向
        matrix.reset();
        //在xy轴平移，
        matrix.setTranslate(mWidth / 2 - bitmap1.getWidth() / 2, mHeight / 2 - bitmap1.getHeight() / 2);

        if (tempRad != 0) {
            //设置旋转，参数：旋转角度，原点坐标x，原点坐标y
            matrix.preRotate(tempRad + 90, (float) bitmap1.getWidth() / 2, (float) bitmap1.getHeight() / 2);  //要旋转的角度
        } else {
            //设置旋转，参数：旋转角度
            matrix.preRotate(tempRad);
        }

        if (isStart) {
            canvas.drawBitmap(bitmap1, matrix, null);
        } else {
            canvas.drawBitmap(bitmap2, srcNoActive, dstNoActive, null);
        }

        matrix.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isStart = true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 在范围外触摸
            if (Math.sqrt(Math.pow((mWidth / 2 - (int) event.getX()), 2) + Math.pow((mWidth / 2 - (int) event.getY()), 2)) >= (mWidth / 2 - 50 * scale)) {
                tempRad = getRad(mWidth / 2, mHeight / 2, event.getX(), event.getY());
                getXY(mWidth / 2, mHeight / 2, bitmap.getWidth() / 2 - minRadius, tempRad);
                tempRad = getAngle(event.getX(), event.getY());
            } else {//范围内触摸
                posX = event.getX();
                posY = event.getY();
                tempRad = getAngle(event.getX(), event.getY());
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isStart = false;
            posX = mWidth / 2;
            posY = mHeight / 2;
            tempRad = 0;
        }
        invalidate();
        return true;
    }

    private float getAngle(float xTouch, float yTouch) {
        RockerCircleX = mWidth / 2;
        RockerCircleY = mHeight / 2;
        return (float) (getRad(RockerCircleX, RockerCircleY, xTouch, yTouch) * 180f / Math.PI);
    }

    public void getXY(float x, float y, float R, double rad) {
        //获取圆周运动的X坐标
//        posX = (float) (R * Math.cos(rad)) + x;
        posX = (float) (R * Math.cos(rad)) + x;
        //获取圆周运动的Y坐标
//        posY = (float) (R * Math.sin(rad)) + y;
        posY = (float) (R * Math.sin(rad)) + y;
    }

    //得到两点之间的弧度
    public float getRad(float px1, float py1, float px2, float py2) {
        float x = px2 - px1;
        float y = py1 - py2;
        //斜边的长
        float z = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float cosAngle = x / z;
        float rad = (float) Math.acos(cosAngle);

        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}

