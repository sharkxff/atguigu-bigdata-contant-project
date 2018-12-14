package com.atguigu.ct.producer.io;

import ct.project.bean.DataOut;

import java.io.*;

/**
 * @author zxfcode
 * @create 2018-11-05 15:45
 */
public class LocalFileDataOut implements DataOut {
    private PrintWriter writer = null;
    public LocalFileDataOut(String path){
        setPath(path);
    }
    public void setPath(String path) {
        try {
            //保证不出乱码
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(Object obj) throws IOException {
        write(obj.toString());
    }

    public void write(String str) throws IOException {
        writer.println(str);
        writer.flush();
    }

    public void close() throws IOException {
        if (writer!=null){
            writer.close();
        }
    }
}
