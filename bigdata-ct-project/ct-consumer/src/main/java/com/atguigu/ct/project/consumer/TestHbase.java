package com.atguigu.ct.project.consumer;

import ct.project.constant.Names;
import ct.project.util.HbaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author zxfcode
 * @create 2018-11-07 14:06
 */
public class TestHbase {
    public static void main(String[] args) throws Exception{
        HbaseUtil.start();
        Table table = HbaseUtil.getTable(Names.TABLE_CALLLOG.value());

        // 4_17885275338_20180210123412_14397114174_6027_0
//        String call = "17885275338";
//        String start = "20180501";
//        String end = "20180801";
//
//        //根据条件查询hbase中的数据，可以采用过滤器的方式
//        Scan scan = new Scan();
//
//        String startRow = "0_"+call+"_"+start;
//        String endRow = "0_"+call+"_"+end;
//
////        //包含开始
//        scan.setStartRow(Bytes.toBytes("4_17885275338"));
////        //不包含结束
//        scan.setStopRow(Bytes.toBytes("4_17885275338"));


//        InclusiveStopFilter f = new InclusiveStopFilter(Bytes.toBytes("4_17885275338"));
//        scan.setFilter(f);


        //比较器和比较运算符
        //比较和电话号码相同的，比较某一列的值
//        Filter f1 = new SingleColumnValueFilter(Bytes.toBytes("info"),
//                Bytes.toBytes("call1"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes(call));
//        Filter f2 = new SingleColumnValueFilter(Bytes.toBytes("info"),
//                Bytes.toBytes("call2"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes(call));
//        Filter f3 = new SingleColumnValueFilter(Bytes.toBytes("info"),
//                Bytes.toBytes("callTime"), CompareFilter.CompareOp.GREATER_OR_EQUAL,Bytes.toBytes(start));
//        Filter f4 = new SingleColumnValueFilter(Bytes.toBytes("info"),
//                Bytes.toBytes("callTime"), CompareFilter.CompareOp.LESS_OR_EQUAL,Bytes.toBytes(end));
//
//        //FilterList将过滤条件看作一个整体
//        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
//        filterList.addFilter(f1);
//        filterList.addFilter(f2);
//
//        //在上面的基础上再做一个过滤操作
//        FilterList filterList2 = new FilterList(FilterList.Operator.MUST_PASS_ALL);
//        filterList.addFilter(filterList);
//        filterList.addFilter(f3);
//        filterList.addFilter(f4);
//        scan.setFilter(filterList2);

        //运行一下
//        ResultScanner scanner = table.getScanner(scan);
//        for (Result result : scanner) {
//            for (Cell cell : result.rawCells()) {
//                System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
//            }
//        }
//
//        HbaseUtil.end();



        String call = "17885275338";
        String start = "20180501";
        String end = "20180801";
        for (String[] strings : HbaseUtil.getStartStopRows(call, start, end)) {
            System.out.println(strings[0] + "~" + strings[1]);
        }
    }
}
