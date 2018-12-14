package ct.project.util;

import ct.project.constant.Formats;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zxfcode
 * @create 2018-11-05 12:49
 */
public class DateUtil {
    //将指定的日期转换为字符串
    public static String format(Date d,Formats f){
        SimpleDateFormat sdf = new SimpleDateFormat(f.value());
        return sdf.format(d);
    }

    /*
    将字符串转换成指定格式的日期
     */
    public static Date parse(String d, Formats f) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(f.value());
        return sdf.parse(d);
    }
}
