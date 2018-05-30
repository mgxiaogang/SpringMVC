package com.liupeng.spring.schedule;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/5/30
 */
@Service
//@EnableScheduling
public class ScheduleTest {

    @Scheduled(fixedDelay = 5000)
    public void test() {
        System.out.println("schedule");
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void test2() {
        System.out.println("schedule1");
    }
}
