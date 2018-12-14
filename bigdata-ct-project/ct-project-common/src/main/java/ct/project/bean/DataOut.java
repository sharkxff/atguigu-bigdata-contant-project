package ct.project.bean;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-05 11:11
 */
//
public interface DataOut extends Closeable{
    //输出路径
    public void setPath(String path);
    //传对象
    public void write(Object obj) throws IOException;
    //串字符串
    public void write(String str) throws IOException;
}
