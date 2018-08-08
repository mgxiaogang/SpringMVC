package servlet.autowire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlet.session.TestServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class AutowaredAwareServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        // 将自己定义为bean，继承他的子类才可以依赖注入其他元素,因为它是abstract的
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }
}
