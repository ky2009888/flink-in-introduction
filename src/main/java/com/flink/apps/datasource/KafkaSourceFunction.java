package com.flink.apps.datasource;

import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author ky2009666
 * @Description 自定义flink的数据源.
 * @Date 2021/2/14
 **/
@AllArgsConstructor
public class KafkaSourceFunction implements SourceFunction<String> {
    /**
     * 是否执行的标记，默认是true,代表执行.
     */
    private boolean isRunning = true;

    @Override
    public void run(SourceContext ctx) throws Exception {
        while (isRunning) {
            ctx.collect(RandomUtil.randomNumbers(10));
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
