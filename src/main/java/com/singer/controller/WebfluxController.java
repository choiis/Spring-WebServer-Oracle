package com.singer.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class WebfluxController {

	@GetMapping("/sse/Flux")
	public Flux<Object> sse() {
		Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
		return Flux.fromStream(stream.limit(10)).map(i -> Collections.singletonMap("value", i));
	}

	@GetMapping("/sse/FluxString")
	public Flux<String> ssestring() {
		String[] str = new String[20];
		String strfix = "str";
		for (int i = 0; i < 20; i++) {
			str[i] = strfix + (i + 1);
		}
		Stream<String> stream = Arrays.stream(str);
		return Flux.fromStream(stream.limit(10)).map(st -> st);
	}

	private ReactiveRedisOperations<String, Object> redisOps;

	@GetMapping("/redis/get/{param}")
	public Mono<Object> redisGet(@PathVariable String param) {
		return redisOps.opsForValue().get(param);
	}

	@GetMapping("/redis/sorted/{param}")
	public Flux<Object> redisSorted(@PathVariable String param) {
		Range<Long> range = Range.closed(new Long(0), new Long(-1));
		return redisOps.opsForZSet().reverseRange(param, range);
	}

	@GetMapping("/redis/hash/{param}")
	public Flux<Entry<Object, Object>> redishash(@PathVariable String param) {
		return redisOps.opsForHash().entries(param);
	}

	@GetMapping("/redis/list/{param}")
	public Flux<Object> redislist(@PathVariable String param) {
		return redisOps.opsForList().range(param, 0, -1);
	}

	@GetMapping("/redis/keyType")
	public Flux<Object> keyType() {
		return redisOps.keys("*").flatMap(key -> redisOps.type(key));
	}

	@GetMapping("/redis/keys")
	public Flux<Object> keys() {
		return redisOps.keys("*").flatMap(key -> redisOps.scan());
	}

}
