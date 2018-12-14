package com.atguigu.ct.producer.bean;


import ct.project.bean.DataIn;
import ct.project.bean.DataOut;
import ct.project.bean.Producer;
import ct.project.constant.Formats;
import ct.project.util.DateUtil;
import ct.project.util.NumberUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zxfcode
 * @create 2018-11-05 12:51
 */
//本地数据生产者
public class LocalFileDateProducer implements Producer {
    private DataIn in;
    private DataOut out;

    public void setIn(DataIn in) {
        this.in = in;
    }

    public void setOut(DataOut out) {
        this.out = out;
    }

    //生产数据
    public void produce(){

        try {
            //获取通讯录数据
            //Object readObj = in.read();
            List<Concate> conList = in.read(Concate.class);
            in.close();
            in = null;
            //确保电话不断在生成
            while (true){
                //System.out.println(list);
                //随机从通讯录中获取两个电话
                int call1Index = new Random().nextInt(conList.size());
                int call2Index = -1;
                //CAS算法，一样就放进去（死循环），这里是不一样才放进去
                //确保两个索引不一样（主被叫号不是一个号码）
                while (true){
                    call2Index = new Random().nextInt(conList.size());
                    if(call2Index != call1Index){
                        break;
                    }
                }
                Concate call1 = conList.get(call1Index);
                Concate call2 = conList.get(call2Index);

                //随机生成通话时间
                String startTimeString = "20180101000000";
                String endTimeString = "20190101000000";

                long startTime = DateUtil.parse(startTimeString, Formats.DATE_TIMESTAMP).getTime();
                long endTime = DateUtil.parse(endTimeString, Formats.DATE_TIMESTAMP).getTime();

                //获取当前通话时间  在我们指定的时间范围内
                //Math.random():[0~1)    [startTime~endTime)
                long currentTime = startTime + (long) ((endTime - startTime) * Math.random());

                //再变回时间格式  long---string
                String callTimeString = DateUtil.format(new Date(currentTime),Formats.DATE_TIMESTAMP);

                //随机生成通话时长
                int callDuration = new Random().nextInt(9999);
                String callDurationString = NumberUtil.format(callDuration, 4);

                //形成通话记录
                CallLog callLog = new CallLog(call1.getTel(), call2.getTel(), callTimeString, callDurationString);

                //将通话记录输出到指定文件中
                System.out.println("生成记录："+callLog);
                out.write(callLog);

                Thread.sleep(300);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.close();
            out= null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() throws IOException {
        if (in != null){
            in.close();
        }
        if (out != null){
            out.close();
        }
    }
}
