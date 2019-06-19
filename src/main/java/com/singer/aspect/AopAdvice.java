package com.singer.aspect;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.singer.common.DateUtil;

@Component
@Aspect
public class AopAdvice {
	private final Log log = LogFactory.getLog(AopAdvice.class);

	@After("execution(* com.singer.service.*Impl.*(..))")
	public void aopService(JoinPoint joinPoint) {

		log.debug("aopService============");
		log.debug(Arrays.toString(joinPoint.getArgs()));
		Signature sg = joinPoint.getSignature();
		log.debug(sg.getName());
		log.debug(sg.getDeclaringTypeName());
		log.debug(joinPoint.getTarget());
	}

	@Around("execution(* com.singer.dao.*Dao.*(..))")
	public Object aopDao(ProceedingJoinPoint pjp) throws Throwable {

		log.debug("aopDao============");
		log.debug(Arrays.toString(pjp.getArgs()));

		long startTime = System.currentTimeMillis();

		Object result = pjp.proceed();
		long endTime = System.currentTimeMillis();

		log.debug(pjp.getSignature().getName() + " 실행시간" + (endTime - startTime));
		return result;
	}

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@After("execution(* com.singer.controller.*Controller.show*(..))")
	public void aopShow(JoinPoint pjp) throws Throwable {

		log.debug("aopShow============");
		// Redis Insert
		// Hash구조에 Key는 날짜 field는 화면명
		HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();
		hashOp.increment(DateUtil.getToday(), pjp.getSignature().getName(), 1);
	}
}
