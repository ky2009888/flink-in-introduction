package com.flink.apps.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author ky2009666
 * @Description 温度相关的数据收集的实体类$
 * @Date 2021-02-14$
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SenSorReadingV1 implements Serializable {
    /**
     * 主键ID.
     */
    private String id;
    /**
     * 时间戳.
     */
    private long timestamp;
    /**
     * 温度.
     */
    private Integer temperature;
}
