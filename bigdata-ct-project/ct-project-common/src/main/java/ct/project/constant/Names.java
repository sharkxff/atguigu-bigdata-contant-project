package ct.project.constant;


import ct.project.bean.Val;

/**
 * @author zxfcode
 * @create 2018-11-05 12:42
 */
//常量类，取值，
public enum Names implements Val {
    KAFKA_TOPIC_CALLLOG("calllog")
    ,TABLE_FAMILY_INFO("info")
    ,NAMESPACE_CT("ct")
    ,TABLE_CALLLOG("ct:calllog")
    ,TABLE_FAMILY_UNACTIVE("unactive")
    ;

    private String value;
    private Names(String name){
        this.value = name;
    }

    public String value() {
        return value;
    }
}
