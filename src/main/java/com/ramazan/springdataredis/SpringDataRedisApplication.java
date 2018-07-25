package com.ramazan.springdataredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
@EntityScan(basePackages = {"com.ramazan.springdataredis.model"})
public class SpringDataRedisApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisApplication.class, args);

        try {
            RedisServer redisServer=new RedisServer(6379);
            redisServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
