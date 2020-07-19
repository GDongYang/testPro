package com.fline.form.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @author panym
 * @date 2019年8月6日上午11:54:25
 * @Description:对象的深复制使用
 */
public class CloneUtil {
	
	
	/**
	 * @Description: 深复制列表对象
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> deepCopyList(List<T> src)
	{
	    List<T> dest = null;
	    try
	    {
	        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(byteOut);
	        out.writeObject(src);
	        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	        ObjectInputStream in = new ObjectInputStream(byteIn);
	        dest = (List<T>) in.readObject();
	    }
	    catch (IOException e)
	    {

	    }
	    catch (ClassNotFoundException e)
	    {

	    }
	    return dest;
	}
	/**
	 * @Description: 深复制单个对象 
	 * @return T
	 */
	public static <T extends Serializable> T clone(T obj){
        T cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();
            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }
}
