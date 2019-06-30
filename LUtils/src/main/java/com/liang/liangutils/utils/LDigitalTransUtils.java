package com.liang.liangutils.utils;

import org.jetbrains.annotations.NotNull;

/**
 * @author : Amarao
 * CreateAt : 15:35 2019/3/14
 * Describe : 字节转化
 * <p>
 * String stringToAsciiString(String content) 数字字符串转ASCII码字符串
 * String hexStringToString(String hexString, int encodeType) 十六进制字符串转字符串(Unicode，普通编码)
 * int hexStringToAlgorism(String hex) 十六进制字符串转十进制数值
 * String hexStringToBinary(String hex) 十六进制字符串转二进制字符串
 * String asciiStringToString(String content) ASCII码字符串转数字字符串
 * String algorismToHEXString(int algorism, int maxLength) 十进制转换为指定长度的十六进制字符串
 * String bytetoString(byte[] bytearray) 字节数组转为普通字符串
 * int binaryToAlgorism(String binary) 二进制字符串转十进制
 * String algorismToHEXString(int algorism) 十进制转换为十六进制字符串
 * int parseToInt(String s, int defaultInt, int radix) 将一个字符串转换为int
 * int parseToInt(String s, int defaultInt) 十进制形式的数字字符串转换为int
 * byte[] hexString2Bytes(String hex) 十六进制字符串转为Byte数组,每两个十六进制字符转为一个Byte
 * byte[] hexStringToBytes(String hexString) 十六进制字符串转化为byte数组
 * byte[] hexStringToBytes2(String hex) 十六进制字符串转化为byte数组
 * String bytesToHexString(byte[] bytes) 字节数组转换为十六进制字符串
 * String byteToHexString(byte b) 字节转换为十六进制字符串
 * byte[] int2LittleEndian(int i) 将int数据转化为byte数组（小端格式）
 * byte[] int2BigEndian(int i) 将int数据转化为byte数组（大端格式）
 * float bytesToFloatBig(byte[] array, int offset) 字节数组转化为float（大端格式）
 * byte[] floatToBytesBig(float value) float转化为字节数组（大端格式）
 * Float bytes2floatBig(@NotNull byte[] byteArray, int index) 字节数组转化为float（大端格式）
 * byte[] float2byte(float f) 浮点转换为字节数组
 */
public class LDigitalTransUtils {

    /**
     * 数字字符串转ASCII码字符串
     *
     * @param content 字符串
     * @return ASCII字符串
     */
    public static String stringToAsciiString(String content) {
        StringBuilder result = new StringBuilder();
        int max = content.length();
        for (int i = 0; i < max; i++) {
            char c = content.charAt(i);
            String b = Integer.toHexString(c);
            result.append(b);
        }
        return result.toString();
    }

    /**
     * 十六进制转字符串
     *
     * @param hexString  十六进制字符串
     * @param encodeType 编码类型-> 4：Unicode，2：普通编码
     * @return 字符串
     */
    public static String hexStringToString(String hexString, int encodeType) {
        StringBuilder result = new StringBuilder();
        int max = hexString.length() / encodeType;
        for (int i = 0; i < max; i++) {
            char c = (char) LDigitalTransUtils.hexStringToAlgorism(hexString
                    .substring(i * encodeType, (i + 1) * encodeType));
            result.append(c);
        }
        return result.toString();
    }

    /**
     * 十六进制字符串转十进制数值
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

    /**
     * 十六进制字符串转二进制字符串
     *
     * @param hex 十六进制字符串
     * @return 二进制字符串
     */
    public static String hexStringToBinary(String hex) {
        hex = hex.toUpperCase();
        StringBuilder result = new StringBuilder();
        int max = hex.length();
        for (int i = 0; i < max; i++) {
            char c = hex.charAt(i);
            switch (c) {
                case '0':
                    result.append("0000");
                    break;
                case '1':
                    result.append("0001");
                    break;
                case '2':
                    result.append("0010");
                    break;
                case '3':
                    result.append("0011");
                    break;
                case '4':
                    result.append("0100");
                    break;
                case '5':
                    result.append("0101");
                    break;
                case '6':
                    result.append("0110");
                    break;
                case '7':
                    result.append("0111");
                    break;
                case '8':
                    result.append("1000");
                    break;
                case '9':
                    result.append("1001");
                    break;
                case 'A':
                    result.append("1010");
                    break;
                case 'B':
                    result.append("1011");
                    break;
                case 'C':
                    result.append("1100");
                    break;
                case 'D':
                    result.append("1101");
                    break;
                case 'E':
                    result.append("1110");
                    break;
                case 'F':
                    result.append("1111");
                    break;
                default:
            }
        }
        return result.toString();
    }

    /**
     * ASCII码字符串转数字字符串
     *
     * @param content ASCII字符串
     * @return 字符串
     */
    public static String asciiStringToString(String content) {
        StringBuilder result = new StringBuilder();
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result.append(d);
        }
        return result.toString();
    }

    /**
     * 十进制转换为指定长度的十六进制字符串
     *
     * @param algorism  int 十进制数字
     * @param maxLength int 转换后的十六进制字符串长度
     * @return String 转换后的十六进制字符串
     */
    public static String algorismToHEXString(int algorism, int maxLength) {
        String result = "";
        result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;
        }
        return patchHexString(result.toUpperCase(), maxLength);
    }

    /**
     * 字节数组转为普通字符串（ASCII对应的字符）
     *
     * @param bytearray byte[]
     * @return String
     */
    public static String bytetoString(byte[] bytearray) {
        StringBuilder result = new StringBuilder();
        char temp;

        for (byte aBytearray : bytearray) {
            temp = (char) aBytearray;
            result.append(temp);
        }

        return result.toString();
    }

    /**
     * 二进制字符串转十进制
     *
     * @param binary 二进制字符串
     * @return 十进制数值
     */
    public static int binaryToAlgorism(String binary) {
        int max = binary.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = binary.charAt(i - 1);
            int algorism = c - '0';
            result += Math.pow(2, max - i) * algorism;
        }
        return result;
    }

    /**
     * 十进制转换为十六进制字符串
     *
     * @param algorism int 十进制的数字
     * @return String 对应的十六进制字符串
     */
    public static String algorismToHEXString(int algorism) {
        String result = "";
        result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;

        }
        result = result.toUpperCase();

        return result;
    }

    /**
     * HEX字符串前补0，主要用于长度位数不足。
     *
     * @param str       String 需要补充长度的十六进制字符串
     * @param maxLength int 补充后十六进制字符串的长度
     * @return 补充结果
     */
    private static String patchHexString(String str, int maxLength) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < maxLength - str.length(); i++) {
            temp.insert(0, "0");
        }
        str = (temp + str).substring(0, maxLength);
        return str;
    }

    /**
     * 将一个字符串转换为int
     *
     * @param s          String 要转换的字符串
     * @param defaultInt int 如果出现异常,默认返回的数字
     * @param radix      int 要转换的字符串是什么进制的,如16 8 10.
     * @return int 转换后的数字
     */
    public static int parseToInt(String s, int defaultInt, int radix) {
        int i = 0;
        try {
            i = Integer.parseInt(s, radix);
        } catch (NumberFormatException ex) {
            i = defaultInt;
        }
        return i;
    }

    /**
     * 十进制形式的数字字符串转换为int
     *
     * @param s          String 要转换的字符串
     * @param defaultInt int 如果出现异常,默认返回的数字
     * @return int 转换后的数字
     */
    public static int parseToInt(String s, int defaultInt) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = defaultInt;
        }
        return i;
    }

    /**
     * 十六进制字符串转为Byte数组,每两个十六进制字符转为一个Byte
     *
     * @param hex 十六进制字符串
     * @return byte 转换结果
     */
    private static byte[] hexString2Bytes(String hex) {
        int max = hex.length() / 2;
        byte[] bytes = new byte[max];
        String binarys = LDigitalTransUtils.hexStringToBinary(hex);
        for (int i = 0; i < max; i++) {
            bytes[i] = (byte) LDigitalTransUtils.binaryToAlgorism(binarys.substring(
                    i * 8 + 1, (i + 1) * 8));
            if (binarys.charAt(8 * i) == '1') {
                bytes[i] = (byte) (0 - bytes[i]);
            }
        }
        return bytes;
    }

    /**
     * 十六进制字符串转化为byte数组
     * <p>e.g. hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }</p>
     *
     * @param hexString The hex string.
     * @return the bytes
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (isSpace(hexString))
            return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * 十六进制字符串转化为byte数组
     *
     * @return the array of byte
     */
    public static byte[] hexStringToBytes2(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = Integer.valueOf(byteint).byteValue();
        }
        return b;
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param bytes byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (byte b : bytes) {
            stmp = Integer.toHexString(b & 0xff);
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 字节转换为十六进制字符串
     *
     * @param b byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    public static String byteToHexString(byte b) {
        StringBuilder hs = new StringBuilder();
        String s = Integer.toHexString(b & 0xff);
        if (s.length() == 1) {
            hs.append("0").append(s);
        } else {
            hs.append(s);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 功能：将int数据转化为byte数组（小端格式）
     * 十六进制： 0x11223344
     * 索引      |  0   | 1    | 2     |3
     * ---------|------|------|-------|-----
     * 大端格式：| 0x11 |  0x22 | 0x33 | 0x44
     * 小端格式：|0x44  |  0x33 | 0x22 |0x11
     */
    public static byte[] int2LittleEndian(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (0xff & i);
        b[1] = (byte) ((0xff00 & i) >> 8);
        b[2] = (byte) ((0xff0000 & i) >> 16);
        b[3] = (byte) ((0xff000000 & i) >> 24);
        return b;
    }

    /**
     * 功能：将int数据转化为byte数组（大端格式）
     * <p>
     * 十六进制： 0x11223344
     * 索引      |  0   | 1    | 2     |3
     * ---------|------|------|-------|-----
     * 大端格式：| 0x11 |  0x22 | 0x33 | 0x44
     * 小端格式：|0x44  |  0x33 | 0x22 |0x11
     */
    public static byte[] int2BigEndian(int i) {
        byte[] b = new byte[4];
        b[3] = (byte) (0xff & i);
        b[2] = (byte) ((0xff00 & i) >> 8);
        b[1] = (byte) ((0xff0000 & i) >> 16);
        b[0] = (byte) ((0xff000000 & i) >> 24);
        return b;
    }

    /***********************以下字节转化均为大端格式*****************************/

    /**
     * 描述：字节数组转化为float
     */
    public static float bytesToFloatBig(byte[] array, int offset) {
        int accum = 0;
        accum = array[offset + 0] & 0xFF;
        accum |= (long) (array[offset + 1] & 0xFF) << 8;
        accum |= (long) (array[offset + 2] & 0xFF) << 16;
        accum |= (long) (array[offset + 3] & 0xFF) << 24;
        return Float.intBitsToFloat(accum);
    }

    /**
     * 描述：float转化为字节数组
     */
    public static byte[] floatToBytesBig(float value) {
        int accum = Float.floatToRawIntBits(value);
        byte[] byteRet = new byte[4];
        byteRet[0] = (byte) (accum & 0xFF);
        byteRet[1] = (byte) ((accum >> 8) & 0xFF);
        byteRet[2] = (byte) ((accum >> 16) & 0xFF);
        byteRet[3] = (byte) ((accum >> 24) & 0xFF);
        return byteRet;
    }

    /**
     * 功能：字节数组转化为float（大端格式）
     * <p>
     * 首先要知道一个float需要4个字节
     * 当byte不足4位时，默认高位补0，测试index废弃
     */
    public static Float bytes2floatBig(@NotNull byte[] byteArray, int index) {

        if (byteArray.length < 4) {
            byte[] newByteArray = new byte[4];
            int length = byteArray.length;
            for (int i = 0; i < 4; i++) {
                if (i < 4 - length) {
                    newByteArray[i] = 0;//
                } else {
                    newByteArray[i] = byteArray[i + length - 4];
                }
            }
            return bytes2float(newByteArray, 0);
        }
        return bytes2float(byteArray, index);
    }

    /**
     * 浮点转换为字节数组
     *
     * @param f
     * @return
     */
    public static byte[] float2byte(float f) {

        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> (24 - i * 8));
        }

        // 翻转数组
        int len = b.length;
        // 建立一个与源数组元素类型相同的数组
        byte[] dest = new byte[len];
        // 为了防止修改源数组，将源数组拷贝一份副本
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        // 将顺位第i个与倒数第i个交换
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }

        return dest;

    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字节数组转换为浮点
     *
     * @param b     字节（至少4个字节）
     * @param index 开始位置
     * @return
     */
    private static float bytes2float(byte[] b, int index) {
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

}

