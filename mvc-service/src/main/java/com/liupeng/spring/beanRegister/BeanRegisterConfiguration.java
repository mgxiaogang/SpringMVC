package com.liupeng.spring.beanRegister;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/5/31
 */
@Configuration
public class BeanRegisterConfiguration implements BeanDefinitionRegistryPostProcessor {
    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registerBean(registry, "springBeanServiceImpl", SpringBeanServiceImpl.class);
    }

    private void registerBean(BeanDefinitionRegistry registry, String beanName,
                              Class<SpringBeanServiceImpl> beanCalss) {
        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(beanCalss);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(annotatedGenericBeanDefinition);
        // singleton
        annotatedGenericBeanDefinition.setScope(scopeMetadata.getScopeName());
        // 可以自动生成name
        String name = (null != beanName ? beanName : this.beanNameGenerator.generateBeanName(
            annotatedGenericBeanDefinition, registry));
        AnnotationConfigUtils.processCommonDefinitionAnnotations(annotatedGenericBeanDefinition);

        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(annotatedGenericBeanDefinition, name);
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
    }

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
