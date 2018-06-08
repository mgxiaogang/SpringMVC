package com.liupeng.spring.dynamicdatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 动态数据源选择
 *
 * @author fengdao.lp
 */
public class DynamicDataSourceHolder {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceHolder.class);

    /**
     * 初始化ThreadLocal,避免多线程操作数据源时互相干扰
     */
    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return super.initialValue();
        }
    };

    /**
     * 设置数据源
     *
     * @param dataSource 数据源名称
     */
    public static void setDataSource(String dataSource) {
        Assert.notNull(dataSource, "数据源未创建成功!");
        THREAD_DATA_SOURCE.set(dataSource);
    }

    /**
     * 获取数据源
     *
     * @return 数据源名称
     */
    public static String getDataSource() {
        if (null == THREAD_DATA_SOURCE.get()) {
            THREAD_DATA_SOURCE.set("myDataSource");
        }
        return THREAD_DATA_SOURCE.get();
    }

    /**
     * 清空
     */
    public static void clearDataSource() {
        THREAD_DATA_SOURCE.remove();
    }
}
