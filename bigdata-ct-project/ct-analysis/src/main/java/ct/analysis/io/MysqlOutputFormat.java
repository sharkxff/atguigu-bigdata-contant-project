package ct.analysis.io;

import ct.project.util.JDBCUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxfcode
 * @create 2018-11-07 18:43
 */
public class MysqlOutputFormat extends OutputFormat<Text,Text> {

    //模仿
    private FileOutputCommitter committer = null;
    static class MysqlRecordWriter extends RecordWriter<Text,Text>{

        private Connection connection ;
        private Map<String,Integer> contaceMap = new HashMap<String, Integer>();
        private Map<String,Integer> dateMap = new HashMap<String, Integer>();

        //准备连接
        public MysqlRecordWriter(){
            //
            connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = null;
            //查询联系人信息和日期信息
            String contactSql = "select id,telephone from t_contacts";
            String dateSql = "select id,year,month,day from t_date";
            ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement(contactSql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    String tel = resultSet.getString(2);
                    contaceMap.put(tel,id);
                }
                resultSet.close();
                preparedStatement.close();

                preparedStatement = connection.prepareStatement(dateSql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    String year = resultSet.getString(2);
                    String month = resultSet.getString(3);
                    String day = resultSet.getString(4);
                    if(month.length() ==1){
                        month = "0"+month;
                    }
                    if(day.length() ==1){
                        day = "0"+day;
                    }
                    dateMap.put(year+month+day,id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {

                    try {
                        if(resultSet != null){
                            resultSet.close();
                        }
                        if(preparedStatement != null){
                            preparedStatement.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

            }
        }
        //连接数据库

        //private PreparedStatement preparedStatement = null;
        public void write(Text key, Text value) throws IOException, InterruptedException {

            //向mysql里面添加数据
            String callSql = "insert into t_call (cid,dateid,sumcount,sumduration) values(?,?,?,?)";
            PreparedStatement preparedStatement = null;
            try {
                String[] keys = key.toString().split("_");
                String tel = keys[0];
                String date = keys[1];

                String[] values = value.toString().split("_");
                String sumCount = values[0];
                String sumDuration = values[1];

                Integer cid = contaceMap.get(tel);
                Integer dateid =contaceMap.get(date);
                if(dateid == null){
                    dateid = 10;
                }

                Integer sumcount = Integer.valueOf(sumCount);
                Integer sumduration = Integer.valueOf(sumDuration);

                preparedStatement = connection.prepareStatement(callSql);

                preparedStatement.setInt(1,cid);
                preparedStatement.setInt(2,dateid);
                preparedStatement.setInt(3,sumcount);
                preparedStatement.setInt(4,sumduration);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            //关闭资源
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //写出记录
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        MysqlRecordWriter recordWriter  = new MysqlRecordWriter();
        return recordWriter;
    }

    //可以什么都不做
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }

    //提交
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        //粘贴fileOutputFormat里面的
        if (committer == null) {
            Path output = getOutputPath(context);
            committer = new FileOutputCommitter(output, context);
        }
        return committer;
    }
    //粘贴
    public static Path getOutputPath(JobContext job) {
        String name = job.getConfiguration().get(FileOutputFormat.OUTDIR);
        return name == null ? null: new Path(name);
    }
}
