package ct.analysis;

import ct.analysis.tool.AnalysisBeanTool;
import ct.analysis.tool.AnalysisTool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author zxfcode
 * @create 2018-11-07 18:19
 */
public class AnalysisData {
    public static void main(String[] args) {
        try {
            //ToolRunner.run(new AnalysisTool(),args);
            ToolRunner.run(new AnalysisBeanTool(),args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
