package com.liupeng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("login")
    public String login(String userName, String password, HttpServletRequest request) {
        // 首次登录
        if ("admin".equals(userName) && "admin".equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);
            return "manageUI";
        }

        // 如果已经登录过，从另一个 tomcat 访问该方法，跳转到 manageUI 页面可以查看 session 信息
        if (StringUtils.isBlank(userName) && StringUtils.isBlank(password)) {
            return "manageUI";
        }

        return "redirect:/index.jsp";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {

        session.removeAttribute("userName");
        session.removeAttribute("url");

        return "redirect:/index.jsp";
    }

}
