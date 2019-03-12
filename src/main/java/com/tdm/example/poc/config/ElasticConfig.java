package com.tdm.example.poc.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticConfig {

    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.port}")
    private int port;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() throws UnknownHostException {

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(InetAddress.getByName(host),port))
                        .setRequestConfigCallback(builder -> builder.setConnectTimeout(5000).setSocketTimeout(120000))
                        .setMaxRetryTimeoutMillis(120000));

    }



}
