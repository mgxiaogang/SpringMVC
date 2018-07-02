package com.liupeng.proxy.cglib.lazyLoader;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

/**
 * @author fengdao.lp
 * @date 2018/5/28
 */
public class ConcreteClassLazyLoader implements LazyLoader {

    public class PropertyBean {
        private String propertyName;

        public PropertyBean(){}

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }
    }

    @Override
    public Object loadObject() throws Exception {
        System.out.println("LazyLoader loadObject() ...");
        PropertyBean bean = new PropertyBean();
        bean.setPropertyName("lazy-load object propertyName!");
        return bean;
    }

    public static void main(String[] args) {
        PropertyBean propertyBean = (PropertyBean)Enhancer.create(PropertyBean.class, new ConcreteClassLazyLoader());

        //此处会回调loadObject
        System.out.println(propertyBean.getPropertyName());

        System.out.println("after...");
        //之后不再回调loadObejct，直接访问第一次返回的对象
        System.out.println(propertyBean.getPropertyName());
    }
}
