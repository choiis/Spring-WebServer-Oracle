package com.singer.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopLog {
	private final Log log = LogFactory.getLog(AopLog.class);
	
	@After("execution(* com.singer.service.*Impl.*(..))")
	public void aopLogger(JoinPoint joinPoint) {

		log.debug("AOP============");
	}
}
