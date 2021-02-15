package com.flink.apps.sink;

import com.flink.apps.vo.SenSorReadingV1;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @Author ky2009666
 * @Description JDBC的slink
 * @Date 2021/2/15
 **/
public class MyJdbcSink extends RichSinkFunction<SenSorReadingV1> {
    /**
     * 声明连接和预编译语句Connection.
     */
    Connection connection = null;
    /**
     * 定义PreparedStatement.
     */
    PreparedStatement insertStmt = null;
    /**
     * 定义updateStmt.
     */
    PreparedStatement updateStmt = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:13306/cbc", "root", "jliang83");
        insertStmt = connection.prepareStatement("insert into sensor_temp (id, temp) values (?, ?)");
        updateStmt = connection.prepareStatement("update sensor_temp set temp = ? where id = ?");
    }

    /**
     * 每来一条数据，调用连接，执行sql
     *
     * @param value   实际入库的数据.
     * @param context 上下文.
     * @throws Exception 抛出异常.
     */
    @Override
    public void invoke(SenSorReadingV1 value, Context context) throws Exception {
        // 直接执行更新语句，如果没有更新那么就插入
        updateStmt.setInt(1, value.getTemperature());
        updateStmt.setString(2, value.getId());
        updateStmt.execute();
        if (updateStmt.getUpdateCount() == 0) {
            insertStmt.setString(1, value.getId());
            insertStmt.setInt(2, value.getTemperature());
            insertStmt.execute();
        }
    }

    @Override
    public void close() throws Exception {
        insertStmt.close();
        updateStmt.close();
        connection.close();
    }
}
