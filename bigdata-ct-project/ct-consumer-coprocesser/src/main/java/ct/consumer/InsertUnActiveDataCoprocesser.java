package ct.consumer;

import ct.project.constant.Names;
import ct.project.util.HbaseUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-06 20:45
 */
public class InsertUnActiveDataCoprocesser extends BaseRegionObserver {
    //插入一条数据后我们要做的操作
    //这里，插入一条主叫数据之后，由hbase自动插入一条被叫用户数据
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put,
                        WALEdit edit, Durability durability) throws IOException {

        String rk = Bytes.toString(put.getRow());
        String[] values = rk.split("_");

        System.out.println(values[1]);
        String call1 = values[1];
        String call2 = values[3];
        String callTime = values[2];
        String duration =values[4];
        String flg = values[5];

        if("1".equals(flg)){
            //这里第一个号码存被叫号码
            String rowkey = HbaseUtil.getRegionNum(call2,callTime)+"_"+call2+"_"+callTime+"_"+call1+"_"+duration+"_0";
            System.out.println(rowkey);

            Put unactivePut = new Put(Bytes.toBytes(rowkey));
            //列族：TABLE_FAMILY_UNACTIVE  列名：call1   列值：call2
            unactivePut.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_UNACTIVE.value()),Bytes.toBytes("call1"),Bytes.toBytes(call2));
            unactivePut.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_UNACTIVE.value()),Bytes.toBytes("callTime"),Bytes.toBytes(callTime));
            unactivePut.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_UNACTIVE.value()),Bytes.toBytes("call2"),Bytes.toBytes(call1));
            unactivePut.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_UNACTIVE.value()),Bytes.toBytes("duration"),Bytes.toBytes(duration));
            unactivePut.addColumn(Bytes.toBytes(Names.TABLE_FAMILY_UNACTIVE.value()),Bytes.toBytes("flg"),Bytes.toBytes("0"));

            try {
//                HbaseUtil.start();
//                Table table = HbaseUtil.getTable(Names.TABLE_CALLLOG.value());
//                table.put(unactivePut);
//                table.close();
//                HbaseUtil.end();
                Table table = e.getEnvironment().getTable(TableName.valueOf(Names.TABLE_CALLLOG.value()));
                table.put(unactivePut);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


    }
}
