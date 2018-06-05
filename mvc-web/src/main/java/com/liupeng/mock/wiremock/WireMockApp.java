package com.liupeng.mock.wiremock;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

/**
 * @author fengdao.lp
 * @date 2018/6/5
 */
public class WireMockApp {
    public static void main(String[] args) throws IOException {
        // 连接 9999 端口
        configureFor(9999);
        // 删除旧的规则
        removeAllMappings();
        // mock
        mock("/user/1", "mock/response.json");
    }

    /**
     * get()：get请求
     * withStatus：返回状态
     * withBody：返回数据
     */
    private static void mock(String url, String fileName) throws IOException {
        // 加载文件
        ClassPathResource resource = new ClassPathResource(fileName);
        // 读取内容
        String data = FileUtils.readFileToString(resource.getFile(), "UTF-8");

        stubFor(get(urlPathEqualTo(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(data)));
    }
}