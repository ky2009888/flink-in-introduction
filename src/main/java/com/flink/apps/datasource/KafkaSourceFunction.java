package com.flink.apps.datasource;

import lombok.AllArgsConstructor;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author ky2009666
 * @Description 自定义kafka的数据源
 * @Date 2021/2/14
 **/
@AllArgsConstructor
public class KafkaSourceFunction implements SourceFunction {

    @Override
    public void run(SourceContext ctx) throws Exception {

    }

    @Override
    public void cancel() {

    }
}
