package com.liupeng.proxy.cglib;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.liupeng.advice.vo.Result;
import com.liupeng.advice.vo.User;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.DefaultNamingPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fengdao.lp
 * @date 2018/5/28
 */
@Controller
@RequestMapping(value = "/liupeng/cglib")
public class BeanGeneratorController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result test() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("fengdao");

        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.setNamingPolicy(new LiupengNamingPolicy());
        beanGenerator.setSuperclass(user.getClass());
        beanGenerator.addProperty("fanlai", String.class);
        // 生成新增字段后的新对象
        Object current = beanGenerator.create();
        System.out.println(current);
        // copy原始对象的字段值到 新 对象的字段值
        BeanCopier copier = BeanCopier.create(user.getClass(), current.getClass(), false);
        copier.copy(user, current, null);
        System.out.println(current);
        // 为新生成的字段赋值
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("fanlai", current.getClass());
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(current, "111111");
        return Result.buildSuccessResultOf(current);
    }

    public static class LiupengNamingPolicy extends DefaultNamingPolicy {
        @Override
        protected String getTag() {
            return "byLiuPengCglib";
        }
    }
}
