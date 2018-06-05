package com.liupeng.controller.spring.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.liupeng.controller.spring.shiro.vo.Result;
import com.liupeng.spring.shiro.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShiroLoginController {

    @RequestMapping("shiro-login")
    @ResponseBody
    public Result login(String userName, String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(userName.trim(), password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);

        } catch (UnknownAccountException e) {
            return Result.fail(403, "用户名不存在");

        } catch (IncorrectCredentialsException e) {
            return Result.fail(403, "密码不正确");

        }

        return Result.succeed("/manageUI");
    }

    @RequestMapping("/shiro-logout")
    @ResponseBody
    public Result logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.succeed("/index.jsp");
    }

    @RequestMapping("manageUI")
    public String manageUI(HttpServletRequest request) {

        //主体
        Subject subject = SecurityUtils.getSubject();

        User user = (User)subject.getPrincipal();

        request.setAttribute("loginUser", user);

        return "manageUI";
    }
}
