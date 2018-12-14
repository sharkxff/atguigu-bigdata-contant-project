package com.atguigu.ct.producer.bean;

import ct.project.bean.Data;

/**
 * @author zxfcode
 * @create 2018-11-05 16:17
 */
public class Concate extends Data {
    private String tel;
    private String username;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setContent(String content) {
        String[] contents = content.split("\t");
        tel = contents[0];
        username = contents[1];
    }
}
