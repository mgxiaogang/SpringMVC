package com.liupeng.innerspring.annotation.demo2_scan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author liupeng
 * @Date 2018/12/26
 */
public class ScanUtils {
    public static void scan() {
        // BeanScannerConfigurer类变为了Bean
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(CustomizeScanTest.class);
        annotationConfigApplicationContext.refresh();

        ScanClass1 injectClass = annotationConfigApplicationContext.getBean(ScanClass1.class);
        injectClass.print();
    }
}
