package com.liupeng;

import java.util.List;

import com.liupeng.dto.User;
import com.liupeng.util.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

/**
 * @author fengdao.lp
 * @date 2018/7/6
 */
public class MybatisTest {

    /**
     * mybatis一级缓存测试：一个SqlSession 关闭 看是否还能走一级缓存
     */
    @Test
    public void testCache1() {
        // 获取SqlSession，查DB
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        List<User> users = sqlSession.selectList("com.liupeng.dao.user.UserMapper.queryPerson");
        System.out.println(users); // 查DB -> 丢缓存

        // 一级缓存默认就会被使用
        users = sqlSession.selectList("com.liupeng.dao.user.UserMapper.queryPerson");
        System.out.println(users); // 直接从缓存查了

        // 关闭session
        sqlSession.close();

        // 重新获取sqlSession再查询DB
        sqlSession = MybatisUtil.getSqlSession();
        users = sqlSession.selectList("com.liupeng.dao.user.UserMapper.queryPerson");
        System.out.println(users); // 又重新从DB查了 -> 丢缓存

        // 增删改操作会重刷缓存
        sqlSession.update("com.liupeng.dao.user.UserMapper.update", new User());
        users = sqlSession.selectList("com.liupeng.dao.user.UserMapper.queryPerson");
        System.out.println(users); // 又重新从DB查了 -> 丢缓存
    }

    /**
     * mybatis二级缓存测试：两个SqlSession commit第一个 看是否走二级缓存
     */
    @Test
    public void testCache2() {
        // 开启两个不同的SqlSession
        SqlSessionFactory factory = MybatisUtil.getSqlSessionFactory();
        SqlSession sqlSession1 = factory.openSession();
        SqlSession sqlSession2 = factory.openSession();

        // 使用二级缓存时,User必须实现Serializable
        List<User> users = sqlSession1.selectList("com.liupeng.dao.user.UserMapper.queryPerson");
        sqlSession1.commit();
        System.out.println(users);

        // 1.user.xml中如果开启了二级缓存，则会缓存所有select，insert/update/delete会刷新缓存
        // 2.另外，因为不是同一个sqlSession，所以这里不会用到一级缓存
        // 3.如果开启了二级缓存，则从二级缓存中查找，查找不到则委托给SimpleExecutor查找。而他会从一级缓存查找，找不到再到DB查找。
        users = sqlSession2.selectList("com.liupeng.dao.user.UserMapper.queryPerson");
        System.out.println(users);
    }
}
