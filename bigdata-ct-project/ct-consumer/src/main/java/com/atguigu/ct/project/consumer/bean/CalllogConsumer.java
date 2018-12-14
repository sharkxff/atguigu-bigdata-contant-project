package com.atguigu.ct.project.consumer.bean;


import com.atguigu.ct.project.consumer.dao.HbaseDao;
import ct.project.bean.Consumer;
import ct.project.constant.Names;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

/**
 * @author zxfcode
 * @create 2018-11-05 19:26
 */
//通话记录消费者
public class CalllogConsumer implements Consumer {
    public void consume() {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemResourceAsStream("consumer.properties"));
            //构建kafka的消费者
            KafkaConsumer consumer = new KafkaConsumer(properties);
            //订阅主题
            consumer.subscribe(Collections.singletonList(Names.KAFKA_TOPIC_CALLLOG.value()));
            //获取dao
            HbaseDao dao = new HbaseDao();
            //初始建命名空间和表，列族等
            dao.init();

            while (true){
                ConsumerRecords<String,String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.value());
                    //把数据放入指定的表
                    dao.puts(record.value());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
