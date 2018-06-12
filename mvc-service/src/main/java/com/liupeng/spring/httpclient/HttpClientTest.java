package com.liupeng.spring.httpclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author fengdao.lp
 * @date 2018/6/13
 */
@Service
public class HttpClientTest {

    private static final String URL = "http://localhost:8088/springBoot/helloworld1?username=li";

    @Autowired
    private RestTemplate restTemplate;

    public void httpClientTest() {
        String str = restTemplate.getForObject(URL, String.class);
        System.out.println(str);
    }
}
