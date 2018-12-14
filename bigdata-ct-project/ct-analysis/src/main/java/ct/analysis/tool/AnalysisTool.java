package ct.analysis.tool;

import ct.analysis.io.MysqlOutputFormat;
import ct.analysis.mapper.AnalysisTextMapper;
import ct.analysis.reducer.AnalysisTextReducer;
import ct.project.constant.Names;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;

/**
 * @author zxfcode
 * @create 2018-11-07 18:20
 */
public class AnalysisTool implements Tool {
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(AnalysisTool.class);

        //设置mapper
        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob(
                Names.TABLE_CALLLOG.value(),
                scan,
                AnalysisTextMapper.class,
                Text.class,
                Text.class,
                job
        );

        //设置reducer
        job.setReducerClass(AnalysisTextReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //设置outputformat
        job.setOutputFormatClass(MysqlOutputFormat.class);

        //提交结果
        return job.waitForCompletion(true)? JobStatus.State.SUCCEEDED.getValue() :
                JobStatus.State.FAILED.getValue();
    }

    public void setConf(Configuration conf) {

    }

    public Configuration getConf() {
        return null;
    }
}
