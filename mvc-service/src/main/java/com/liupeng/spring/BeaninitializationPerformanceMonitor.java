package com.liupeng.spring;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static java.util.Collections.reverseOrder;

/**
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Service
public class BeaninitializationPerformanceMonitor implements BeanPostProcessor, ApplicationListener {

    public static Log log = LogFactory.getLog(BeaninitializationPerformanceMonitor.class);

    private Map<String, Long> startTime = newHashMapWithExpectedSize(1024);
    private List<Initialization> costTime = newArrayListWithCapacity(1024);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        startTime.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Long start = startTime.get(beanName);
        if (start != null) {
            costTime.add(Initialization.of(beanName, (System.currentTimeMillis() - start)));
            startTime.remove(beanName);
        }
        return bean;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            Collections.sort(costTime, reverseOrder());
            for (Initialization initialization : costTime) {
                log.info(initialization);
            }
            startTime.clear();
            costTime.clear();
        }
    }

    private static class Initialization implements Comparable<Initialization> {
        private String beanName;
        private long costTime;

        public static Initialization of(String beanName, long costTime) {
            Initialization initialization = new Initialization();
            initialization.costTime = costTime;
            initialization.beanName = beanName;
            return initialization;
        }

        @Override
        public String toString() {
            return "bean:" + beanName + ",cost " + costTime + " ms.";
        }

        @Override
        public int compareTo(Initialization o) {
            long res = costTime - o.costTime;
            return res == 0 ? 0 : (res > 0 ? 1 : -1);
        }
    }
}
