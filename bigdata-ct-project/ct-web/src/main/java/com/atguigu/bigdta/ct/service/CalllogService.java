package com.atguigu.bigdta.ct.service;

import com.atguigu.bigdta.ct.bean.Calllog;
import com.atguigu.bigdta.ct.bean.Contact;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-11-09 19:52
 */

public interface CalllogService {

    Contact queryContactByTel(String tel);

    List<Calllog> queryCalllogByYear(Map<String,Object> paramap);
}
