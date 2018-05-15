package servlet.autowire;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liupeng.spring.beanFactory.CacheBeansFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fengdao.lp
 * @date 2018/5/15
 */
public class AutowaredServlet extends AutowaredAwareServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AutowaredServlet.class);
    @Autowired
    private CacheBeansFactory cacheBeansFactory;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        LOG.info("test");
        PrintWriter writer = response.getWriter();
        writer.write("<h1>hello</h1>");
    }
}
