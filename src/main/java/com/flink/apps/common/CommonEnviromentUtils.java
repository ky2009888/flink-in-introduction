package com.flink.apps.common;

import cn.hutool.core.util.RandomUtil;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Author ky2009666
 * @Description 创建通用的环境变量工具类
 * @Date 2021/2/15
 **/
public class CommonEnviromentUtils {
    /**
     * 定义私有函数变量.
     */
    private CommonEnviromentUtils() {
    }

    /**
     * 获取Flink的基础环境
     *
     * @return StreamExecutionEnvironment
     */
    public static StreamExecutionEnvironment getStreamExecutionEnvironment() {
        return StreamExecutionEnvironment.getExecutionEnvironment();
    }

    /**
     * 默认是使用192.168.64.9的kafka的环境变量.
     *
     * @return Properties.
     */
    public static Properties optKafkaProperties() {
        Properties kafkah = new Properties();
        kafkah.setProperty("bootstrap.servers", "192.168.64.9:9092");
        kafkah.setProperty("group.id", "consumer-group");
        kafkah.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkah.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkah.setProperty("auto.offset.reset", "latest");
        return kafkah;
    }

    /**
     * 获取定义好的数据.
     *
     * @param environment 环境变量.
     * @return DataStreamSource<SenSorReadingV1>.
     */
    public static DataStreamSource<SenSorReadingV1> optCommonData(StreamExecutionEnvironment environment) {
        if (environment == null) {
            return null;
        }
        return environment.fromCollection(
                Arrays.asList(
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100)),
                        new SenSorReadingV1(RandomUtil.randomNumbers(1), System.currentTimeMillis(), RandomUtil.randomInt(1, 100))
                ));
    }
}
