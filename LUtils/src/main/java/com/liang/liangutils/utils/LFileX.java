package com.liang.liangutils.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.Toast;

import com.liang.liangutils.init.LCommon;
import com.liang.liangutils.libs.utils.LLogX;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 文件相关操作
 */
public class LFileX {

    /**
     * 描述：向指定文件中写入一行内容
     *
     * @param pathFile   Environment.getExternalStorageDirectory() + "/Download/" + fileName
     * @param path       Environment.getExternalStorageDirectory() + "/Download/" + fileName
     * @param writeTytpe ture-append
     * @param line       写入一行内容
     */
    public static void writeLineToFile(File pathFile, String path, boolean writeTytpe, String line) {

        try {
            if (!pathFile.exists()) {
                pathFile.createNewFile();
            }

            try {
                FileOutputStream fos = new FileOutputStream(path, writeTytpe);
                fos.write(line.getBytes());
                fos.write("\n".getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件到
     *
     * @param srcFile         原文件
     * @param destFile        目标文件
     * @param isDeleteSrcFile 是否删除源文件
     * @return 复制成功
     */
    public static boolean copyTo(File srcFile, File destFile, boolean isDeleteSrcFile) {
        if (isNotExist(srcFile)) {
            return false;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            newDir(destFile.getParentFile().getAbsolutePath());
            if (!destFile.createNewFile()) {
                return false;
            }
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            int size;
            byte[] temp = new byte[1024 * 2];
            while ((size = bis.read(temp, 0, temp.length)) != -1) {
                bos.write(temp, 0, size);
            }
            bos.flush();
            if (isDeleteSrcFile) {
                delete(srcFile);
            }
            return true;
        } catch (IOException e) {
            LLogX.e(e);
        } finally {
            LRecycleX.recycle(bis, bos);
        }
        return true;
    }

    /**
     * 遍历删除文件
     *
     * @param delFile 要删除的文件／文件夹
     * @return 正确删除
     */
    public static boolean delete(File delFile) {
        if (LEmptyX.isEmpty(delFile)) {
            return false;
        }
        if (delFile.isFile()) {
            return delFile.delete();
        } else if (delFile.isDirectory()) {
            for (File file : delFile.listFiles()) {
                delete(file);
            }
        }
        return true;
    }

    /**
     * 创建文件夹
     *
     * @param path 路径
     * @return 文件夹
     */
    public static File newDir(String path) {
        File file = new File(path);
        boolean mkdirs = file.mkdirs();
        if (mkdirs) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * @param fileName 文件名
     * @return 在根目录下创建文件
     */
    public static File newRootFile(String fileName) {
        return new File(Environment.getExternalStorageDirectory(), fileName);
    }

    /**
     * @return sd卡是否可用
     */
    public static boolean isSDCardValid() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * @param filePath 路径
     * @return 文件不存在？
     */
    public static boolean isNotExist(String filePath) {
        return LEmptyX.isEmpty(filePath) || isNotExist(new File(filePath));
    }

    /**
     * @param file 文件
     * @return 文件不存在？
     */
    public static boolean isNotExist(File file) {
        return file == null || !file.exists() || file.length() == 0;
    }

    public static boolean isImageFile(String filePath) {
        return !LFileX.isNotExist(filePath) && (filePath.toLowerCase().endsWith("jpg") || filePath.toLowerCase().endsWith("png") || filePath.toLowerCase().endsWith("gif"));
    }


    /**
     * 文件后缀
     *
     * @param path 路径
     * @return 后缀名
     */
    public static String getSuffix(String path, String def) {
        if (!TextUtils.isEmpty(path)) {
            int lineIndex = path.lastIndexOf("/");
            if (lineIndex != -1) {
                String fileName = path.substring(lineIndex, path.length());
                if (!TextUtils.isEmpty(fileName)) {
                    int pointIndex = fileName.lastIndexOf(".");
                    if (pointIndex != -1) {
                        String suffix = fileName.substring(pointIndex, fileName.length());
                        if (!TextUtils.isEmpty(suffix)) {
                            return suffix;
                        }
                    }

                }
            }
        }
        return def;
    }

    /**
     * @return 获得SD卡总大小
     */
    public static String getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(LCommon.getApp(), blockSize * totalBlocks);
    }

    /**
     * @return 获得sd卡剩余容量，即可用大小
     */
    public static String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(LCommon.getApp(), blockSize * availableBlocks);
    }


}
