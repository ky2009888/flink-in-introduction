package com.flink.apps.sink;

import com.flink.apps.common.CommonEnviromentUtils;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Author ky2009666
 * @Description Flink处理完成的数据写入jdbc
 * @Date 2021/2/15
 **/
public class FlinkSinkJdbc {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = CommonEnviromentUtils.getStreamExecutionEnvironment();
        DataStreamSource<SenSorReadingV1> dataStreamSource = CommonEnviromentUtils.optLoopCommonData(streamExecutionEnvironment, 100);
        dataStreamSource.addSink(new MyJdbcSink());
        streamExecutionEnvironment.execute();
    }
}
