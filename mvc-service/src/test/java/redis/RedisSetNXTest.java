package redis;

import java.util.concurrent.TimeUnit;

import com.liupeng.spring.redis.RedisSetNXManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author fengdao.lp
 * @date 2018/6/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring/spring-redis.xml"})
public class RedisSetNXTest {

    @Autowired
    private RedisSetNXManager redisSetNXManager;

    @Test
    public void testRedis() {

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task(Thread.currentThread().getName());
                }
            }).start();
        }

        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task(String name) {

        String locKey = "aa1a1234341";
        boolean ifLock = false;

        try {
            ifLock = redisSetNXManager.tryLock(locKey, 2000);
            if (ifLock) {
                //开始执行任务
                System.out.println(name + "任务执行中");
            }
        } finally {
            if (ifLock) {
                //任务执行完毕 关闭锁
                redisSetNXManager.unlock(locKey);
            }
        }
    }
}
