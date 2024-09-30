package com.game.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate工厂
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/22 1:39
 */
@Component
public class RestTemplateFactory {

    private static final int MAX_TOTAL_CONNECTIONS = 100; // 最大连接数
    private static final int MAX_CONNECTIONS_PER_ROUTE = 100; // 单路由的最大并发数
    private static final int CONNECT_TIMEOUT = 5000; // 连接超时时间

    public RestTemplate getRestTemplate() {
        // 配置连接池
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);

        // 创建 HttpClient 实例
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        // 创建 HttpRequestFactory
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        return restTemplate;
    }
}
