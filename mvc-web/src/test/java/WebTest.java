import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vo.User;

/**
 * @author fengdao.lp
 * @date 2018/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:wukong-tx-test.xml")
public class WebTest {
    private static final String URL = "http://localhost:8088/springBoot/helloworld1?username={username}";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate() {

        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "Liu");
        String str = restTemplate.getForObject(URL, String.class, map);
        System.out.println(str);

        MultiValueMap map1 = new LinkedMultiValueMap<>();
        map1.add("username", "Liu");
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8088")
            .path("/springBoot/helloworld1")
            .queryParams(map1)
            .build()
            .toUri();
        String str1 = restTemplate.getForObject(uri, String.class);
        System.out.println(str1);

        User user = new User();
        user.setUsername("LP");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
            "http://localhost:8088/springBoot/helloworld1?username=123", user, String.class);
        System.out.println(responseEntity);

        Map<String, Object> callParams = new HashMap<>();
        callParams.put("method", "queryJobStatus");
        callParams.put("recordId", 123);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(callParams);
        Map result =
            restTemplate.postForEntity("http://sic.alibaba-inc.com/offcalcpai/rest.json", request, Map.class).getBody();
        System.out.println(result);
    }
}
