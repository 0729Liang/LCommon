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

    /**
     * 将Bitmap转化为圆形
     * <p>
     * 来源：https://blog.csdn.net/Stanny_Bing/article/details/49449785
     * 参考：https://blog.csdn.net/stephenzml/article/details/74451483
     */
    public static class RoundBitmap {
        /**
         * 先绘制一个圆形，再绘制原图，之后通过设置图像合成模式，只显示相交的区域
         *
         * @param bitmap 原图
         * @param radius 半径
         * @return 将Bitmap转化为圆形
         */
        public static Bitmap toRoundBitmapByXfermode(Bitmap bitmap, int radius) {
            // 前面同上，绘制图像分别需要bitmap，canvas，paint对象
            bitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, true);
            Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);
            paint.reset();
            // 设置图像合成模式，只在源图像和目标图像相交的地方绘制源图像
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return output;
        }

        /**
         * 将原图设置为画笔渲染方式，之后绘制一个圆形
         *
         * @param bitmap 原图
         * @param radius 半径
         * @return 圆形bitmap
         */
        public static Bitmap toRoundBitmapByShaper(Bitmap bitmap, int radius) {
            bitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, true);
            Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // BitmapShader的作用是使用一张位图作为纹理来对某一区域进行填充。
            Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);// 设置画笔的渲染方式
            canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);
            return output;
        }

        /**
         * 将bitmap绘制为圆形，
         * 缺点：显示不全
         *
         * @param bitmap 原图
         * @return 圆形bitmap
         * 来源：https://blog.csdn.net/Stanny_Bing/article/details/49449785
         */
        public static Bitmap toRoundBitmapByXfermode(Bitmap bitmap) {
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
            final Paint paint = new Paint();
            final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
            final Rect dst = new Rect((int) dstLeft, (int) dstTop, (int) dstRight, (int) dstBottom);
            final RectF rectF = new RectF(dst);
            Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            paint.setAntiAlias(true); // 抗锯齿
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            // 图像合成模式:只在源图像和目标图像相交的地方绘制源图像
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, src, dst, paint);
            return output;
        }


    }
}
