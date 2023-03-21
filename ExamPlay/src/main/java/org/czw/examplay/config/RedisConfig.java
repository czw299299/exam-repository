package org.czw.examplay.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean("remoteRedisTemplate")
    public RedisTemplate redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String,String> redisObj = new RedisTemplate<>();
        redisObj.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer jsonRedisSerializer = new FastJsonRedisSerializer<>(String.class);
        redisObj.setKeySerializer(stringRedisSerializer);
        redisObj.setValueSerializer(jsonRedisSerializer);
        redisObj.setHashKeySerializer(stringRedisSerializer);
        redisObj.setHashValueSerializer(jsonRedisSerializer);
        redisObj.afterPropertiesSet();
        return redisObj;
    }
    @Bean("loacalRedisTemplate")
    public RedisTemplate loacalRedisTemplate(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("127.0.0.1");
        redisConfig.setPort(6379);
        redisConfig.setPassword("123456");
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder().build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig,clientConfiguration);
        factory.afterPropertiesSet();
        RedisTemplate<String,String> redisObj = new RedisTemplate<>();
        redisObj.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisObj.setKeySerializer(stringRedisSerializer);
        redisObj.setValueSerializer(stringRedisSerializer);
        return redisObj;
    }
}
