package com.liang.lcommon.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * @author : Amarao
 * CreateAt : 10:52 2019/1/11
 * Describe :
 */
public class LBitmapX {

    /**
     * @param bitmap
     * @return 将Bitmap转化为圆形
     * <p>
     * 来源：https://blog.csdn.net/Stanny_Bing/article/details/49449785
     * 参考：https://blog.csdn.net/stephenzml/article/details/74451483
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dstLeft, dstTop, dstRight, dstBottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            dstLeft = 0;
            dstTop = 0;
            dstRight = width;
            dstBottom = width;
            height = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            top = 0;
            right = width - clip;
            bottom = height;
            dstLeft = 0;
            dstTop = 0;
            dstRight = height;
            dstBottom = height;
            width = height;
        }
        //final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dstLeft, (int) dstTop, (int) dstRight, (int) dstBottom);
        final RectF rectF = new RectF(dst);
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        paint.setAntiAlias(true); // 抗锯齿
        //paint.setColor(color);
        //canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // 图像合成模式:只在源图像和目标图像相交的地方绘制源图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 重新确定bitmap大小
     *
     * @param bm        原图
     * @param newWidth  新图片宽度sp
     * @param newHeight 新图片宽度
     * @return 新图片
     */
    public static Bitmap resizeBitmap(Bitmap bm, float newWidth, float newHeight) {
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
}
