package com.atguigu.bigdta.ct.bean;

/**
 * @author zxfcode
 * @create 2018-11-09 19:45
 */
public class Calllog {
    private Integer id;
    private Integer dateid;
    private int sumduration;
    private int sumcount;

    public Calllog() {
    }

    public Calllog(Integer id, Integer dateid, int sumduration, int sumcount) {
        this.id = id;
        this.dateid = dateid;
        this.sumduration = sumduration;
        this.sumcount = sumcount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDateid() {
        return dateid;
    }

    public void setDateid(Integer dateid) {
        this.dateid = dateid;
    }

    public int getSumduration() {
        return sumduration;
    }

    public void setSumduration(int sumduration) {
        this.sumduration = sumduration;
    }

    public int getSumcount() {
        return sumcount;
    }

    public void setSumcount(int sumcount) {
        this.sumcount = sumcount;
    }
}
