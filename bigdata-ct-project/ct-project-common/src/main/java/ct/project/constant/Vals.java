package ct.project.constant;


import ct.project.bean.Val;

/**
 * @author zxfcode
 * @create 2018-11-05 12:46
 */
public enum Vals implements Val {
    INT_0(0)
    ,INT_1(1)
    ,INT_6(6)
    ,String_0("0");

    private Object value;

    private Vals(Object obj){
        this.value = obj;
    }


    public Object value() {
        return value;
    }

    public int intValue(){return (Integer)value;}
}
