package com.atguigu.ct.producer;

import com.atguigu.ct.producer.bean.LocalFileDateProducer;
import com.atguigu.ct.producer.io.LocalFileDataIn;
import com.atguigu.ct.producer.io.LocalFileDataOut;
import ct.project.bean.Producer;

/**
 * @author zxfcode
 * @create 2018-11-05 10:20
 */
//启动生产者
public class Bootstrap {
    public static void main(String[] args) throws Exception{
        //构建生产者对象
        Producer producer = new LocalFileDateProducer();
        //数据来源写成可配置的，扩展性强
        String in = "D:/zxf/FlumeAndKafka/13_尚硅谷大数据技术之电信客服/2.资料/辅助文档/contact.log";
        String out = "D:/zxf/FlumeAndKafka/13_尚硅谷大数据技术之电信客服/2.资料/辅助文档/call.log";
        /*if(args.length != 2){
            System.out.println("需要两个参数，请参考：java -jar xxxx.jar path1 path2");
            return;
        }*/
        //in = args[0];
        //out = args[1];
        producer.setIn(new LocalFileDataIn(in));
        producer.setOut(new LocalFileDataOut(out));
        //生产数据
        producer.produce();

        producer.close();
    }
}
