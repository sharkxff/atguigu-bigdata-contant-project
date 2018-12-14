package ct.project.bean;

import java.io.Closeable;

/**
 * @author zxfcode
 * @create 2018-11-05 11:11
 */
//生产者接口
public interface Producer  extends Closeable{
    public void produce();
    public void setIn(DataIn in);
    public void setOut(DataOut out);

}
