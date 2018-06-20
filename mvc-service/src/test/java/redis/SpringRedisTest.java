package redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author fengdao.lp
 * @date 2018/6/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring/spring-redis.xml"})
public class SpringRedisTest {
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Test
    public void springRedisTest() {
        String key = "username";
        try {
            // 从池中获取jedis
            Jedis jedis = jedisPool.getResource();

            // 判断key是否存在
            System.out.println(jedis.exists(key));

            // 存数据
            jedis.set(key, "liupeng");

            // 取数据
            System.out.println(jedis.get(key));
        } catch (Exception e) {
            System.out.println("发生异常" + e);
        } finally {
            jedisPool.close();
        }
    }

    @Test
    public void shardedJedisPoolTest() {
        String key = "username";
        ShardedJedis shardedJedis = shardedJedisPool.getResource();

        // 判断key是否存在
        System.out.println(shardedJedis.exists(key));

        // 存数据
        shardedJedis.set(key, "liupeng");

        // 取数据
        System.out.println(shardedJedis.get(key));

        // 删除key
        shardedJedis.del(key);

        shardedJedis.close();
    }
}
