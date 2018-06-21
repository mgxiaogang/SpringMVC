package com.liupeng.interceptor;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Springmvc中使用guava限流
 *
 * @author fengdao.lp
 * @date 2018/6/20
 */
public class RateLimitInterceptor extends HandlerInterceptorAdapter {
    public enum LimitType {
        // 丢弃
        DROP,
        // 等待
        WAIT
    }

    /**
     * 限流器
     */
    private RateLimiter rateLimiter;

    private LimitType limitType = LimitType.DROP;

    /**
     * 默认构造器
     */
    public RateLimitInterceptor() {
        this.rateLimiter = RateLimiter.create(10);
    }

    /**
     * @param tips      限流量 (每秒处理量)
     * @param limitType 限流类型:等待/丢弃(达到限流量)
     */
    public RateLimitInterceptor(int tips, RateLimitInterceptor.LimitType limitType) {
        this.rateLimiter = RateLimiter.create(tips);
        this.limitType = limitType;
    }

    /**
     * @param permitsPerSecond 每秒新增的令牌数
     * @param limitType        限流类型:等待/丢弃(达到限流量)
     */
    public RateLimitInterceptor(double permitsPerSecond, RateLimitInterceptor.LimitType limitType) {
        this.rateLimiter = RateLimiter.create(permitsPerSecond, 1000, TimeUnit.SECONDS);
        this.limitType = limitType;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (limitType.equals(LimitType.DROP)) {
            if (rateLimiter.tryAcquire()) {
                return super.preHandle(request, response, handler);
            } else {
                //达到限流后，往页面提示的错误信息。
                System.out.println("限流中");
                // throw new Exception("网络异常!");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
