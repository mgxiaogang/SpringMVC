import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author fengdao.lp
 * @date 2018/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:wukong-tx-test.xml")
public class WebTest {
    private static final String URL = "http://localhost:8088/springBoot/helloworld1?username=li";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate() {
        String str = restTemplate.getForObject(URL, String.class);
        System.out.println(str);
    }
}
