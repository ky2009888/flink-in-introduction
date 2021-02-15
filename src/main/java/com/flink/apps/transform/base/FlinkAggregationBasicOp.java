package com.flink.apps.transform.base;

import com.flink.apps.common.CommonEnviromentUtils;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author ky2009666
 * @Description Flink聚合操作
 * @Date 2021/2/15
 **/
public class FlinkAggregationBasicOp {
    public static void main(String[] args) throws Exception {
        //1、创建环境变量
        StreamExecutionEnvironment environment = CommonEnviromentUtils.getStreamExecutionEnvironment();
        //2、获取自定义的数据
        DataStreamSource<SenSorReadingV1> commonData = CommonEnviromentUtils.optCommonData(environment);
        //3、根据ID获取最大值
        commonData.keyBy("id").maxBy("temperature").setParallelism(1).print();
        environment.execute();
    }
}
