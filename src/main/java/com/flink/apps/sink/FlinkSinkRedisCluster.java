package com.flink.apps.sink;

import com.flink.apps.common.CommonEnviromentUtils;
import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisClusterConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author ky2009666
 * @Description Flink将处理完成的数据写入Redis集群
 * @Date 2021/2/15
 **/
public class FlinkSinkRedisCluster {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = CommonEnviromentUtils.getStreamExecutionEnvironment();
        DataStreamSource<SenSorReadingV1> dataStreamSource = CommonEnviromentUtils.optLoopCommonData(streamExecutionEnvironment, 100);
        FlinkJedisClusterConfig.Builder builder = new FlinkJedisClusterConfig.Builder();
        Set<InetSocketAddress> nodes = new HashSet<>();
        nodes.add(new InetSocketAddress("192.168.64.7", 7001));
        nodes.add(new InetSocketAddress("192.168.64.7", 7002));
        nodes.add(new InetSocketAddress("192.168.64.7", 7003));
        nodes.add(new InetSocketAddress("192.168.64.7", 8001));
        nodes.add(new InetSocketAddress("192.168.64.7", 8002));
        nodes.add(new InetSocketAddress("192.168.64.7", 8003));
        FlinkJedisClusterConfig jedisClusterConfig = builder.setNodes(nodes).build();
        dataStreamSource.addSink(new RedisSink(jedisClusterConfig, new RedisMapper<SenSorReadingV1>() {
            @Override
            public RedisCommandDescription getCommandDescription() {
                return new RedisCommandDescription(RedisCommand.HSET, "FlinkSinkRedisCluster_" + LocalDate.now());
            }

            @Override
            public String getKeyFromData(SenSorReadingV1 senSorReadingV1) {
                return senSorReadingV1.getId();
            }

            @Override
            public String getValueFromData(SenSorReadingV1 senSorReadingV1) {
                return senSorReadingV1.getTemperature().toString();
            }
        }));
        streamExecutionEnvironment.execute();
    }
}
