package com.flink.apps.main;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;


/**
 * @Author ky2009666
 * @Description 演示Flink从文件中读取内容
 * @Date 2021-02-14
 **/
public class ReadDataFromFile {
    /**
     * 执行入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) throws Exception {
        //1、创建环境变量
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、获取输入的文件流
        DataStreamSource<String> stringDataStreamSource = environment.
                readTextFile("F:\\workspace_contro_flow\\flink-in-introduction\\src\\main\\resources\\flink.txt", "UTF-8");
        //3、打印数据
        stringDataStreamSource.print();
        //4、执行
        environment.execute();
    }
}
