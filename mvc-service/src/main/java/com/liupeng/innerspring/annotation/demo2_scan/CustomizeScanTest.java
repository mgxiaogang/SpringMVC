package com.liupeng.innerspring.annotation.demo2_scan;

import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * test for customer
 * 1 BeanFactoryPostProcessor after bean factory is created,scan and modify bean definition
 * 2 BeanDefinition , bean class , if a basic class, auto ,else if a factory bean ,create by factory bean
 * 3 FactoryBean , create bean
 * 4 Scan ,basic scan
 *
 * @author liupeng
 */
@Configuration
public class CustomizeScanTest {
    /**
     * 用于嵌入到Spring的加载过程
     */
    @Component
    public static class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        /**
         * 扫描指定包路径下、指定注解的类，自动注册为代理后的Bean
         */
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
            scanner.setResourceLoader(this.applicationContext);
            scanner.scan("com.liupeng.innerspring.annotation.demo2_scan");
        }
    }

    /**
     * ClassPathBeanDefinitionScanner是Spring内置的Bean定义的扫描器
     */
    public final static class Scanner extends ClassPathBeanDefinitionScanner {
        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        @Override
        protected void registerDefaultFilters() {
            this.addIncludeFilter(new AnnotationTypeFilter(CustomizeComponent.class));
        }

        @Override
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            // 扫描包下注解的类、并自动注解为Bean
            Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
            // 对Bean做代理
            /*for (BeanDefinitionHolder holder : beanDefinitions) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
                definition.setBeanClass(FactoryBeanTest.class);
            }*/
            return beanDefinitions;
        }

        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata()
                    .hasAnnotation(CustomizeComponent.class.getName());
        }
    }

    public class FactoryBeanTest<T> implements FactoryBean<T> {

        private String innerClassName;

        public void setInnerClassName(String innerClassName) {
            this.innerClassName = innerClassName;
        }

        @Override
        public T getObject() throws Exception {
            Class innerClass = Class.forName(innerClassName);
            if (innerClass.isInterface()) {
                return (T) InterfaceProxy.newInstance(innerClass);
            } else {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(innerClass);
                enhancer.setNamingPolicy(new LiupengNamingPolicy());
                enhancer.setCallback(new MethodInterceptorImpl());
                return (T) enhancer.create();
            }
        }

        @Override
        public Class<?> getObjectType() {
            try {
                return Class.forName(innerClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

    }

    public static class InterfaceProxy implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("ObjectProxy execute:" + method.getName());
            return method.invoke(proxy, args);
        }

        public static <T> T newInstance(Class<T> innerInterface) {
            ClassLoader classLoader = innerInterface.getClassLoader();
            Class[] interfaces = new Class[]{innerInterface};
            InterfaceProxy proxy = new InterfaceProxy();
            return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
        }
    }

    public static class MethodInterceptorImpl implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("MethodInterceptorImpl:" + method.getName());
            return methodProxy.invokeSuper(o, objects);
        }
    }

    public static class LiupengNamingPolicy extends DefaultNamingPolicy {
        @Override
        protected String getTag() {
            return "byLiuPengCglib";
        }
    }
}
