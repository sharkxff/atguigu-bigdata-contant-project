package ct.analysis.reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-07 18:40
 */
public class AnalysisTextReducer extends Reducer<Text,Text,Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //统计通话总时长和通话次数
        int sumDuration = 0;
        int sumCount = 0;

        for (Text value : values) {
            sumDuration = Integer.valueOf(value.toString());
            sumCount++;
        }

        context.write(key,new Text(sumCount + "_" + sumDuration));
    }
}
