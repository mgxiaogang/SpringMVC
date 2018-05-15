package com.liupeng.spring.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 *
 * @author fengdao.lp
 * @date 2018/5/15
 */
public class MyApplicationEvent extends ApplicationEvent {
    private String message;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyApplicationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
