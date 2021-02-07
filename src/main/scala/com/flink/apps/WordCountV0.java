package com.flink.apps;


import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

/**
 * @author Lenovo
 */
public class WordCountV0 {
    /**
     * 命令行入口方法
     *
     * @param args 命令行参数.
     */
    public static void main(String[] args) {
        //初始化执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        //从文件中读取数据
        String filePath = "F:\\workspace_contro_flow\\flink-in-introduction\\src\\main\\resources\\flink.txt";
        //获取DataSource
        DataSource<String> stringDataSource = environment.readTextFile(filePath);

    }
}
