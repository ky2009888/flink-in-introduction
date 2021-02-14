package com.flink.apps.main;

import cn.hutool.core.util.RandomUtil;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @Author ky2009666
 * @Description 演示Flink从集合中读取数据的案例
 * @Date 2021-02-14
 **/
public class ReadDataFromCollection {
    public static void main(String[] args) throws Exception {
        //1、创建环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、输入数据
        DataStreamSource<SenSorReadingV1> dataStreamSource = environment.fromCollection(
                Arrays.asList(
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(3), System.currentTimeMillis(), RandomUtil.randomDouble(0.0, 40.0))
                ));
        //3、打印数据
        dataStreamSource.print();
        //4、开启环境执行
        environment.execute();
    }
}
