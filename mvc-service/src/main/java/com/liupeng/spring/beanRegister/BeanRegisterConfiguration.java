package com.liupeng.spring.beanRegister;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.*;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Bean注册器 - 注册springBeanServiceImpl为Bean
 *
 * @author fengdao.lp
 * @date 2018/5/31
 */
@Configuration
public class BeanRegisterConfiguration implements BeanDefinitionRegistryPostProcessor {
    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    /**
     * Bean被定义但还没被创建的时候执行
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registerBean(registry, "springBeanServiceImpl", SpringBeanServiceImpl.class, BeanRegisterAnnotation.class);
    }

    private void registerBean(BeanDefinitionRegistry registry,
                              String beanName,
                              Class<SpringBeanServiceImpl> beanCalss,
                              Class<? extends Annotation>... qualifiers) {
        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(beanCalss);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(annotatedGenericBeanDefinition);
        // singleton
        annotatedGenericBeanDefinition.setScope(scopeMetadata.getScopeName());
        // 可以自动生成name
        String name = (null != beanName ? beanName : this.beanNameGenerator.generateBeanName(
                annotatedGenericBeanDefinition, registry));
        AnnotationConfigUtils.processCommonDefinitionAnnotations(annotatedGenericBeanDefinition);
        // 注解的处理
        if (Objects.nonNull(qualifiers)) {
            for (Class<? extends Annotation> qualifier : qualifiers) {
                if (Primary.class == qualifier) {
                    annotatedGenericBeanDefinition.setPrimary(true);
                } else if (Lazy.class == qualifier) {
                    annotatedGenericBeanDefinition.setLazyInit(true);
                } else {
                    annotatedGenericBeanDefinition.addQualifier(new AutowireCandidateQualifier(qualifier));
                }
            }
        }

        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(annotatedGenericBeanDefinition, name);
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
    }

    /**
     * Bean被创建但还没被初始化的时候执行
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("start");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("springBeanServiceImpl");
        beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        System.out.println("修改后的scope为：" + beanDefinition.getScope());

        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        System.out.println("属性为：" + mutablePropertyValues);
        System.out.println("end");
    }
}
