package com.liupeng.controller.spring.beanRegister;

import com.liupeng.spring.beanRegister.SpringBeanServiceImpl;
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

    @RequestMapping("test")
    public void test() {
        System.out.println(springBeanService);
    }
}
