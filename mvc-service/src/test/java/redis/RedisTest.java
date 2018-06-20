package redis;

import java.util.ResourceBundle;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author fengdao.lp
 * @date 2018/6/19
 */
public class RedisTest {

    private static JedisPool jedisPool;

    private static ShardedJedisPool shardedJedisPool;

    @Before
    public void before() {
        // 基础配置
        ResourceBundle bundle = ResourceBundle.getBundle("redis/redis");
        if (null == bundle) {
            throw new IllegalArgumentException("[redis.properties] is not found");
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(bundle.getString("redis.pool.maxIdle")));
        jedisPoolConfig.setMinIdle(Integer.parseInt(bundle.getString("redis.pool.minIdle")));
        jedisPoolConfig.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
        jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
        jedisPoolConfig.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));

        // 池化管理jedis
        jedisPool = new JedisPool(jedisPoolConfig, bundle.getString("redis.server"),
            Integer.parseInt(bundle.getString("redis.port")));

        // 集群一致性hash。多套主从
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(bundle.getString("redis.server1"),
            Integer.parseInt(bundle.getString("redis.port")));
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(bundle.getString("redis.server2"),
            Integer.parseInt(bundle.getString("redis.port")));
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, Lists.newArrayList(jedisShardInfo1, jedisShardInfo2));
    }

    /**
     * 简单使用jedis
     */
    @Test
    public void jedisTest() {
        String key = "username";
        // 创建jedis
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 判断key是否存在
        boolean flag = jedis.exists(key);
        System.out.println(flag);

        //设置key
        jedis.set(key, "liupeng");
        // 获取key
        System.out.println(jedis.get(key));

        // 删除key
        jedis.del(key);
        // 获取key
        System.out.println(jedis.get(key));
    }

    /**
     * 池化使用jedis
     */
    @Test
    public void jedisPoolTest() {
        String key = "username";
        // 从池中获取一个jedis对象
        Jedis jedis = jedisPool.getResource();

        // 判断key是否存在
        System.out.println(jedis.exists(key));

        // 存数据
        jedis.set(key, "liupeng");

        // 取数据
        System.out.println(jedis.get(key));

        // 删除key
        jedis.del(key);

        // 把对象释放回对象池
        jedisPool.returnResource(jedis);
    }

    /**
     * 集群一致性hash使用jedis
     */
    @Test
    public void shardedJedisPoolTest() {
        String key = "username";
        // 从集群池中获取一个jedis对象
        ShardedJedis shardedJedis = shardedJedisPool.getResource();

        // 判断key是否存在
        System.out.println(shardedJedis.exists(key));

        // 存数据
        shardedJedis.set(key, "liupeng");

        // 取数据
        System.out.println(shardedJedis.get(key));

        // 删除key
        shardedJedis.del(key);
    }
}
