package com.singer.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	private final Log log = LogFactory.getLog(RedisConfig.class);

	@Autowired
	private JedisConnectionFactory conn;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		log.debug("Redis Serializer");
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		redisTemplate.setConnectionFactory(conn);
		return redisTemplate;
	}

}
