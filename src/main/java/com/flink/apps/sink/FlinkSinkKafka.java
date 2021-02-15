package com.flink.apps.sink;

import com.flink.apps.common.CommonEnviromentUtils;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

/**
 * @Author ky2009666
 * @Description 演示Flink的Sink操作之Kafka, 将flink处理完成之后的数据写入kafka.
 * @Date 2021/2/15
 **/
public class FlinkSinkKafka {
    /**
     * 命令行入口执行方法
     *
     * @param args 命令行参数.
     * @throws Exception 异常.
     */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.setParallelism(1);
        DataStreamSource<SenSorReadingV1> streamSource = CommonEnviromentUtils.optLoopCommonData(executionEnvironment, 100);
        //将结果写入kafka
        streamSource.map((MapFunction<SenSorReadingV1, String>) value -> value.toString())
                .addSink(new FlinkKafkaProducer011<String>("192.168.64.9:9092", "sink_kafka", new SimpleStringSchema()));
        executionEnvironment.execute();
    }
}
