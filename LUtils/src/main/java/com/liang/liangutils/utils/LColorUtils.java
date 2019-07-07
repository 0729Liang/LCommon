package com.liang.liangutils.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * @author : Amarao
 * CreateAt : 14:36 2019/3/18
 * Describe : 颜色工具类
 *
 * int getRandomColor() 随机得到一种颜色
 */
public class LColorUtils {

    /**
     * 随机得到一种颜色
     * <p>
     * 参考：https://blog.csdn.net/qq_21036939/article/details/51282778?locationNum=15&fps=1
     * 参考：https://blog.csdn.net/coder_nice/article/details/50679781
     *
     * @return
     */
    public static int getRandomColor() {
        Random random = new Random();
        int a = random.nextInt(256);
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.argb(a, r, g, b);
    }


    /**
     * 描述：内置颜色
     */
    public enum Colors {
        /**
         * 内置颜色
         */
        RED("#FF0500", 0xFF0500),
        ORANGE("#FFB601", 0xFFB601),
        YELLOW("#FFEF05", 0xFFEF05),
        GREEN("#08FF08", 0x08FF08),
        CYAN("#04FFAF", 0X04FFAF),
        BLUE("#00E3FF", 0X00E3FF),
        PURPLE("#FF00EF", 0XFF00EF),
        PINK("#FFB0E5", 0XFFB0E5),
        WHITE("#FFFFFF", 0XFFFFFF),
        BLACK("#000000", 0X000000);

        private String name;
        private int values;

        Colors(String name, int values) {
            this.name = name;
            this.values = values;
        }

        public String getName() {
            return name;
        }

        public int getValues() {
            return values;
        }

        /**
         * 解析颜色为整型值
         * <p>
         * 如'#345678'
         *
         * @return color
         */
        public int getColor() {
            try {
                return Color.parseColor(name);
            } catch (Exception e) {
                return Color.WHITE;
            }

        }

    }
}
