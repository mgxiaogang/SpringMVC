package servlet.listener;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.common.collect.Maps;

/**
 * @author fengdao.lp
 * @date 2018/8/8
 */
public class MySessionContext {
    private static Map<String, Object> map = Maps.newHashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (null != session) {
            map.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            map.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null) { return null; }
        return (HttpSession)map.get(session_id);
    }
}
