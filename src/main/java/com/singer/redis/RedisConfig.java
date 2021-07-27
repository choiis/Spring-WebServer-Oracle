package com.singer.redis;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;

@Configuration
public class RedisConfig {

	private final Log log = LogFactory.getLog(RedisConfig.class);

	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		return new JedisPoolConfig();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory conn = new JedisConnectionFactory();
		conn.setHostName("127.0.0.1");
		conn.setPort(6379);
		return conn;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		log.debug("Redis Serializer");
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofSeconds(2)).shutdownTimeout(Duration.ZERO).build();

		return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379), clientConfig);
	}

	@Bean
	public ReactiveRedisTemplate<String, Object> personRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {

		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, Object> serializationContext = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}

}
