package com.flink.apps;


import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author Lenovo
 */
@Slf4j
public class WordCountV0 {
    /**
     * 命令行入口方法
     *
     * @param args 命令行参数.
     */
    public static void main(String[] args) throws Exception {
        //初始化执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        //从文件中读取数据
        String filePath = "F:\\workspace_contro_flow\\flink-in-introduction\\src\\main\\resources\\flink.txt";
        //获取dataSet,主要是针对离线数据
        DataSet<String> dataSet = environment.readTextFile(filePath);
        //获取元组中的位置的值
        AggregateOperator<Tuple2<String, Integer>> sum = dataSet.flatMap(new MyFlatMap()).groupBy(0).sum(1);
        log.info("--------------------------------------------------------start");
        sum.print();
        log.info("--------------------------------------------------------end");
    }

    /**
     * 自定义内部类.
     */
    public static class MyFlatMap implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String line, Collector<Tuple2<String, Integer>> collector) {
            String[] words = line.split(" ");
            for (String word : words) {
                collector.collect(new Tuple2<>(word, 1));
            }
        }
    }
}
