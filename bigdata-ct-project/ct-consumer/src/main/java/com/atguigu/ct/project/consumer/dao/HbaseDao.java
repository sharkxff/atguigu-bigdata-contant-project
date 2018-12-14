package com.atguigu.ct.project.consumer.dao;

import ct.project.constant.Names;
import ct.project.constant.Vals;
import ct.project.util.HbaseUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-11-06 10:48
 */
public class HbaseDao {
    private List<Put> putList = new ArrayList<Put>();
    private int batchSize = 20;
    public void init(){
        try {
            HbaseUtil.start();
            HbaseUtil.createNamespaceNX(Names.NAMESPACE_CT.value());
            HbaseUtil.createTableNN(Names.TABLE_CALLLOG.value(), Vals.INT_6.intValue(),
                    "ct.consumer.InsertUnActiveDataCoprocesser");
            HbaseUtil.end();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //将数据放入指定位置
    public void puts(String value) {
        try {
            //获得每一个字段
            System.out.println("-----------puts");
            String[] values = value.split("\t");
            String call1 = values[0];
            String call2 = values[1];
            String callTime = values[2];
            String duration = values[3];

            //预分区，rowkey设计，分区号
            String regionNum = HbaseUtil.getRegionNum(call1, callTime);
            String rowkey = regionNum + "_" + call1 + "_" + callTime + "_" + call2 + "_" + duration+"_1";
            Put put = new Put(Bytes.toBytes(rowkey));
            //添加列数据
            put.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_INFO.value()),Bytes.toBytes("call1"),Bytes.toBytes(call1));
            put.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_INFO.value()),Bytes.toBytes("callTime"),Bytes.toBytes(callTime));
            put.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_INFO.value()),Bytes.toBytes("call2"),Bytes.toBytes(call2));
            put.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_INFO.value()),Bytes.toBytes("duration"),Bytes.toBytes(duration));
            put.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_INFO.value()),Bytes.toBytes("flg"),Bytes.toBytes("1"));

            //将数据添加进list列表
            putList.add(put);

            //批量存入数据
            if(putList.size()>=batchSize){
                HbaseUtil.start();
                //获取表名
                Table table = HbaseUtil.getTable(Names.TABLE_CALLLOG.value());
                //把数据放进表里
                table.put(putList);
                table.close();
                HbaseUtil.end();
                //保证下一批数据从头开始
                putList.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
