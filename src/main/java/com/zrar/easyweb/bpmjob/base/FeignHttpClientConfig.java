package com.zrar.easyweb.bpmjob.base;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author zhangxw
 * @Date 2019-12-12
 */
@Configuration
public class FeignHttpClientConfig{

    @Value("${feign.httpclient.maxTotal:1500}")
    private int maxTotal;
    @Value("${feign.httpclient.maxPerRoute:200}")
    private int maxPerRoute;
    @Value("${feign.httpclient.connectTimeout:3000}")
    private int connectTimeout;
    @Value("${feign.httpclient.socketTimeout:1500}")
    private int socketTimeout;
    @Value("${feign.httpclient.retry:true}")
    private Boolean retry;
    @Value("${feign.httpclient.retryCount:2}")
    private int retryCount;

    @Bean
    public HttpClient httpClient(HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }

    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        return connectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(HttpClientConnectionManager connectionManager){
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(socketTimeout).build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(connectionManager);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        //是否重试，重试次数
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, retry));
        //保持长连接配置，需在头添加keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        return httpClientBuilder;
    }
}
