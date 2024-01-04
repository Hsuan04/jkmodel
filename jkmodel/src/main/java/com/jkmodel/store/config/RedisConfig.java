package com.jkmodel.store.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    // Configuration for db00
    @Bean
    RedisStandaloneConfiguration redisStandaloneConfigurationDB00() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(0); // Set the database index for db00
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return redisStandaloneConfiguration;
    }

    @Bean(name = "redisConnectionFactoryDB00")
    @Primary
    public RedisConnectionFactory redisConnectionFactoryDB00() {
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMinutes(30))
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfigurationDB00(), lettuceClientConfiguration);
    }

    @Bean(name = "redisTemplateDB00")
    public RedisTemplate<String, Object> redisTemplateDB00(@Qualifier("redisConnectionFactoryDB00") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
    }

    // Configuration for db01
    @Bean
    RedisStandaloneConfiguration redisStandaloneConfigurationDB01() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(1); // Set the database index for db01
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return redisStandaloneConfiguration;
    }

    @Bean(name = "redisConnectionFactoryDB01")
    public RedisConnectionFactory redisConnectionFactoryDB01() {
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMinutes(30))
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfigurationDB01(), lettuceClientConfiguration);
    }

    @Bean(name = "redisTemplateDB01")
    public RedisTemplate<String, Object> redisTemplateDB01(@Qualifier("redisConnectionFactoryDB01") RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory);
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
