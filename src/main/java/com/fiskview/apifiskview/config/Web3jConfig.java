package com.fiskview.apifiskview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Value("${web3j.server.url}")
    private String urlServer;

    @Value("${web3j.server.port}")
    private String port;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(urlServer + ":" + port));
    }

}
