package com.liupeng.spring.IOC.full;

import com.liupeng.spring.IOC.single.MyInject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 自定义注解 单个依赖注入+全部依赖注入案例
 *
 * @author liupeng
 * @Date 2018/12/28
 */
@Configuration
public class FullInjectTest {

    @Component
    public static class FieldClass {
        public void print() {
            System.out.println("hello world");
        }
    }

    /**
     * 单个注入案例
     * 标注了@MyInject在属性上，会自动注入属性
     */
    @Component
    public static class BeanClass {
        @MyInject
        private FieldClass fieldClass;

        public void print() {
            fieldClass.print();
        }

    }

    public static class FullInjectSuperBeanClass {
        private FieldClass superFieldClass;

        public void superPrint() {
            superFieldClass.print();
        }
    }

    /**
     * 全部注入案例
     * 标注了@FullInject注解，里面属性会自动注入
     */
    @Component
    @FullInject
    public static class FullInjectBeanClass extends FullInjectSuperBeanClass {
        private FieldClass fieldClass;

        public void print() {
            fieldClass.print();
        }
    }

    @Component
    public static class MyInjectBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

        private ApplicationContext applicationContext;

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (hasAnnotation(bean.getClass().getAnnotations(), FullInject.class.getName())) {
                Class beanClass = bean.getClass();
                do {
                    Field[] fields = beanClass.getDeclaredFields();
                    for (Field field : fields) {
                        setField(bean, field);
                    }
                } while ((beanClass = beanClass.getSuperclass()) != null);
            } else {
                processMyInject(bean);
            }
            return bean;
        }

        private void processMyInject(Object bean) {
            Class beanClass = bean.getClass();
            do {
                Field[] fields = beanClass.getDeclaredFields();
                for (Field field : fields) {
                    if (!hasAnnotation(field.getAnnotations(), MyInject.class.getName())) {
                        continue;
                    }
                    setField(bean, field);
                }
            } while ((beanClass = beanClass.getSuperclass()) != null);
        }

        private void setField(Object bean, Field field) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                field.set(bean, applicationContext.getBean(field.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean hasAnnotation(Annotation[] annotations, String annotationName) {
            if (annotations == null) {
                return false;
            }
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getName().equals(annotationName)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(FullInjectTest.class);
        annotationConfigApplicationContext.refresh();
        BeanClass beanClass = annotationConfigApplicationContext.getBean(BeanClass.class);
        beanClass.print();
        FullInjectBeanClass fullInjectBeanClass = annotationConfigApplicationContext.getBean(FullInjectBeanClass.class);
        fullInjectBeanClass.print();
        fullInjectBeanClass.superPrint();
    }
}
