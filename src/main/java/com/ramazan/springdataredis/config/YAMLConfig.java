package com.ramazan.springdataredis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@ConfigurationProperties
public class YAMLConfig {

    @Value("${cache.expirations}")
    private Long expirations;

    public Long getExpirations() {
        return expirations;
    }

    public void setExpirations(Long expirations) {
        this.expirations = expirations;
    }
}
