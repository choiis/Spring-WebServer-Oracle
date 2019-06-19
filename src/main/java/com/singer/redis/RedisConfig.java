package com.singer.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	private String server = "127.0.0.1";
	private int port = 6379;

	private final Log log = LogFactory.getLog(RedisConfig.class);

	@Bean
	public JedisConnectionFactory connectionFactory() {
		log.debug("HostName : " + server);
		log.debug("port : " + port);

		JedisConnectionFactory conn = new JedisConnectionFactory();
		conn.setHostName(server);
		conn.setPort(port);

		conn.setUsePool(true);
		return conn;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}

}
