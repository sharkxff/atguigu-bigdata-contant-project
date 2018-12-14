package ct.project.bean;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-11-05 10:25
 */
//数据来源
public interface DataIn extends Closeable{
    //输入路径
    public void setPath(String path);
    //读取
    public Object read() throws IOException;
    //扩展性，泛型约束
    public <T extends Data> List<T> read(Class<T> clazz) throws IOException;

}
