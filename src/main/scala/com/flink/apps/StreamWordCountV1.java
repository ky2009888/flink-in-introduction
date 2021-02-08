package com.flink.apps;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 用流的思想处理数据
 *
 * @author Lenovo
 */
public class StreamWordCountV1 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        //从命令行参数获取配置参数
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String host = parameterTool.get("host", "localhost");
        int port = parameterTool.getInt("port");
        DataStream<String> stringDataStreamSource = environment.socketTextStream(host, port);
        //DataStream<String> stringDataStreamSource = environment.socketTextStream("192.168.64.8", 7777);
        DataStream<Tuple2<String, Integer>> dataStream = stringDataStreamSource.
                flatMap(new WordCountV0.MyFlatMap()).
                //setParallelism 设置并行执行
                keyBy(0).sum(1).setParallelism(2);
        dataStream.print().setParallelism(1);
        //流式环境启动
        environment.execute();
    }
}
