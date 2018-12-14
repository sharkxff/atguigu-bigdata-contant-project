package ct.analysis.reducer;

import ct.analysis.bean.OutputKey;
import ct.analysis.bean.OutputValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-07 18:40
 */
public class AnalysisBeanReducer extends Reducer<OutputKey,Text,OutputKey,OutputValue> {
    @Override
    protected void reduce(OutputKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //统计通话总时长和通话次数
        int sumDuration = 0;
        int sumCount = 0;

        for (Text value : values) {
            sumDuration = sumDuration + Integer.valueOf(value.toString());
            sumCount++;
        }

        context.write(key,new OutputValue(""+sumCount,""+sumDuration));
    }
}
