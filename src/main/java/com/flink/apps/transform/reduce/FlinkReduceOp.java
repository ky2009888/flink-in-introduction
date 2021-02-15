package com.flink.apps.transform.reduce;

import com.flink.apps.common.CommonEnviromentUtils;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author ky2009666
 * @Description 演示Flink如何进行reduce运算
 * @Date 2021/2/15
 **/
public class FlinkReduceOp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = CommonEnviromentUtils.getStreamExecutionEnvironment();
        DataStreamSource<SenSorReadingV1> dataStreamSource = CommonEnviromentUtils.optCommonData(streamExecutionEnvironment);
        dataStreamSource.keyBy(SenSorReadingV1::getId).reduce((ReduceFunction<SenSorReadingV1>) (value1, value2) -> value1.getTemperature() - value2.getTemperature() > 0 ? value1 : value2).setParallelism(1).print();
        streamExecutionEnvironment.execute();
    }
}
