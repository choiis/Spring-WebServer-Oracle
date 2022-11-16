package com.singer.infrastructure.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AopAdvice {

	@After("execution(* com.singer.application.service..*Service.*(..))")
	public void clickBoardLog(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		if (StringUtils.isEmpty(userid)) {
			return;
		}
		Signature signature = jp.getSignature();
		String name = signature.getName();
		log.debug("method call " + name + " by " + userid);

	}
	
	@Around("execution(* com.singer.domain.dao..*Dao.*(..))")
	public Object aopDao(ProceedingJoinPoint pjp) throws Throwable {

		log.debug(Arrays.toString(pjp.getArgs()));

		long startTime = System.currentTimeMillis();

		Object result = pjp.proceed();
		long endTime = System.currentTimeMillis();
		String name = pjp.getSignature().getName();

		log.debug(name + " SQL execution time " + (endTime - startTime));
		return result;
	}
}
