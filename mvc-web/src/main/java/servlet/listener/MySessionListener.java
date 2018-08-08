package servlet.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 * 如果需要根据sessionId来获取session，调用MySessionContext.getSession(sessionID)
 *
 * @author fengdao.lp
 * @date 2018/8/8
 */
public class MySessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        MySessionContext.AddSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        MySessionContext.DelSession(session);
    }
}
