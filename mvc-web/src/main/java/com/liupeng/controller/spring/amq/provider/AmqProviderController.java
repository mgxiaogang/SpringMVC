package com.liupeng.controller.spring.amq.provider;

import com.liupeng.spring.amq.provider.AmqQueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fengdao.lp
 * @date 2018/6/22
 */
@Controller
@RequestMapping("/amq")
public class AmqProviderController {

    /*@Autowired
    private AmqQueueSender amqQueueSender;

    @RequestMapping(value = "/testQueue", method = RequestMethod.GET)
    public void testQueue() {
        amqQueueSender.sendQueue(1, "liupeng");
    }

    @RequestMapping(value = "/testTopic", method = RequestMethod.GET)
    public void testTopic() {
        amqQueueSender.sendTopic(1, "liupeng");
    }*/
}
