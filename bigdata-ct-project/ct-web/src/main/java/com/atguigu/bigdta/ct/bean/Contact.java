package com.atguigu.bigdta.ct.bean;

/**
 * @author zxfcode
 * @create 2018-11-09 19:45
 */
public class Contact {
   private String tel;
   private Integer id;
   private String name;

    public Contact() {
    }

    public Contact(String tel, Integer id, String name) {
        this.tel = tel;
        this.id = id;
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
