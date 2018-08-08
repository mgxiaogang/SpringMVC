package servlet.session;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public class TestServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(TestServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        // 强制更新session
        //updateSession(request);
        // session操作
        HttpSession session = request.getSession();
        session.setAttribute("data", "刘鹏");
        String sessionId = session.getId();
        LOG.info("test");
        if (session.isNew()) {
            response.getWriter().write("session创建成功，session的ID是：" + sessionId);
        } else {
            response.getWriter().print("服务器内已经存在改session了，session的ID是：" + sessionId);
        }
    }

    /**
     * 强制更新JSESSIONID：
     * 登录前的请求一般都是http的，http是不安全的，假设用户登录前的JSESSIONID被人取得，如果登录后不变更JSESSIONID的话，即使登录请求是https的，该用户仍然会被他人冒充
     *
     * @param request 请求
     */
    private void updateSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 首先将原session中的数据转移至一临时map中
        Map<String, Object> tempMap = Maps.newHashMap();
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String name = sessionNames.nextElement();
            tempMap.put(name, session.getAttribute(name));
        }
        // 注销原session，为的是重置sessionId
        session.invalidate();

        // 将临时map中的数据转移至新session
        session = request.getSession();
        for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
