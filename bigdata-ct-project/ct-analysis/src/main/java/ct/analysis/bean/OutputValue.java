package ct.analysis.bean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-09 18:30
 */
public class OutputValue implements Writable {
    public void write(DataOutput out) throws IOException {
        out.writeUTF(sumcount);
        out.writeUTF(sumduration);
    }

    public void readFields(DataInput in) throws IOException {
        sumcount = in.readUTF();
        sumduration = in.readUTF();
    }
    private String sumcount;
    private String sumduration;

    public OutputValue() {
    }

    public OutputValue(String sumcount, String sumduration) {
        this.sumcount = sumcount;
        this.sumduration = sumduration;
    }

    public String getSumcount() {
        return sumcount;
    }

    public void setSumcount(String sumcount) {
        this.sumcount = sumcount;
    }

    public String getSumduration() {
        return sumduration;
    }

    public void setSumduration(String sumduration) {
        this.sumduration = sumduration;
    }
}
