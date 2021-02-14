package com.flink.apps.main;

import com.flink.apps.datasource.KafkaSourceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

/**
 * @Author ky2009666
 * @Description 演示flink读取kafka数据的用法
 * @Date 2021-02-14
 **/
public class ReadDataFromKafka {
    public static void main(String[] args) throws Exception {
        //1、创建环境变量
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、添加kafka的数据源
        Properties kafkaProperties = new Properties();
        kafkaProperties.setProperty("bootstrap.servers", "192.168.64.9:9092");
        kafkaProperties.setProperty("group.id", "consumer-group");
        kafkaProperties.setProperty("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.setProperty("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.setProperty("auto.offset.reset", "latest");
        //这个SimpleStringSchema是flink的kafka连接包中自定义的
        DataStreamSource<String> kafkaDataSource = environment.addSource(
                new FlinkKafkaConsumer011<>("sensor", new SimpleStringSchema(), kafkaProperties));
        //3、打印kafka中的数据
        kafkaDataSource.setParallelism(1).print();
        //4、开启环境
        environment.execute();
    }
}
