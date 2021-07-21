package com.singer.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.singer.reactive.ReactiveRedisOps;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
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

	@Inject
	private ReactiveRedisOps reactiveOps;

	@GetMapping("/redis/get/{param}")
	public Mono<Object> redisGet(@PathVariable String param) {
		return reactiveOps.getStringValue(param);
	}

	@GetMapping("/redis/hash/{param}")
	public Flux<Entry<Object, Object>> redishash(@PathVariable String param) {
		return reactiveOps.getHash(param);
	}

	@GetMapping("/redis/list/{param}")
	public Flux<Object> redislist(@PathVariable String param) {
		return reactiveOps.getList(param);
	}

	@GetMapping("/redis/keys")
	public Flux<Object> keys() {
		return reactiveOps.keys();
	}

	@GetMapping("/redis/keyType")
	public Flux<Object> keyType() {
		return reactiveOps.keyType();
	}

}
