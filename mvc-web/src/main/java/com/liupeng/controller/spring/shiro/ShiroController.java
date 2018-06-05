package com.liupeng.controller.spring.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * shiro
 *
 * @author fengdao.lp
 * @date 2018/6/4
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    /**
     * 登录
     */
    @RequestMapping("/login")
    public void test() {
        // 读取 shiro.ini 文件内容
        Factory factory = new IniSecurityManagerFactory("classpath:spring/shiro-realm.ini");
        SecurityManager securityManager = (SecurityManager)factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String)session.getAttribute("someKey");
        if ("aValue".equalsIgnoreCase(value)) {
            System.out.println("someKey的值：" + value);
        }

        // 登录
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "zhangsan");
        token.setRememberMe(true);

        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("用户名不存在:" + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            System.out.println("账户密码 " + token.getPrincipal() + " 不正确!");
        } catch (LockedAccountException lae) {
            System.out.println("用户名 " + token.getPrincipal() + " 被锁定 !");
        }

        // 认证成功后
        if (currentUser.isAuthenticated()) {
            // 打印
            System.out.println("用户 " + currentUser.getPrincipal() + " 登陆成功！");
            // 测试角色
            System.out.println("是否拥有 manager 角色：" + currentUser.hasRole("manager"));
            // 测试权限
            System.out.println("是否拥有 user:create 权限" + currentUser.isPermitted("user:create"));
            // 退出
            //currentUser.logout();
        }
    }

    /**
     * 登出
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index.jsp";
    }

    /**
     * 跳转到页面验证shiro
     */
    @RequestMapping("listUI")
    public String listUI(HttpServletRequest request) {
        return "user/listUI";
    }
}