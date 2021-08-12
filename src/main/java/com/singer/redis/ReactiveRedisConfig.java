package com.singer.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;

@Configuration
public class ReactiveRedisConfig {

	private final Log log = LogFactory.getLog(ReactiveRedisConfig.class);

	private final String REDIS_HOST = "127.0.0.1";
	private final int REDIS_PORT = 6379;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT));
	}

	@Bean
	public ReactiveRedisTemplate<String, Object> personRedisTemplate() {

		log.debug("ReactiveRedis Serializer");
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, Object> serializationContext = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(redisConnectionFactory(), serializationContext);
	}
}
