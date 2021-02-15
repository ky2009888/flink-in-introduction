package com.flink.apps.transform.base;

import com.flink.apps.common.CommonEnviromentUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @Author ky2009666
 * @Description Flink的transform的基本运算
 * @Date 2021/2/15
 **/
public class FlinkTransFormBasicOp {
    public static void main(String[] args) throws Exception {
        //1、创建环境
        StreamExecutionEnvironment environment = CommonEnviromentUtils.getStreamExecutionEnvironment();
        //2、获取数据源,此处使用kafka数据源
        Properties kafkah = CommonEnviromentUtils.optKafkaProperties();
        DataStreamSource<String> streamSource = environment.addSource(new FlinkKafkaConsumer011<String>("sensor", new SimpleStringSchema(), kafkah));
        streamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                String[] numbers = value.split(" ");
                for (String num : numbers) {
                    out.collect(num);
                }
            }
        }).map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) throws Exception {
                if (StringUtils.isEmpty(value)) {
                    return 0;
                } else {
                    return Integer.parseInt(value);
                }
            }
        }).filter(value -> value > 10).setParallelism(1).print();
        environment.execute();
    }
}
