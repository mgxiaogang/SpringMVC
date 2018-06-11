package com.liupeng.Dubbo.service;

import com.alibaba.dubbo.demo.DemoService;

/**
 * @author fengdao.lp
 * @date 2018/6/11
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello:" + name;
    }
}
