package ct.project.util;

import java.text.DecimalFormat;

/**
 * @author zxfcode
 * @create 2018-11-05 12:49
 */
public class NumberUtil {
    //将指定的数字按照指定的长度转换为字符串，长度不够的在前面补0
    public static String format(int num,int len){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<len;i++){
            stringBuilder.append("0");
        }
        DecimalFormat df = new DecimalFormat(stringBuilder.toString());
        return df.format(num);
    }

    public static void main(String[] args) {
        System.out.println(format(123, 6));
    }
}
