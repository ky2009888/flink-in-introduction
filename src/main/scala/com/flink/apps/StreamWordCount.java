package com.flink.apps;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 用流的思想处理数据
 *
 * @author Lenovo
 */
public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        String filePath = "F:\\workspace_contro_flow\\flink-in-introduction\\src\\main\\resources\\flink.txt";
        DataStream<String> stringDataStreamSource = environment.readTextFile(filePath);
        DataStream<Tuple2<String, Integer>> sum = stringDataStreamSource.flatMap(new WordCountV0.MyFlatMap()).keyBy(0).sum(1);
        sum.print();
        //流式环境启动
        environment.execute();
    }
}
