package com.liupeng.webservice;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * @author fengdao.lp
 * @date 2018/7/19
 */
public class StudentServer {
    public static void main(String[] args) {
        //使用jaxrsServerFactoryBean发布rest服务
        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        //设置rest的服务地址
        jaxrsServerFactoryBean.setAddress("http://127.0.0.1:12345/rest");
        //设置服务对象
        jaxrsServerFactoryBean.setServiceBean(new StudentServiceImpl());
        //设置资源 对象，如果有多个pojo资源 对象中间以半角逗号隔开
        jaxrsServerFactoryBean.setResourceClasses(StudentServiceImpl.class);
        //发布rest服务
        jaxrsServerFactoryBean.create();
    }
}
