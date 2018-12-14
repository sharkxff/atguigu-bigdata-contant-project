package ct.analysis.tool;

import ct.analysis.bean.OutputKey;
import ct.analysis.bean.OutputValue;
import ct.analysis.io.MysqlBeanOutputFormat;
import ct.analysis.io.MysqlOutputFormat;
import ct.analysis.mapper.AnalysisBeanMapper;
import ct.analysis.mapper.AnalysisTextMapper;
import ct.analysis.reducer.AnalysisBeanReducer;
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
public class AnalysisBeanTool implements Tool {
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(AnalysisBeanTool.class);

        //设置mapper
        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob(
                Names.TABLE_CALLLOG.value(),
                scan,
                AnalysisBeanMapper.class,
                OutputKey.class,
                Text.class,
                job
        );

        //设置reducer
        job.setReducerClass(AnalysisBeanReducer.class);
        job.setOutputKeyClass(OutputKey.class);
        job.setOutputValueClass(OutputValue.class);

        //设置outputformat
        job.setOutputFormatClass(MysqlBeanOutputFormat.class);

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
