package com.liupeng.spring.httpclient;

import java.util.Map;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author fengdao.lp
 * @date 2018/6/13
 */
@Service
public class HttpClientTest {

    private static final String URL = "http://localhost:8088/springBoot/helloworld1?username={username}";

    @Autowired
    private RestTemplate restTemplate;

    public void httpClientTest() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "Liu");
        String str = restTemplate.getForObject(URL, String.class, map);
        System.out.println(str);
    }
}
