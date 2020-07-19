package com.fline.form.config;

import com.alipay.atgbusmng.api.client.AtgBusClient;
import com.alipay.atgbusmng.api.client.DefaultAtgBusClient;
import com.alipay.atgbusmng.api.domain.AtgBusSecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AtgBusConfig {

    @Autowired
    private AtgBusProperties atgBusProperties;

    @Bean
    public List<AtgBusSecretKey> secretKeys() {
        System.out.println(atgBusProperties.getAppKey());
        System.out.println(atgBusProperties.getAppSecret());
        List<AtgBusSecretKey> secretKeys = new ArrayList<>();
        AtgBusSecretKey atgBusSecretKey = new AtgBusSecretKey(atgBusProperties.getAppKey(), atgBusProperties.getAppSecret());
        secretKeys.add(atgBusSecretKey);
        return secretKeys;
    }

    @Bean
    public AtgBusClient atgBusClient(List<AtgBusSecretKey> secretKeys) {
        AtgBusClient atgBusClient = new DefaultAtgBusClient(atgBusProperties.getGatewayUrl(), atgBusProperties.getAppId(), secretKeys);
        return atgBusClient;
    }
}
