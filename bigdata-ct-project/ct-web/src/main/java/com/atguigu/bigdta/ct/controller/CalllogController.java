package com.atguigu.bigdta.ct.controller;

import com.atguigu.bigdta.ct.bean.Calllog;
import com.atguigu.bigdta.ct.bean.Contact;
import com.atguigu.bigdta.ct.service.CalllogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-11-09 19:45
 */
@Controller
public class CalllogController {

    @Autowired
    private CalllogService callService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/data")
    public Object data(){
        Map map = new HashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        return map;
    }

    @RequestMapping("/queryData")
    public String queryData(String tel, String year, Model model){
        //根据电话号码查询联系人
        Contact contact = callService.queryContactByTel(tel);

        Map<String, Object> paramap = new HashMap<String, Object>();
        paramap.put("cid",contact.getId());
        paramap.put("year",year);

        List<Calllog> calllogs = callService.queryCalllogByYear(paramap);
        //保存联系人信息
        model.addAttribute("contact",contact);
        model.addAttribute("calllogs",calllogs);

        return "result";
    }
}
