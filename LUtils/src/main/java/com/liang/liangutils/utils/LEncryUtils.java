package com.liang.liangutils.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author : amarao
 * @date: 2019-07-22
 * @describer: LEncryUtils 加密工具类
 * <p>
 * 参考：
 * Python 和 Java 进行 DES 加密和解密：https://blog.csdn.net/lihao21/article/details/78557461
 * 轻松了解JAVA开发中的四种加密方法：https://blog.51cto.com/14226230/2422346
 * Class MessageDigest:https://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html?is-external=true
 * MessageDigest的功能及用法:https://www.cnblogs.com/honey01/p/6420111.html
 * Python 和 Java 进行 DES 加密和解密:https://blog.csdn.net/lihao21/article/details/78557461
 * Java - byte[] 和 String互相转换：https://www.cnblogs.com/keeplearnning/p/7003415.html
 * Java http数据MD5、AES、DES加密：https://www.cnblogs.com/chen-lhx/p/5817262.html
 * 备注：
 * 1、不可逆算法加密:md5,Hex,Sha等
 * 2、AES加密，此加密方受平台影响较重，所以只适合同类平台加密解密
 * 3、DES自定义加密，跨平台，兼容性好
 * 4. DES 为 Data Encryption Standard（数据加密标准），
 * DES 对称加密,采用 CBC 工作模式和 PKCS5Padding 的填充模式，使用的初始化向量是加密的密钥(des秘钥强制要求8位)
 * <p>
 * 方法说明
 * 1. md5 加密
 * encodeByMd5(File file) 根据文件内容加密得到32位md5码
 * encodeByMd5(File file, String secretKey) 指定加密的 key ,根据文件内容加密得到32位 md5 码
 * encodeByMd5(String content) 根据字符串生成32位md5码
 * encodeByMd5(String content, String secretKey) 指定加密的 key ,根据字符串生成32位m d5 码
 * 2. 自定义 加解密
 * encodeByOrdinary(String content) 自定义加密
 * decodeByOrdinary(String cipher) 自定义解密
 * 3. base64 加解密
 * encodeByBase64(String content) base64 加密
 * decodeByBase64(String cipher) base64 解密
 * 4. Des 加解密
 * decodeByDesCbc(byte[] content, byte[] keyBytes) 指定加密的 key,加密 byte[]
 * encodeByDesCbc(String content, String keyBytes) 指定加密的 key,加密 String
 * decodeByDesCbc(byte[] content, byte[] keyBytes) 指定加密的 key,解密 byte[]
 * decodeByDesCbc(String content, String keyBytes) 指定加密的 key,解密 String
 * 5. byte[] 与 16进制 String 转化
 * bytesToHexString(byte[] bytes) byte[] 转 String
 * hexStringToBytes(String hexString) String 转 byte[]
 */

public class LEncryUtils {

    private static final String DES = "DES";
    private static final String MD5 = "MD5";
    private static String PAD_PKCS5 = "DES/CBC/PKCS5Padding";
    //    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String UTF_8 = "UTF-8";//
    private static char[] defaultMd5SecretKey = "abcdef0123456789".toCharArray();//用于Md5默认产生密文的key
    private static byte[] defaultDesSecretKey = "12345678".getBytes();//用于Md5默认产生密文的key

    private static MessageDigest messagedigest = null;

    /**
     * 描述：得到Md5算法的MessageDigest对象。
     */
    private static MessageDigest getMd5Instance() {
        if (messagedigest == null) {
            try {
                messagedigest = MessageDigest.getInstance(MD5);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return messagedigest;
    }

    /**
     * MD5 根据文件内容加密得到32位md5码
     *
     * @param file 要加密的文件
     * @return MD5 字符串
     * <p>
     * update: 使用指定的字节更新摘要
     * digest: 通过执行填充等最终操作完成哈希计算。执行此调用后将重置摘要
     */
    public static String encodeByMd5(File file) throws IOException {
        MessageDigest messageDigest = getMd5Instance();
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead;
        while ((numRead = fis.read(buffer)) > 0) {
            messageDigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messageDigest.digest());
    }

    /**
     * MD5 根据文件内容加密得到32位md5码
     *
     * @param file      要加密的文件
     * @param secretKey 加密用的key，强制要求位16位字符串
     * @return 返回32位md5码
     * <p>
     * update: 使用指定的字节更新摘要
     * digest: 通过执行填充等最终操作完成哈希计算。执行此调用后将重置摘要
     */
    public static String encodeByMd5(File file, String secretKey) throws IOException {
        MessageDigest messageDigest = getMd5Instance();
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead;
        while ((numRead = fis.read(buffer)) > 0) {
            messageDigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messageDigest.digest(), secretKey);
    }

    /**
     * 描述：MD5 根据字符串生成32位md5码
     *
     * @param content 要加密的字符串
     * @return 返回32位md5码
     */
    public static String encodeByMd5(String content) throws UnsupportedEncodingException {
        MessageDigest messageDigest = getMd5Instance();
        return bufferToHex(messageDigest.digest(content.getBytes(UTF_8)));
    }

    /**
     * 描述：MD5 根据字符串生成32位md5码
     *
     * @param content   要加密的字符串
     * @param secretKey 加密用的key，强制要求位16位字符串
     * @return 返回32位md5码
     */
    public static String encodeByMd5(String content, String secretKey) throws UnsupportedEncodingException {
        MessageDigest messageDigest = getMd5Instance();
        return bufferToHex(messageDigest.digest(content.getBytes(UTF_8)), secretKey);
    }


    /**
     * 描述：自定义加密
     *
     * @param content 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encodeByOrdinary(String content) {

        char[] a = content.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    /**
     * 描述：自定义解密
     *
     * @param cipher 加密后的字符串
     * @return 解密后的字符串
     */
    public static String decodeByOrdinary(String cipher) {

        char[] a = cipher.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }


    /**
     * 描述：Base64 加密字符串
     *
     * @param content 要加密的字符串
     * @return 加密后的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encodeByBase64(String content) throws UnsupportedEncodingException {
        if (null == content) {
            return null;
        }
        return Base64.getEncoder().encodeToString(content.getBytes(UTF_8));
    }

    /**
     * 解密加密后的字符串
     *
     * @param cipher 加密后的字符串
     * @return 解密后的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decodeByBase64(String cipher) throws UnsupportedEncodingException {
        if (null == cipher) {
            return null;
        }
        return new String(Base64.getDecoder().decode(cipher.getBytes(UTF_8)), UTF_8);
    }


    /**
     * 描述：DES加密
     * 采用 CBC 模式，采用 PKCS5Padding 的填充模式
     *
     * @param content  要加密内容
     * @param keyBytes 加密的key，强制要求为8位字节数组
     * @return 加密后的字节数组，可调用 bytesToHexString 转化为字符查看
     */
    public static byte[] encodeByDesCbc(byte[] content, byte[] keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(PAD_PKCS5);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            return cipher.doFinal(content);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }

        return null;
    }

    /**
     * 描述：DES加密
     * 采用 CBC 模式，采用 PKCS5Padding 的填充模式
     *
     * @param content  要加密内容
     * @param keyBytes 加密的key，强制要求为8位字符串
     * @return 加密后的内容，可调用 hexStringToBytes 转化为字节数组
     */
    public static String encodeByDesCbc(String content, String keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(PAD_PKCS5);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            byte[] result = cipher.doFinal(content.getBytes());
            return bytesToHexString(result);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }

        return "";
    }

    /**
     * 描述：DES解密
     * 采用 CBC 模式，采用 PKCS5Padding 的填充模式
     *
     * @param content  加密后的内容
     * @param keyBytes 加密的key
     * @return 解密后的字节数组，可调用 bytesToHexString 转化为字符查看
     */
    public static byte[] decodeByDesCbc(byte[] content, byte[] keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(PAD_PKCS5);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return null;
    }

    /**
     * 描述：DES解密
     * 采用 CBC 模式，采用 PKCS5Padding 的填充模式
     *
     * @param content  加密后的内容
     * @param keyBytes 加密的key
     * @return 解密后的内容，可调用 hexStringToBytes 转化为字符查看
     */
    public static String decodeByDesCbc(String content, String keyBytes) {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(PAD_PKCS5);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes.getBytes()));
            byte[] result = cipher.doFinal(hexStringToBytes(content));
            return new String(result);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return "";
    }

    /**
     * 描述：将byte数组转化为16进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
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
        return hs.toString();
    }

    /**
     * 描述：十六进制字符串转化为byte数组
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
     * 描述：messagedigest完成哈希计算后,转化为16进制,采用默认的key加密
     */
    private static String bufferToHex(byte[] bytes) {
        StringBuilder stringbuffer = new StringBuilder(2 * bytes.length);
        int newLength = bytes.length;
        for (byte aByte : bytes) {
            // 取字节中高 4 位的数字转换, >> 为逻辑右移，将符号位一起右移
            char heigh = defaultMd5SecretKey[(aByte & 0xf0) >> 4];
            // 取字节中低 4 位的数字转换
            char low = defaultMd5SecretKey[aByte & 0x0f];
            stringbuffer.append(heigh);
            stringbuffer.append(low);
        }
        return stringbuffer.toString();
    }

    /**
     * 描述：messagedigest完成哈希计算后,转化为16进制,采用指定的key加密
     */
    private static String bufferToHex(byte[] bytes, String secretKey) {
        StringBuilder stringbuffer = new StringBuilder(2 * bytes.length);
        char[] keyBytes = secretKey.toCharArray();
        if (keyBytes.length != 16) {
            throw new IllegalArgumentException("secretKey length has to be 16");
        }
        for (byte aByte : bytes) {
            // 取字节中高 4 位的数字转换, >> 为逻辑右移，将符号位一起右移
            char heigh = keyBytes[(aByte & 0xf0) >> 4];
            // 取字节中低 4 位的数字转换
            char low = keyBytes[aByte & 0x0f];
            stringbuffer.append(heigh);
            stringbuffer.append(low);
        }
        return stringbuffer.toString();
    }

    /**
     * 描述：16进制char转化为int
     */
    private static int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 描述：判断是不是空格
     */
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

}
