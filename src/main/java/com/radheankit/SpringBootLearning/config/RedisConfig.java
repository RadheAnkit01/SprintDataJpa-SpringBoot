package com.radheankit.SpringBootLearning.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {


    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration defaultRedisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(15));
        Map<String,RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        cacheConfigurationMap.put("products", defaultRedisCacheConfiguration.entryTtl(Duration.ofHours(1)));
        cacheConfigurationMap.put("orders", defaultRedisCacheConfiguration.entryTtl(Duration.ofMinutes(3)));
        cacheConfigurationMap.put("users", defaultRedisCacheConfiguration.entryTtl(Duration.ofMinutes(10)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultRedisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .build();
    }
}
