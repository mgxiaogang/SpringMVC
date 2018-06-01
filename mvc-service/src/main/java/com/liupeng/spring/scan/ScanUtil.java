package com.liupeng.spring.scan;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

/**
 * 在多个包下查找指定注解的类
 *
 * @author fengdao.lp
 * @date 2018/6/1
 */
public class ScanUtil {
    public static Set<Class<?>> scanPackages(String[] packages, Class<? extends Annotation>... clazz) {
        LoadPackage loadPackage = new LoadPackage(packages, clazz);
        try {
            return loadPackage.getClassSet();
        } catch (Exception e) {
            System.out.println("error");
        }
        return null;
    }

    public static class LoadPackage {
        /**
         * 需要被扫描的包
         */
        private List<String> packageList = Lists.newArrayList();

        /**
         * 需要扫描的类型
         */
        private List<TypeFilter> typeFilters = Lists.newArrayList();

        /**
         * 结果类集合
         */
        private Set<Class<?>> set = Sets.newHashSet();

        private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        /**
         * 构造函数 指定多个需要被扫描的包、指定扫描包中含有特定注解的Bean
         *
         * @param packages 包
         * @param clazz    注解class
         */
        public LoadPackage(String[] packages, Class<? extends Annotation>... clazz) {
            if (null != packages) {
                packageList = Lists.newArrayList(packages);
            }
            if (null != clazz) {
                for (Class<? extends Annotation> annotation : clazz) {
                    //忽略元注解
                    AnnotationTypeFilter af = new AnnotationTypeFilter(annotation, false);
                    typeFilters.add(af);
                }

            }
        }

        /**
         * 检查当前扫描到的Bean含有任何一个指定的注解标记,符合条件的Bean以Class形式返回
         */
        public Set<Class<?>> getClassSet() throws Exception {
            set.clear();
            if (CollectionUtils.isNotEmpty(packageList)) {
                for (String packagePath : packageList) {
                    // classpath*:xxx
                    String patten = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils
                        .convertClassNameToResourcePath(packagePath) + "/**/*.class";
                    Resource[] resources = this.resolver.getResources(patten);
                    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resolver);
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            String className = metadataReader.getClassMetadata().getClassName();
                            if (CollectionUtils.isNotEmpty(typeFilters)) {
                                for (TypeFilter filter : typeFilters) {
                                    if (filter.match(metadataReader, metadataReaderFactory)) {
                                        this.set.add(Class.forName(className));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return this.set;
        }
    }

    public static void main(String[] args) {
        String[] strs = {"com.liupeng.spring.scan"};
        Set<Class<?>> set = ScanUtil.scanPackages(strs, TestAnnotation.class, Service.class);
        System.out.println(set);
    }
}