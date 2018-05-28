package com.liupeng.proxy.cglib;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author fengdao.lp
 * @date 2018/5/28
 */
public class ConcreteDispatcher implements Dispatcher {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("Dispatcher loadObject() ...");
        PropertyBean bean = new PropertyBean();
        bean.setPropertyName("Dispatcher object propertyName!");
        return bean;
    }

    public static void main(String[] args) {
        PropertyBean propertyBean = (PropertyBean)Enhancer.create(PropertyBean.class, new ConcreteDispatcher());

        //此处会回调loadObject
        System.out.println(propertyBean.getPropertyName());

        System.out.println("after...");
        //每次都回调loadObejct
        System.out.println(propertyBean.getPropertyName());
    }

    public class PropertyBean {
        private String propertyName;

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }
    }
}
