package ct.analysis.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author zxfcode
 * @create 2018-11-09 10:54
 */
public class OutputKey implements WritableComparable<OutputKey> {

    public int compareTo(OutputKey o) {
        int result = tel.compareTo(o.tel);
        if(result == 0){
            result = date.compareTo(o.date);
            //return result;
        }
        return result;
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(tel);
        out.writeUTF(date);
    }

    public void readFields(DataInput in) throws IOException {
        tel = in.readUTF();
        date = in.readUTF();
    }

    private String tel;
    private String date;

    public OutputKey() {
    }

    public OutputKey(String tel, String duration) {
        this.tel = tel;
        this.date = duration;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String duration) {
        this.date = duration;
    }
}
