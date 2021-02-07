package com.flink.apps;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 用流的思想处理数据
 *
 * @author Lenovo
 */
public class StreamWordCountV1 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        //从命令行参数获取配置参数
       /* String host = parameterTool.get("host", "localhost");
        int port = parameterTool.getInt("port");
        DataStream<String> stringDataStreamSource = environment.socketTextStream(host, port);*/
        DataStream<String> stringDataStreamSource = environment.socketTextStream("192.168.64.7", 7777);
        DataStream<Tuple2<String, Integer>> dataStream = stringDataStreamSource.flatMap(new WordCountV0.MyFlatMap()).keyBy(0).sum(1);
        dataStream.print();
        //流式环境启动
        environment.execute();
    }
}
