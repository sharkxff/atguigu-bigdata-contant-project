package com.atguigu.bigdta.ct.dao;

import com.atguigu.bigdta.ct.bean.Calllog;
import com.atguigu.bigdta.ct.bean.Contact;

import java.util.List;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-11-09 19:52
 */
public interface CalllogDao {
    Contact queryContactByTel(String tel);

    List<Calllog> queryCalllogByYear(Map<String,Object> paramap);
}
