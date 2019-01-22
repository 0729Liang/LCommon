package com.liang.lcommon.view;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;

import com.liang.lcommon.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * CreateAt : 2019/1/4
 * Describe : 点击效果，按压图片变灰色
 *
 * @author chendong
 */
public class LDarkableRoundImageView extends RoundedImageView {


    public LDarkableRoundImageView(Context context) {
        super(context);
        setBackgroundResource(R.drawable.comm_img_press_bg);
    }

    public LDarkableRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.comm_img_press_bg);

    }

    public LDarkableRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.comm_img_press_bg);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        if (pressed) {
            setBrightness(-50);
        } else {
            setColorFilter(null);
        }
    }

    private void setBrightness(int brightness) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        setColorFilter(new ColorMatrixColorFilter(matrix));
    }
}
