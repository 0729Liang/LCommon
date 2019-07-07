package com.liang.liangutils.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Amarao
 * CreateAt : 14:40 2019/3/16
 * Describe : list 工具类
 *
 * <T> List<T> deepCopyList(List<T> src) 深复制 List
 */
public class ListUtils {

    /**
     * 描述：list集合深拷贝
     * T 对象必须要实现序列化接口 Serializable
     */
    public static <T> List<T> deepCopyList(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

}
