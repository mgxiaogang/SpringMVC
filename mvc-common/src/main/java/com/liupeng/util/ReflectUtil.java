package com.liupeng.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射util
 *
 * @author fengdao.lp
 * @date 2018/7/7
 */
public class ReflectUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 获取指定类的所有声明的字段，包括父类
     */
    public static List<Field> getAllFields(Class<?> targetClass) {
        if (targetClass == null) {
            return Collections.emptyList();
        }
        List<Field> fields = Lists.newArrayList();
        while (targetClass != Object.class) {
            fields.addAll(Arrays.asList(targetClass.getDeclaredFields()));
            targetClass = targetClass.getSuperclass();
        }
        return fields;
    }

    /**
     * 根据属性名称获取属性，包括父类
     * <pre>
     *     返回值：private int com.liupeng.util.ReflectUtil$ReflectTest.age
     * </pre>
     */
    public static <T> Field getFieldByFieldName(T t, String fieldName) {
        if (t == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        for (Class<?> superClass = t.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                LOG.error("getFieldByName error.", e);
                return null;
            }
        }
        LOG.warn("no such field {} in class {}", fieldName, t.getClass());
        return null;
    }

    /**
     * 根据字段名称获取字段值，包括父类
     * <pre>
     *     返回值：1
     * </pre>
     */
    public static <T> Object getFieldValueByFieldName(T t, String fieldName) {
        if (t == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        try {
            List<Field> fields = ReflectUtil.getAllFields(t.getClass());
            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), t.getClass());
                    return propertyDescriptor.getReadMethod().invoke(t);
                }
            }
        } catch (Exception e) {
            LOG.error("getFieldByName error.", e);
            return null;
        }
        return null;
    }

    /**
     * 获取对象的属性名和对应的值
     * <pre>
     *     返回值：{name=liupeng, age=1}
     * </pre>
     */
    public static Map<String, Object> invokeGetMethod(Object object) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            // 这边传入的是接口的话,得到的class是实现类
            Class<?> clazz = object.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                Method method = propertyDescriptor.getReadMethod();
                if (method.invoke(object) != null) {
                    map.put(propertyDescriptor.getName(), method.invoke(object));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取对象的属性信息
     * <pre>
     *     vo => [{filedName=itemId, typeName=java.lang.Long, value=100}, {filedName=title, typeName=java.lang.String,
     * value=title}]
     *
     * </pre>
     *
     * @param object 对象
     * @return 信息
     */
    public static List<Map<String, Object>> getFieldInfo(Object object) {

        List<Map<String, Object>> list = Lists.newArrayList();
        try {
            // 这边传入的是接口的话,得到的class是实现类
            Class<?> clazz = object.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                Method method = propertyDescriptor.getReadMethod();
                String typeName = propertyDescriptor.getPropertyType().getName();
                if (method.invoke(object) != null) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("filedName", propertyDescriptor.getName());
                    map.put("value", method.invoke(object));
                    map.put("typeName", typeName);
                    list.add(map);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static class ReflectTest extends ReflectSuperTest {
        private int age = 1;
        private String name = "liupeng";

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class ReflectSuperTest {
        private int height = 181;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static void main(String[] args) {
        ReflectTest reflectTest = new ReflectTest();
        System.out.println(invokeGetMethod(reflectTest));
    }
}
