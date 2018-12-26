package com.liupeng.innerspring.annotation.demo1_annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 验证自定义注解的类 变为 Bean
 *
 * @author liupeng
 * @Date 2018/12/26
 */
@Configuration
public class ComponentAnnotationTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ComponentAnnotationTest.class);
        context.refresh();

        InjectClass injectClass = context.getBean(InjectClass.class);
        injectClass.print();
    }

    // @Component
    @MyComponent
    public static class InjectClass {
        void print() {
            System.out.println("hello world");
        }
    }
}
