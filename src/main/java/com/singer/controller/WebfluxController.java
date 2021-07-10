package com.singer.controller;

import java.util.Collections;
import java.util.stream.Stream;

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

	@GetMapping("/sseFlux")
	public Flux<Object> sse() {
		Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
		return Flux.fromStream(stream.limit(10)).map(i -> Collections.singletonMap("value", i));
	}

	private ReactiveRedisOperations<String, Object> personOps;

	@GetMapping("/redis/man")
	public Mono<Object> redisMan() {
		return personOps.opsForValue().get("man");
	}

	@GetMapping("/redis/keyType")
	public Flux<Object> keyType() {
		return personOps.keys("*").flatMap(key -> personOps.type(key));
	}

	@GetMapping("/redis/keys")
	public Flux<Object> keys() {
		return personOps.keys("*").flatMap(key -> personOps.scan());
	}

}
