package com.atguigu.ct.producer.io;

import ct.project.bean.Data;
import ct.project.bean.DataIn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-11-05 15:46
 */
public class LocalFileDataIn implements DataIn {
    private BufferedReader reader = null;

    public LocalFileDataIn(String path){
        setPath(path);
    }
    public void setPath(String path) {
        try {
            //保证不出乱码
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object read() throws IOException {
        return null;
    }

    public <T extends Data> List<T> read(Class<T> clazz) throws IOException{
        List<T> list = new ArrayList<T>();
        try {
            String line = null;
            while ((line = reader.readLine()) != null){
                T t = clazz.newInstance();
                /* 调这个方法时看的是内存，调用者内存中有这个方法就走这个方法 */
                //Data的子类型都有这个方法setContent
                t.setContent(line);

                list.add(t);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void close() throws IOException {
        if (reader!=null){
            reader.close();
        }
    }
}
