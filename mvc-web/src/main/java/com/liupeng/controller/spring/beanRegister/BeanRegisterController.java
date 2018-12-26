package com.liupeng.controller.spring.beanRegister;

import com.liupeng.innerspring.annotation.demo2_scan.ScanClass1;
import com.liupeng.innerspring.annotation.demo2_scan.ScanUtils;
import com.liupeng.spring.beanRegister.SpringBeanServiceImpl;
import com.liupeng.spring.beanfactory.CacheBeansFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fengdao.lp
 * @date 2018/5/31
 */
@Controller
@RequestMapping("/beanRegister")
public class BeanRegisterController {
    @Autowired
    private SpringBeanServiceImpl springBeanService;

    @Autowired
    ScanClass1 scanClass1;

    @RequestMapping("test")
    public void test() {
        System.out.println(springBeanService);
        ScanUtils.scan();
    }
}
