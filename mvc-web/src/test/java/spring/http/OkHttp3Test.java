package spring.http;

import java.io.IOException;

import com.liupeng.spring.httpclient.OKHttpClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author fengdao.lp
 * @date 2018/6/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:wukong-tx-test.xml")
public class OkHttp3Test {

    @Autowired
    private OKHttpClientTest okHttpClientTest;

    @Test
    public void testOkHttp3GetSync() throws IOException {
        String result = okHttpClientTest.testGetSync("http://localhost:8088/springBoot/helloworld1?username=123");
        System.out.println(result);
    }

    @Test
    public void testOkHttp3GetASync() throws IOException {
        okHttpClientTest.testGetAsync("http://localhost:8088/springBoot/helloworld1?username=123");
    }

    @Test
    public void testOkHttp3Post() throws IOException {
        String result = okHttpClientTest.testPostSync("http://localhost:8088/springBoot/helloworld1?username=123");
        System.out.println(result);
    }
}
