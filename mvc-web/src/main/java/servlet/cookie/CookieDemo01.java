package servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.MediaType;

/**
 * 使用cookie记录用户上一次访问的时间
 *
 * @author fengdao.lp
 * @date 2018/8/8
 */
public class CookieDemo01 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");

        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {

            Cookie cookie = Arrays.stream(cookies).filter(ck -> "lastAccessTime".equals(ck.getName())).findAny().orElse(
                null);
            if (null == cookie) {
                writer.write("这是您第一次访问本站！");
            } else {
                writer.write("您上次的访问时间是：");
                Long lastAccessTime = Long.parseLong(cookie.getValue());
                Date date = new Date(lastAccessTime);
                writer.write(DateFormatUtils.format(date, "yyyy-MM-dd HH:ss:mm"));
            }
        }

        // 用户访问过之后重新设置用户的访问时间，存储到cookie中，然后发送到客户端浏览器
        Cookie cookie1 = new Cookie("lastAccessTime", System.currentTimeMillis() + "");
        // 中文必须用URLEncoder.encode编码，获取的时候再用URLDecoder.decode(cookies[i].getValue(), "UTF-8")解码
        Cookie cookie2 = new Cookie("userName", URLEncoder.encode("刘鹏", "UTF-8"));
        cookie1.setDomain(".liupeng.com");
        // 设置有效期为一天。防止浏览器关闭后失效（有效期设置为0，即可以删除cookie）
        cookie1.setMaxAge(24 * 60 * 60);
        cookie2.setMaxAge(24 * 60 * 60);
        // 将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
        response.addCookie(cookie1);
        response.addCookie(cookie2);
    }
}
