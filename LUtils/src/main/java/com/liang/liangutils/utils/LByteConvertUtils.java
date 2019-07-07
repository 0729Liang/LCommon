package com.liang.liangutils.utils;

/**
 * @author : Amarao
 * CreateAt : 3:57 PM 2019/5/7
 * Describe : 字节转化工具
 *
 * <p>
 * Byte和int之间的转换
 * * byte[] int2byteArray(int num) 将32位的int值放到4字节里
 * * int byteArray2int(byte[] b) 将4字节的byte数组转成一个int值
 * <p>
 * short和byte的互转
 * * void putShort(byte b[], short s, int index) 转换short为byte
 * * short getShort(byte[] b, int index) 通过byte数组取到short
 * <p>
 * byte和char类型的转换
 * * void putChar(byte[] bb, char ch, int index) 字符到字节转换
 * * char getChar(byte[] b, int index) 字符到字节转换
 *
 * byte和float的转换
 * * void putFloat(byte[] bb, float x, int index) float转换byte
 * * float getFloat(byte[] b, int index) 通过byte数组取得float
 *
 * byte和double转换
 * * void putDouble(byte[] bb, double x, int index) double转换byte
 * * double getDouble(byte[] b, int index) 通过byte数组取得double
 */
public class LByteConvertUtils {

    /**************Byte和int之间的转换*******************/
    /**
     * 将32位的int值放到4字节里
     *
     * @param num
     * @return
     */
    public static byte[] int2byteArray(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >>> 24);//取最高8位放到0下标
        result[1] = (byte) (num >>> 16);//取次高8为放到1下标
        result[2] = (byte) (num >>> 8); //取次低8位放到2下标
        result[3] = (byte) (num);      //取最低8位放到3下标
        return result;
    }

    /**
     * 将4字节的byte数组转成一个int值
     *
     * @param b
     * @return
     */
    public static int byteArray2int(byte[] b) {
        byte[] a = new byte[4];
        int i = a.length - 1, j = b.length - 1;
        for (; i >= 0; i--, j--) {//从b的尾部(即int值的低位)开始copy数据
            if (j >= 0) {
                a[i] = b[j];
            } else {
                a[i] = 0;//如果b.length不足4,则将高位补0
            }
        }
        int v0 = (a[0] & 0xff) << 24;//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
        int v1 = (a[1] & 0xff) << 16;
        int v2 = (a[2] & 0xff) << 8;
        int v3 = (a[3] & 0xff);
        return v0 + v1 + v2 + v3;
    }

    /**************short和byte的互转*******************/
    /**
     * 转换short为byte
     *
     * @param b
     * @param s     需要转换的short
     * @param index
     */
    public static void putShort(byte[] b, short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index 第几位开始取
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    /**************byte和char类型的转换*******************/

    /**
     * 字符到字节转换
     *
     * @param ch
     * @return
     */
    public static void putChar(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            // 将最高位保存在最低位
            bb[index + i] = new Integer(temp & 0xff).byteValue();
            temp = temp >> 8; // 向右移8位
        }
    }

    /**
     * 字节到字符转换
     *
     * @param b
     * @return
     */
    public static char getChar(byte[] b, int index) {
        int s = 0;
        if (b[index + 1] > 0) {
            s += b[index + 1];
        } else {
            s += 256 + b[index + 0];
        }
        s *= 256;
        if (b[index + 0] > 0) {
            s += b[index + 1];
        } else {
            s += 256 + b[index + 0];
        }
        char ch = (char) s;
        return ch;
    }

    /**************byte和float的转换*******************/

    /**
     * float转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putFloat(byte[] bb, float x, int index) {
        // byte[] b = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得float
     *
     * @param b
     * @param index
     * @return
     */
    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**************byte和double转换*******************/
    /**
     * double转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putDouble(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Long(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得double
     *
     * @param b
     * @param index
     * @return
     */
    public static double getDouble(byte[] b, int index) {
        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffL;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffL;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffL;
        l |= ((long) b[6] << 48);
        l &= 0xffffffffffffffL;
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }
}

