package ct.project.util;

import ct.project.bean.Val;
import ct.project.constant.Formats;
import ct.project.constant.Names;
import ct.project.constant.Vals;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author zxfcode
 * @create 2018-11-06 10:48
 */
public class HbaseUtil {
    private static ThreadLocal<Connection> conHolder = new ThreadLocal<Connection>();
    private static ThreadLocal<Admin> adminHolder = new ThreadLocal<Admin>();
    public static void start() throws Exception {
        Connection connection = conHolder.get();
        if(connection == null){
            connection = ConnectionFactory.createConnection();
            conHolder.set(connection);
        }
        Admin admin = adminHolder.get();
        if(admin == null){
            admin = connection.getAdmin();
            adminHolder.set(admin);
        }
    }
    public static void end() throws Exception{
        Admin admin = adminHolder.get();
        if(admin != null){
            admin.close();
            adminHolder.remove();
        }
        Connection connection = conHolder.get();
        if(connection != null && !connection.isClosed()){
            connection.close();
            conHolder.remove();
        }
    }

    //创建命名空间
    public static void createNamespace(String namespace) throws IOException {
        Admin admin = adminHolder.get();
        NamespaceDescriptor namespaceDescriptor =
                NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(namespaceDescriptor);
    }

    //创建普通表
    public static void createTable(String tabname) throws IOException {
        createTable(tabname,0);
    }

    //创建分区表
    public static void createTable(String tabname,int regionCount) throws IOException {
        createTable(tabname,regionCount,null);
    }

    //创建分区表，协处理器
    public static void createTable(String tabname,int regionCount,String coprocesserClass) throws IOException {
        Admin admin = adminHolder.get();
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tabname));
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(Names.TABLE_FAMILY_INFO.value());
        tableDescriptor.addFamily(columnDescriptor);

        //增加协处理器列族
        HColumnDescriptor columnDescriptor2 = new HColumnDescriptor(Names.TABLE_FAMILY_UNACTIVE.value());
        tableDescriptor.addFamily(columnDescriptor2);
        if(coprocesserClass != null){
            tableDescriptor.addCoprocessor(coprocesserClass);
        }
        if(regionCount == 0){
            admin.createTable(tableDescriptor);
        }else{
            byte[][] splitkeys = getSplitkeys(regionCount);
            admin.createTable(tableDescriptor,splitkeys);
        }
    }

    //获取分区键
    public static byte[][] getSplitkeys(int regionCOunt){
        int splitKeyCount = regionCOunt - 1;
        List<byte[]> bytes = new ArrayList<byte[]>();
        for (int i = 0; i < splitKeyCount; i++) {
            //划分分区
            // [-∞ , 0|), [0|, 1|), [1|, +∞)
            String splitKey = i + "|";
            bytes.add(Bytes.toBytes(splitKey));
        }
        //给list排序
        Collections.sort(bytes,new Bytes.ByteArrayComparator());
        //分区数组长度已知
        byte[][] splitKeys = new byte[splitKeyCount][];
        //将list转成byte[2][]数组,此处以分为2三个分区为例，有两个分区字段
        //将0,1转换成byte[0][0]   byte[0][1]  byte[1][0]  byte[1][1]
        bytes.toArray(splitKeys);
        return splitKeys;
    }

    //创建命名空间，如果存在不做任何处理
    public static void createNamespaceNX(String namespace) throws IOException {
        Admin admin = adminHolder.get();
        try {
            admin.getNamespaceDescriptor(namespace);
        } catch (NamespaceNotFoundException e) {
            createNamespace(namespace);
        }
    }

    //创建表，如果存在就删除后创建
    public static void createTableNN(String tabName,int regionCount,String className) throws IOException {
        Admin admin = adminHolder.get();
        if(admin.tableExists(TableName.valueOf(tabName))){
            deleteTable(tabName);
        }
        createTable(tabName,regionCount,className);
    }

    //删除表
    private static void deleteTable(String tabName) throws IOException {
        Admin admin = adminHolder.get();
        TableName tn = TableName.valueOf(tabName);
        admin.disableTable(tn);
        admin.deleteTable(tn);
    }

    //获取表
    public static Table getTable(String tabname) throws Exception {
        Connection conn = conHolder.get();
        return conn.getTable(TableName.valueOf(tabname));
    }

    //获取rowkey分区号
    public static String getRegionNum(String call,String date){
        //20180101000000
        String yearMonth = date.substring(0, 6);

        //反转一下号码后四位作为唯一标识
        String usercode = call.substring(call.length()-4);
        //反转用户标识
        StringBuilder stringBuilder = new StringBuilder();
        usercode = stringBuilder.reverse().toString();

        //
        int callHashCode = usercode.hashCode();
        int yearMonthHashCode = date.hashCode();
        //异或算法
        int crc = Math.abs(callHashCode^yearMonthHashCode);

        int regionNum = crc % Vals.INT_6.intValue();

        return regionNum +"";
    }

    //获取查询rowkey范围集合数据,过滤查询
    public static List<String[]> getStartStopRows(String call,String startDate,String endDate){
        //201805~201808
        //201805~201805|  201806~201806| 201807~201807|

        List<String[]> rowList = new ArrayList<String[]>();
        try {
            //开始时间
            Calendar sd = Calendar.getInstance();
            sd.setTime(DateUtil.parse(startDate, Formats.DATE_YEARMONTH));

            //结束时间
            Calendar ed = Calendar.getInstance();
            ed.setTime(DateUtil.parse(endDate, Formats.DATE_YEARMONTH));

            while (sd.getTimeInMillis() <= ed.getTimeInMillis()){
                String regionNum = getRegionNum(call, startDate);
                String startRow = regionNum + "_" + call + "_" + DateUtil.format(sd.getTime(),Formats.DATE_YEARMONTH);
                String stopRow = startRow + "|";

                String[] rows = {startRow,stopRow};
                rowList.add(rows);

                sd.add(Calendar.MONTH,1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowList;
    }
}
