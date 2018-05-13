package com.liupeng.advice.render;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liupeng.advice.annotation.UserNickRender;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.DefaultNamingPolicy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
@Service
public class UserRenderer extends AbstractResponseRenderer {

    @Override
    Object doRender(Object data) {
        if (null == data) {
            return null;
        }

        if (data instanceof List) {
            List list = (List)data;
            for (int i = 0; i < list.size(); i++) {
                list.set(i, renderData(list.get(i)));
            }
        } else {
            return renderData(data);
        }
        return null;
    }

    /**
     * 渲染数据，针对注解为{@link UserNickRender}属性替换为工号对应的名称
     *
     * @param data 原始数据
     * @return 渲染后的实例
     */
    private Object renderData(Object data) {
        Class<?> clazz = data.getClass();
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(clazz, UserNickRender.class);
        Object current = null;
        if (CollectionUtils.isNotEmpty(fields)) {
            // 判断是否需要动态添加字段，如果需要，则生成新实例，拷贝对象
            current = prepareObject(data, fields);
            for (Field field : fields) {
                UserNickRender userNickRender = field.getAnnotation(UserNickRender.class);
                try {
                    // 调用get方法得到字段原始值
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object originValue = readMethod.invoke(current);

                    // 调用set方法设置新字段值
                    String employeeId = originValue.toString();
                    String actualField = userNickRender.aliasField();
                    Method writeMethod;
                    if (StringUtils.isNotBlank(actualField)) {
                        writeMethod = new PropertyDescriptor(actualField, current.getClass()).getWriteMethod();
                    } else {
                        writeMethod = propertyDescriptor.getWriteMethod();
                    }
                    writeMethod.invoke(current, employeeId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return current;
    }

    /**
     * 根据UserNickRender 的 aliasField字段判断是否需要动态添加字段
     * 如果需要，则生成新实例，并拷贝数据
     *
     * @param source 原始对象
     * @param fields 拥有UserNickRender注解的字段
     * @return source实例或者source子类的实例(添加了字段后的实例)
     */
    private Object prepareObject(Object source, List<Field> fields) {
        Set<String> fieldNameToAdds = new HashSet<>();
        // 别名增加到set中
        for (Field field : fields) {
            UserNickRender userNickRender = field.getAnnotation(UserNickRender.class);
            if (null != userNickRender && StringUtils.isNotBlank(userNickRender.aliasField())) {
                if (fieldNameToAdds.contains(userNickRender.aliasField())) {
                    throw new IllegalArgumentException("duplicate field, already exists field name:" +
                        field.getDeclaringClass().getName() + "." + field.getName());
                }
                fieldNameToAdds.add(userNickRender.aliasField());
            }
        }

        if (fieldNameToAdds.size() > 0) {
            BeanGenerator beanGenerator = new BeanGenerator();
            beanGenerator.setNamingPolicy(new LiupengNamingPolicy());
            beanGenerator.setSuperclass(source.getClass());
            for (String fieldNameToAdd : fieldNameToAdds) {
                // 仅仅用于渲染返回结果，field类型设置为Object
                beanGenerator.addProperty(fieldNameToAdd, Object.class);
            }
            Object current = beanGenerator.create();
            BeanCopier copier = BeanCopier.create(source.getClass(), current.getClass(), false);
            copier.copy(source, current, null);
            return current;
        }
        return source;

    }

    public static class LiupengNamingPolicy extends DefaultNamingPolicy {
        @Override
        protected String getTag() {
            return "byLPCglib";
        }
    }
}


