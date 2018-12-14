package com.atguigu.ct.project.consumer;

import com.atguigu.ct.project.consumer.bean.CalllogConsumer;

/**
 * @author zxfcode
 * @create 2018-11-05 19:24
 */
//启动消费者
public class Bootstrap {
    public static void main(String[] args) {
        //创建消费者
        CalllogConsumer calllogConsumer = new CalllogConsumer();
        //消费数据
        calllogConsumer.consume();
    }
}
