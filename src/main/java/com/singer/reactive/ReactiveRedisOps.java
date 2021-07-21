package com.singer.reactive;

import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import com.singer.redis.RedisDao;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ReactiveRedisOps {

	private ReactiveRedisOperations<String, Object> redisOps;

	public Mono<Object> getStringValue(String param) {
		return redisOps.opsForValue().get(param);
	}

	public Flux<Entry<Object, Object>> getHash(String param) {
		return redisOps.opsForHash().entries(param);
	}

	public Flux<Object> getList(String param) {
		return redisOps.opsForList().range(param, 0, -1);
	}

	public Flux<Object> keys() {
		return redisOps.keys("*").flatMap(key -> redisOps.scan());
	}

	@Inject
	private RedisDao redisDao;

	public Flux<Object> keyType() {
		return redisOps.keys("*").map(x -> {
			return x + "  : " + redisDao.type(x).toString() + " ";
		});
	}
}
