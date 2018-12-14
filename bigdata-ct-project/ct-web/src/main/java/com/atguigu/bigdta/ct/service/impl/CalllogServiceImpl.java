package com.atguigu.bigdta.ct.service.impl;

import com.atguigu.bigdta.ct.bean.Calllog;
import com.atguigu.bigdta.ct.bean.Contact;
import com.atguigu.bigdta.ct.dao.CalllogDao;
import com.atguigu.bigdta.ct.service.CalllogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-11-09 19:44
 */
@Service
public class CalllogServiceImpl implements CalllogService{
    @Autowired
    private CalllogDao calllogDao;
    @Override
    public Contact queryContactByTel(String tel) {
        return calllogDao.queryContactByTel(tel);
    }

    @Override
    public List<Calllog> queryCalllogByYear(Map<String, Object> paramap) {
        return calllogDao.queryCalllogByYear(paramap);
    }
}
