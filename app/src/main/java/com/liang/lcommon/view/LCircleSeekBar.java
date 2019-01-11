package com.liang.lcommon.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.liang.lcommon.R;
import com.liang.lcommon.utils.LBitmapX;
import com.liang.lcommon.utils.LSizeX;

/**
 * @author : Amarao
 * CreateAt : 14:10 2019/1/5
 * Describe :  圆形 环形 seekBar
 */
public class LCircleSeekBar extends View {
    private static final double RADIAN      = 180 / Math.PI;// 1弧度对应的角度，180°= π弧度
    private static final int    START_ANGLE = -90;// 起始角度
    private static final int    CIRCLE_ANLE = 360; // 一圈的角度
    private final        String TAG         = getClass().getSimpleName();

    /**
     * xml 属性对应值
     */
    private class LCircleSeekBarConfig {
        private static final int LSEEKBAR_SHAPE_RING   = 0;
        private static final int LSEEKBAR_SHAPE_CIRCLE = 1;

        private static final int LSEEKBAR_LINE_CAP_ROUND  = 0;
        private static final int LSEEKBAR_LINE_CAP_SQUARE = 1;

        private static final int LSEEKBAR_THUMB_POSITION_MIDDLE = 0;
        private static final int LSEEKBAR_THUMB_POSITION_ABOVE  = 1;
        private static final int LSEEKBAR_THUMB_POSITION_BELOW  = 2;
    }

    public enum LShape {
        /**
         * seekbar的形状
         */
        LSEEKBAR_SHAPE_RING,
        LSEEKBAR_SHAPE_CIRCLE;

        private LShape() {
        }
    }

    public enum LCap {
        /**
         * 进度的笔帽形状
         */
        LSEEKBAR_LINE_CAP_ROUND,
        LSEEKBAR_LINE_CAP_SQUARE;

        private LCap() {
        }
    }

    public enum LThumbPosition {
        /**
         * 图标的位置
         */
        LSEEKBAR_THUMB_POSITION_MIDDLE,
        LSEEKBAR_THUMB_POSITION_ABOVE,
        LSEEKBAR_THUMB_POSITION_BELOW;

        private LThumbPosition() {
        }
    }

    private Context mContext;

    private Paint  mBasePaint;
    private Paint  mProPaint;
    private Paint  mScdProPaint;
    private RectF  mArcRectF;
    private Bitmap mThumBbitmap;
    private Matrix mMatrix;

    private int   mSize;
    private int   mWidth;
    private int   mHeight;
    private float mCircleX;
    private float mCircleY;
    private float mCircleRadius;
    private float mPointOfftX;
    private float mPointOfftY;
    private float mProgressAngle;
    private float mSecondProgressAngle;

    /*暴露属性*/
    private LShape         mShape; // bar形状
    private LCap           mLineCap; // 进度笔帽形状
    private LThumbPosition mThumbPosition; // 锚点偏移位置

    private boolean  mHideThumb; // 隐藏锚点
    private float    mProgress;
    private float    mSecondProgress;
    private float    mMaxProgress;
    private float    mMinProgress;
    private float    mLineWidth;
    private float    mThumbSize;
    private int      mThumb;
    private Drawable mThumDrawable;
    private int      mBaseLineColor;
    private int      mProgressColor;
    private int      mSecondProgressColor;

    private onLSeekBarStartListener     mStartListener; // 触摸开始监听
    private onLSeekBarStopListener      mStopListener; // 触摸结束监听
    private onLSeekBarChangeListener    mChangeListener; // 进度改变监听
    private OnLSeekBarScdChangeListener mScdChangeListener; // 二级进度条改变监听

    // 私有字段
    private float   mTempX;
    private boolean mThumbIsColor; // 锚点是否是颜色

    public interface onLSeekBarStartListener {
        void onLSeekBarStart(LCircleSeekBar view);
    }

    public interface onLSeekBarStopListener {
        void onLSeekBarStop(LCircleSeekBar view);
    }

    public interface onLSeekBarChangeListener {
        void onLSeekBarChange(LCircleSeekBar view, float progress);
    }

    public interface OnLSeekBarScdChangeListener {
        void onLSeekBarScdChange(LCircleSeekBar view, float secondProgress);
    }

    public LCircleSeekBar(Context context) {
        super(context);
        mContext = context;
    }

    public LCircleSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mMatrix = new Matrix();
        initAttrs(attrs);
    }

    public LCircleSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mMatrix = new Matrix();
        initAttrs(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //我们调用自己的自定义方法measureSize对宽高重新定义，
        // 参数宽和高是MeasureSpec对象
        mWidth = measureSize(widthMeasureSpec);
        mHeight = measureSize(heightMeasureSpec);
        mWidth = mHeight = mSize = Math.min(mWidth, mHeight);
        mCircleX = mSize / 2;
        mCircleY = mSize / 2;
        mCircleRadius = mSize / 2 - mThumbSize;
        mTempX = mSize / 2;
        mPointOfftX = mCircleX - mThumbSize / 2;
        refreshPointY(mThumbPosition); // 确定mPointOfftY的偏移量

        setMeasuredDimension(mWidth, mHeight);
    }

    /*判定View 宽高*/
    private int measureSize(int measureSpec) {
        //我们通过测量模式，给出不同的测量值
        //当specMode = EXACTLY时，直接指定specSize即可
        //当specMode != EXACTLY时，需要指出默认大小
        //当specMode = AT_MOST时，即指定了wrap_content属性时，需要取出我们指定大小和specSize中最小的一个为最后测量值
        //MeasureSpec对象有两个常用方法
        //MeasureSpec.getMode(measureSpec)  得到测量模式
        //MeasureSpec.getSize(measureSpec) 得到测量大小
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 180;   //指定默认大小
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
        //super.onDraw(canvas);
        switch (mShape) {
            case LSEEKBAR_SHAPE_RING:
                // 背景进度条
                canvas.drawArc(mArcRectF, START_ANGLE, CIRCLE_ANLE, false, mBasePaint);
                // 二级进度条
                canvas.drawArc(mArcRectF, START_ANGLE, mSecondProgressAngle, false, mScdProPaint);
                // 一级进度条
                canvas.drawArc(mArcRectF, START_ANGLE, mProgressAngle, false, mProPaint);
                break;
            case LSEEKBAR_SHAPE_CIRCLE:
                canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mBasePaint);
                canvas.drawArc(mArcRectF, START_ANGLE, mSecondProgressAngle, true, mScdProPaint);
                canvas.drawArc(mArcRectF, START_ANGLE, mProgressAngle, true, mProPaint);
                break;
            default:
        }

        if (!mHideThumb) {
            // 锚点
            mMatrix.setTranslate(mPointOfftX, mPointOfftY);
            mMatrix.postRotate(mProgressAngle, mCircleX, mCircleY);
            canvas.drawBitmap(mThumBbitmap, mMatrix, mScdProPaint);
        }

        this.invalidate(); //开启后实时更新
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mStartListener != null) {
                    mStartListener.onLSeekBarStart(this);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mStopListener != null) {
                    mStopListener.onLSeekBarStop(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 控制最大转一圈
                if (mTempX > mSize / 2 && event.getX() < mSize / 2 && y < mSize / 2) {
                    setProgress(0);
                    break;
                }
                if (mTempX < mSize / 2 && event.getX() > mSize / 2 && y < mSize / 2) {
                    setProgress(100);
                    break;
                }
                mTempX = event.getX();
                // 通过当前触摸点搞到cos角度值
                float cos = computeCos(mCircleX, mCircleY, x, y);
                // 通过反三角函数获得角度值
                double angle;
                if (x < mWidth / 2) {
                    // 滑动超过180度
                    angle = Math.PI * RADIAN + Math.acos(cos) * RADIAN;
                } else {
                    // 没有超过180度
                    angle = Math.PI * RADIAN - Math.acos(cos) * RADIAN;
                }
                setProgressAngle((float) angle + 1);
                setProgress(angleToProgress(getProgressAngle()));
                break;
            default:
        }
        return true;//super.onTouchEvent(event);
    }

    /*初始化配置*/
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LCircleSeekBar);
        switch (typedArray.getInteger(R.styleable.LCircleSeekBar_lcircleseekbar_shape, LCircleSeekBarConfig.LSEEKBAR_SHAPE_RING)) {
            case LCircleSeekBarConfig.LSEEKBAR_SHAPE_RING:
                mShape = LShape.LSEEKBAR_SHAPE_RING;
                break;
            case LCircleSeekBarConfig.LSEEKBAR_SHAPE_CIRCLE:
                mShape = LShape.LSEEKBAR_SHAPE_CIRCLE;
                break;
            default:
                mShape = LShape.LSEEKBAR_SHAPE_RING;
        }
        switch (typedArray.getInteger(R.styleable.LCircleSeekBar_lcircleseekbar_lineCap, LCircleSeekBarConfig.LSEEKBAR_LINE_CAP_SQUARE)) {
            case LCircleSeekBarConfig.LSEEKBAR_LINE_CAP_SQUARE:
                mLineCap = LCap.LSEEKBAR_LINE_CAP_SQUARE;
                break;
            case LCircleSeekBarConfig.LSEEKBAR_LINE_CAP_ROUND:
                mLineCap = LCap.LSEEKBAR_LINE_CAP_ROUND;
                break;
            default:
                mLineCap = LCap.LSEEKBAR_LINE_CAP_SQUARE;
        }
        switch (typedArray.getInteger(R.styleable.LCircleSeekBar_lcircleseekbar_thumbPosition, LCircleSeekBarConfig.LSEEKBAR_THUMB_POSITION_ABOVE)) {
            case LCircleSeekBarConfig.LSEEKBAR_THUMB_POSITION_ABOVE:
                mThumbPosition = LThumbPosition.LSEEKBAR_THUMB_POSITION_ABOVE;
                break;
            case LCircleSeekBarConfig.LSEEKBAR_THUMB_POSITION_MIDDLE:
                mThumbPosition = LThumbPosition.LSEEKBAR_THUMB_POSITION_MIDDLE;
                break;
            case LCircleSeekBarConfig.LSEEKBAR_THUMB_POSITION_BELOW:
                mThumbPosition = LThumbPosition.LSEEKBAR_THUMB_POSITION_BELOW;
                break;
            default:
                mThumbPosition = LThumbPosition.LSEEKBAR_THUMB_POSITION_ABOVE;
        }

        mHideThumb = typedArray.getBoolean(R.styleable.LCircleSeekBar_lcircleseekbar_hideThumb, false);
        mProgress = typedArray.getFloat(R.styleable.LCircleSeekBar_lcircleseekbar_progress, 0);
        mSecondProgress = typedArray.getFloat(R.styleable.LCircleSeekBar_lcircleseekbar_secondProgress, 0);
        mMaxProgress = typedArray.getFloat(R.styleable.LCircleSeekBar_lcircleseekbar_maxProgress, 100);
        mMinProgress = typedArray.getFloat(R.styleable.LCircleSeekBar_lcircleseekbar_minProgress, 0);

        mLineWidth = typedArray.getDimension(R.styleable.LCircleSeekBar_lcircleseekbar_lineWidth, LSizeX.dp2px(15));
        mThumbSize = typedArray.getDimension(R.styleable.LCircleSeekBar_lcircleseekbar_thumb_size, LSizeX.dp2px(15));

        mThumb = typedArray.getResourceId(R.styleable.LCircleSeekBar_lcircleseekbar_thumb, R.drawable.icon_lseekbar_point);
        mThumDrawable = typedArray.getDrawable(R.styleable.LCircleSeekBar_lcircleseekbar_thumb);
        mBaseLineColor = typedArray.getColor(R.styleable.LCircleSeekBar_lcircleseekbar_baseLineColor, ContextCompat.getColor(mContext, R.color.text_gray));
        mProgressColor = typedArray.getColor(R.styleable.LCircleSeekBar_lcircleseekbar_progressColor, ContextCompat.getColor(mContext, R.color.text_blue));
        mSecondProgressColor = typedArray.getColor(R.styleable.LCircleSeekBar_lcircleseekbar_secondProgressColor, ContextCompat.getColor(mContext, R.color.text_tag));

        typedArray.recycle();

        // thumb 是图片
        if (!judgeThumbStatus(mThumDrawable)) {
            mThumBbitmap = BitmapFactory.decodeResource(getResources(), mThumb);
            mThumBbitmap = reSizeBitmap(mThumBbitmap, mThumbSize, mThumbSize);
        } else {
            mThumBbitmap = Bitmap.createBitmap((int) mThumbSize, (int) mThumbSize, Bitmap.Config.ARGB_8888);
            if (mThumDrawable == null){
                mThumBbitmap.eraseColor(ContextCompat.getColor(mContext,R.color.white));
            }else {
                mThumBbitmap.eraseColor(((ColorDrawable) mThumDrawable).getColor());
            }
        }

        mThumBbitmap = LBitmapX.toRoundBitmap(mThumBbitmap); // 转化为圆形

        setProgressAngle(360 * mProgress / mMaxProgress);
        setSecondProgressAngle(360 * mSecondProgress / mMaxProgress);
    }


    /**
     * 判断thumb是颜色还是图片
     *
     * @param drawable thumb
     * @return true 颜色 false 图片
     */
    private boolean judgeThumbStatus(Drawable drawable) {
        if (drawable == null || drawable instanceof ColorDrawable) {
            mThumbIsColor = true;
        } else if (drawable instanceof BitmapDrawable) {
            mThumbIsColor = false;
        }
        return mThumbIsColor;
    }

    /*初始化画笔*/
    private void initPaint() {
        mBasePaint = createBasePaint();
        mProPaint = createProPaint();
        mScdProPaint = createScdProPaint();
        mArcRectF = new RectF(mThumbSize, mThumbSize, mWidth - mThumbSize, mHeight - mThumbSize);
        if (mLineCap == LCap.LSEEKBAR_LINE_CAP_ROUND) {
            mProPaint.setStrokeCap(Paint.Cap.ROUND);
            mScdProPaint.setStrokeCap(Paint.Cap.ROUND);
        }
        switch (mShape) {
            case LSEEKBAR_SHAPE_CIRCLE:
                mBasePaint.setStyle(Paint.Style.FILL);
                mProPaint.setStyle(Paint.Style.FILL);
                mScdProPaint.setStyle(Paint.Style.FILL);
                break;
            case LSEEKBAR_SHAPE_RING:
                mBasePaint.setStyle(Paint.Style.STROKE);
                mProPaint.setStyle(Paint.Style.STROKE);
                mScdProPaint.setStyle(Paint.Style.STROKE);
                break;
            default:
        }
    }


    /**
     * 重新确定锚点大小
     *
     * @param bm        原图
     * @param newWidth  新图片宽度sp
     * @param newHeight 新图片宽度
     * @return 新图片
     */
    public Bitmap reSizeBitmap(Bitmap bm, float newWidth, float newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 根据两点，得到与水平线夹角的cos值
     */
    public float computeCos(float cx, float cy, float rx, float ry) {
        float width = rx - cx;
        float height = ry - cy;
        float slope = (float) Math.sqrt(width * width + height * height);
        return height / slope;
    }


    /**
     * @param angle 角度
     * @return 返回角度对应的进度（百分比
     */
    public float angleToProgress(float angle) {
        return mMaxProgress * angle / CIRCLE_ANLE;
    }

    /**
     * @param position 确定mPointOfftY的偏移量
     */
    private void refreshPointY(LThumbPosition position) {
        switch (position) {
            case LSEEKBAR_THUMB_POSITION_ABOVE:
                mPointOfftY = 0;
                break;
            case LSEEKBAR_THUMB_POSITION_MIDDLE:
                mPointOfftY = mThumbSize / 2;
                break;
            case LSEEKBAR_THUMB_POSITION_BELOW:
                mPointOfftY = mThumbSize;
                break;
            default:
                mPointOfftY = 0;
        }
    }

    /**
     * @param progess 进度
     * @return 返回进度对应的角度
     */
    public float progressToAngle(float progess) {
        return CIRCLE_ANLE * progess / mMaxProgress;
    }

    public Paint createBasePaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //平滑
        paint.setColor(mBaseLineColor);
        paint.setStrokeWidth(mLineWidth);   //画笔宽度
        return paint;
    }

    public Paint createProPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);     //平滑
        paint.setColor(mProgressColor);
        paint.setStrokeWidth(mLineWidth);   //画笔宽度
        return paint;
    }

    public Paint createScdProPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //平滑
        paint.setColor(mSecondProgressColor);
        paint.setStrokeWidth(mLineWidth);   //画笔宽度
        return paint;
    }


    public onLSeekBarStartListener getStartListener() {
        return mStartListener;
    }

    public void setStartListener(onLSeekBarStartListener startListener) {
        mStartListener = startListener;
    }

    public onLSeekBarStopListener getStopListener() {
        return mStopListener;
    }

    public void setStopListener(onLSeekBarStopListener stopListener) {
        mStopListener = stopListener;
    }

    public onLSeekBarChangeListener getChangeListener() {
        return mChangeListener;
    }

    public void setChangeListener(onLSeekBarChangeListener changeListener) {
        mChangeListener = changeListener;
    }


    public float getProgressAngle() {
        return mProgressAngle;
    }

    private void setProgressAngle(float progressAngle) {
        mProgressAngle = progressAngle;
    }

    public float getSecondProgressAngle() {
        return mSecondProgressAngle;
    }

    private void setSecondProgressAngle(float secondProgressAngle) {
        mSecondProgressAngle = secondProgressAngle;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        progress = Math.min(progress, mMaxProgress);
        progress = Math.max(progress, mMinProgress);
        setProgressAngle(progressToAngle(progress));
        //setSecondProgress(progress * 2);
        mProgress = progress;
        if (mChangeListener != null) {
            mChangeListener.onLSeekBarChange(this, mProgress);
        }
    }

    public float getSecondProgress() {
        return mSecondProgress;
    }

    public void setSecondProgress(float secondProgress) {
        secondProgress = Math.min(secondProgress, mMaxProgress);
        secondProgress = Math.max(secondProgress, mMinProgress);
        setSecondProgressAngle(progressToAngle(secondProgress));
        mSecondProgress = secondProgress;
        if (mScdChangeListener != null) {
            mScdChangeListener.onLSeekBarScdChange(this, mSecondProgress);
        }
    }


    public LShape getShape() {
        return mShape;
    }

    public void setShape(LShape shape) {
        mShape = shape;
    }

    public LCap getLineCap() {
        return mLineCap;
    }

    public void setLineCap(LCap lineCap) {
        mLineCap = lineCap;
    }

    public LThumbPosition getThumbPosition() {
        return mThumbPosition;
    }

    public void setThumbPosition(LThumbPosition thumbPosition) {
        mThumbPosition = thumbPosition;
        refreshPointY(mThumbPosition);
    }

    public boolean isHideThumb() {
        return mHideThumb;
    }

    public void setHideThumb(boolean hideThumb) {
        mHideThumb = hideThumb;
    }

    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        mMaxProgress = maxProgress;
    }

    public float getMinProgress() {
        return mMinProgress;
    }

    public void setMinProgress(float minProgress) {
        mMinProgress = minProgress;
    }

    public float getThumbSize() {
        return mThumbSize;
    }

    public void setThumbSize(float thumbSize) {
        mThumbSize = thumbSize;
    }

    public int getThumb() {
        return mThumb;
    }

    public void setThumb(int thumb) {
        mThumb = thumb;
    }

    public int getBaseLineColor() {
        return mBaseLineColor;
    }

    public void setBaseLineColor(int baseLineColor) {
        mBaseLineColor = baseLineColor;
    }

    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    public int getSecondProgressColor() {
        return mSecondProgressColor;
    }

    public void setSecondProgressColor(int secondProgressColor) {
        mSecondProgressColor = secondProgressColor;
    }

    public OnLSeekBarScdChangeListener getScdChangeListener() {
        return mScdChangeListener;
    }

    public void setScdChangeListener(OnLSeekBarScdChangeListener scdChangeListener) {
        mScdChangeListener = scdChangeListener;
    }
}
