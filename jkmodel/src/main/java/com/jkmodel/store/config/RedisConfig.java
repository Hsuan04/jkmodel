package com.jkmodel.store.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "redisConnectionFactoryDB00")
    @Primary
    public RedisConnectionFactory redisConnectionFactoryDB00() {
        return createConnectionFactory(1);
    }

    @Bean(name = "redisTemplateDB00")
    public RedisTemplate<String, Object> redisTemplateDB00(@Qualifier("redisConnectionFactoryDB00") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
    }

    // 用戶觀看商品習慣
    @Bean(name = "redisConnectionFactoryDB01")
    public RedisConnectionFactory redisConnectionFactoryDB01() {
        return createConnectionFactory(1);
    }

    @Bean(name = "redisTemplateDB01")
    public RedisTemplate<String, Object> redisTemplateDB01(@Qualifier("redisConnectionFactoryDB01") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
    }

    // 紀錄當日熱門商品
    @Bean(name = "redisConnectionFactoryDB02")
    public RedisConnectionFactory redisConnectionFactoryDB02() {
        return createConnectionFactory(2);
    }

    @Bean(name = "redisTemplateDB02")
    public RedisTemplate<String, Object> redisTemplateDB02(@Qualifier("redisConnectionFactoryDB02") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
    }

    // 儲存剩餘票卷數量
    @Bean(name = "redisConnectionFactoryDB03")
    public RedisConnectionFactory redisConnectionFactoryDB03() {
        return createConnectionFactory(3);
    }

    @Bean(name = "redisTemplateDB03")
    public RedisTemplate<String, Object> redisTemplateDB03(@Qualifier("redisConnectionFactoryDB03") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
    }

    @Bean(name = "redisConnectionFactoryDB04")
    public RedisConnectionFactory redisConnectionFactoryDB04() {
        return createConnectionFactory(4);
    }

    @Bean(name = "redisTemplateDB04")
    public RedisTemplate<String, Object> redisTemplateDB04(@Qualifier("redisConnectionFactoryDB04") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
    }

    private RedisConnectionFactory createConnectionFactory(int databaseIndex) {
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMinutes(30))
                .build();

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(databaseIndex);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }

    private RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setEnableTransactionSupport(true);
        return template;
    }
}