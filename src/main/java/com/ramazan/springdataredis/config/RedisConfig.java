package com.ramazan.springdataredis.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.lang.reflect.Method;
import java.time.Duration;

@EnableCaching
@Configuration
@ComponentScan("com.ramazan.springdataredis")
@EnableRedisRepositories(basePackages = "com.ramazan.springdataredis.repo")
@PropertySource("classpath:application.yml")
public class RedisConfig extends CachingConfigurerSupport {

    /*@Autowired
    private CustomCacheErrorHandler customCacheErrorHandler;*/

    private final YAMLConfig yamlConfig;

    private String KEY_SEPERATOR = "#";

    @Autowired
    public RedisConfig(YAMLConfig yamlConfig) {
        this.yamlConfig = yamlConfig;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }


    //Custom Connection Properties
   /* @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }*/

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        final RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        /*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer);*/
        template.setConnectionFactory(jedisConnectionFactory());


        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Duration expiration = Duration.ofSeconds(yamlConfig.getExpirations());
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(expiration)).build();
    }



    //Custom keyGenerator
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {

                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getSimpleName());
                sb.append(KEY_SEPERATOR);
                sb.append(method.getName());
                sb.append(KEY_SEPERATOR);
                for(Object param : params){
                    sb.append(param.toString());
                    sb.append(KEY_SEPERATOR);
                }

                String str = sb.toString();

                return str.substring(0, str.length() - 1);
            }
        };
    }

   /* @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return customCacheErrorHandler;
    }*/
}
