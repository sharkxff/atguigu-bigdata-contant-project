package ct.project.constant;

import ct.project.bean.Val;

/**
 * @author zxfcode
 * @create 2018-11-05 16:46
 */
public enum Formats implements Val {
    DATE_TIMESTAMP("yyyyMMddHHmmss")
    ,DATE_YEARMONTH("yyyyMMdd")
    ;
    private String format;
    private Formats(String f){
        this.format = f;
    }
    public String value() {
        return format;
    }
}
