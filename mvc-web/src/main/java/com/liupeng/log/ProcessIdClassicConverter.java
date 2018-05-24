package com.liupeng.log;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 自定义 进程ID 获取
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class ProcessIdClassicConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        return name.substring(0, name.indexOf("@"));
    }
}
