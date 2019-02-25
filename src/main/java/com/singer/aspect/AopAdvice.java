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
import org.springframework.stereotype.Component;

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

}
