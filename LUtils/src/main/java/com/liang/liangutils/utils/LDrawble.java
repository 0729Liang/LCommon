package com.liang.liangutils.utils;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.liang.liangutils.view.RoundedDrawable;

public class LDrawble {

    /**
     * 描述：合成两个图片
     */
    public Drawable combineTwoPictures(Drawable backgroud, Drawable foreground) {
        Drawable[] drawables = new Drawable[]{backgroud, foreground};
        return new LayerDrawable(drawables);
    }

    public Drawable combineTwoPictures2(Drawable backgroud, Drawable foreground) {

        Bitmap bgBitmap = ((BitmapDrawable) backgroud).getBitmap();
        Bitmap fgBitmap = ((BitmapDrawable) foreground).getBitmap();
        int width = bgBitmap.getWidth();
        int height = bgBitmap.getHeight();
        final Rect bg = new Rect((int) 0, (int) 0, (int) width, (int) height);
        final Rect fg = new Rect((int) 0, (int) 0, (int) width, (int) height);
        final RectF rectF = new RectF(bg);
        Canvas canvas = new Canvas(bgBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿
        canvas.drawRoundRect(rectF, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        canvas.drawBitmap(fgBitmap, bg, fg, paint);

        return new BitmapDrawable(bgBitmap);
    }


    /**
     * 着色 ----蒙层效果
     *
     * @param imageView
     */
    public void setTint(ImageView imageView, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//API21，即android5.0 以上
            imageView.setImageTintMode(PorterDuff.Mode.DARKEN);
            imageView.setImageTintList(ColorStateList.valueOf(color));
        } else {//v4包
            Drawable up = imageView.getDrawable();
            Drawable drawableUp = DrawableCompat.wrap(up);
            DrawableCompat.setTint(drawableUp, color);
            imageView.setImageDrawable(drawableUp);
        }
    }

    /**
     * 描述：根据drawable生成一个圆角drawble
     */
    public static RoundedDrawable createRoundedDrawable(Drawable drawable) {
        RoundedDrawable roundedDrawable = new RoundedDrawable(RoundedDrawable.drawableToBitmap(drawable));
        roundedDrawable.setCornerRadius(4);
        return roundedDrawable;
    }

}
