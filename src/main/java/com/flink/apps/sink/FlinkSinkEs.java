package com.flink.apps.sink;

import com.flink.apps.common.CommonEnviromentUtils;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ky2009666
 * @Description 演示Flink将处理完成的数据写入ES
 * @Date 2021/2/15
 **/
public class FlinkSinkEs {
    /**
     * 命令行主入口方法
     *
     * @param args 命令行参数.
     */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = CommonEnviromentUtils.getStreamExecutionEnvironment();
        DataStreamSource<SenSorReadingV1> senSorReadingV1DataStreamSource = CommonEnviromentUtils.optLoopCommonData(streamExecutionEnvironment, 100);
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("localhost", 9200));
        senSorReadingV1DataStreamSource.addSink(new ElasticsearchSink.Builder<>(httpHosts, new ElasticsearchSinkFunction<SenSorReadingV1>() {
            @Override
            public void process(SenSorReadingV1 senSorReadingV1, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
                Map<String, Object> dataSource = new HashMap<>();
                dataSource.put("id", senSorReadingV1.getId());
                dataSource.put("temperature", senSorReadingV1.getTemperature());
                dataSource.put("timestamp", senSorReadingV1.getTimestamp());
                IndexRequest indexRequest = new IndexRequest();
                indexRequest.index("sensorreadingv1").type("_doc").source(dataSource);
                requestIndexer.add(indexRequest);
            }
        }).build());
        streamExecutionEnvironment.setParallelism(1).execute();
        //查看索引
        //http://localhost:9200/_cat/indices
        //查看执行结果
        //http://localhost:9200/sensorreadingv1/_search?pretty
    }
}
