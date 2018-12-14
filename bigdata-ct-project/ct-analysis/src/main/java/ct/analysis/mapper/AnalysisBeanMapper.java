package ct.analysis.mapper;

import ct.analysis.bean.OutputKey;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-07 18:29
 */
public class AnalysisBeanMapper extends TableMapper<OutputKey,Text>{
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        String rowkey = Bytes.toString(key.get());
        // 4_17885275338_20180210123412_14397114174_6027_0
        String[] values = rowkey.split("_");
        String call1 = values[1];
        String call2 = values[3];

        String callTime = values[2];
        String year = callTime.substring(0,4);
        String yearMonth = callTime.substring(0,6);
        String date = callTime.substring(0,8);
        String duration = values[4];


        context.write(new OutputKey(call1,year),new Text(duration));
        context.write(new OutputKey(call1,yearMonth),new Text(duration));
        context.write(new OutputKey(call1,date),new Text(duration));
        context.write(new OutputKey(call2,year),new Text(duration));
        context.write(new OutputKey(call2,yearMonth),new Text(duration));
        context.write(new OutputKey(call2,date),new Text(duration));

    }
}
