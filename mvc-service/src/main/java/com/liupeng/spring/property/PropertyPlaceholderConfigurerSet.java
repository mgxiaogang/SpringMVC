package com.liupeng.spring.property;

import java.io.IOException;
import java.util.Properties;

import com.liupeng.util.DESUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 配置文件占位符操作类,替换如下配置
 * <code>
 * <context:property-placeholder location="classpath:META-INF/wukong-dao.properties,xxx.properties/>
 * </code>
 *
 * @author fengdao.lp
 * @date 2018/6/4
 */
@Service
public class PropertyPlaceholderConfigurerSet extends PropertyPlaceholderConfigurer implements InitializingBean {

    /**
     * 参数占位符帮助类
     */
    private static PropertyPlaceholderHelper propertyPlaceholderHelper;

    /**
     * 配置属性
     */
    private static Properties properties;

    /**
     * 需要解密的字段
     */
    private String[] encryptPropNames = {"mysql.username", "mysql.password"};

    /**
     * 资源解析服务
     */
    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    // @formatter:off
    /**
     * bean初始化后设置配置文件路径,此处也可以用xml配置的方式
     *
     * <code>
     *     <bean id="propertyConfigurerTest" class="com.x.x.PropertyPlaceholderConfigurerSet" lazy-init="false">
     *         ﻿<property name="locations">
     *             <list>
     *                 <value>classpath*:*.properties</value>
     *                 <value>classpath*:config/test.properties</value>
     *                 <value>classpath*:config/${profile.path:dev}/*.properties</value>
     *             </list>
     *         </property>
     *     </bean>
     * </code>
     */
    // @formatter:on
    @Override
    public void afterPropertiesSet() throws Exception {
        // maven profile指定了具体环境对应的配置文件到classpath下
        String path1 = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "*.properties";
        String path2 = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "config/test.properties";
        String path3 = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "sharding/shardingjdbc/jdbc_dev.properties";
        Resource[] resources1 = resourcePatternResolver.getResources(path1);
        Resource[] resources2 = resourcePatternResolver.getResources(path2);
        Resource[] resources3 = resourcePatternResolver.getResources(path3);
        Resource[] resources = ArrayUtils.addAll(resources1, resources2);
        super.setLocations(ArrayUtils.addAll(resources, resources3));
        super.setFileEncoding("UTF-8");
    }

    /**
     * 解析配置文件,先执行,不推荐
     *
     * @param props 属性配置
     * @throws IOException 异常
     */
    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
    }

    /**
     * 解析配置文件,后执行,推荐
     *
     * @param beanFactoryToProcess beanFactoryToProcess
     * @param props                属性配置web-app
     * @throws BeansException 异常
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
        throws BeansException {
        propertyPlaceholderHelper = new PropertyPlaceholderHelper(
            DEFAULT_PLACEHOLDER_PREFIX,
            DEFAULT_PLACEHOLDER_SUFFIX,
            DEFAULT_VALUE_SEPARATOR,
            false);
        properties = props;
        super.processProperties(beanFactoryToProcess, props);
    }

    /**
     * 对属性进行处理:如果存在需要解密的字段，则返回DES解密后的属性值
     *
     * @param propertyName  属性key
     * @param propertyValue 属性value
     * @return 返回值
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (ArrayUtils.contains(encryptPropNames, propertyName)) {
            return DESUtil.getDecryptString(propertyValue);
        } else {
            return propertyValue;
        }
    }
}