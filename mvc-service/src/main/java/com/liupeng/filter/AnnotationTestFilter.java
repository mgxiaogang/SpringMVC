package com.liupeng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.stereotype.Service;

/**
 * 过滤器，基于servlet3.0后才有@WebFilter等标签
 *
 * @author fengdao.lp
 * @date 2018/5/31
 */
@Service
@WebFilter(
    filterName = "annotationTestFilter",
    urlPatterns = "/*",
    initParams = @WebInitParam(name = "wukong", value = "liupeng"))
public class AnnotationTestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        System.out.println("AnnotationTestFilter.doFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
