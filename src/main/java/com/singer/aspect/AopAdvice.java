package com.singer.aspect;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.singer.common.DateUtil;
import com.singer.kafka.Producer;
import com.singer.redis.RedisDao;
import com.singer.util.InputQueryUtil;
import com.singer.vo.BoardVo;

@Component
@Aspect
public class AopAdvice {

	private final Log log = LogFactory.getLog(AopAdvice.class);

	@Resource(name = "redisDao")
	private RedisDao redisDao;

	@Inject
	private Producer producer;

	@After("execution(* com.singer.service.*Impl.selectOne*(..))")
	public void clickBoardLog(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		Signature signature = jp.getSignature();
		String method = signature.getName().substring(9, 13);

		int number = 0;
		Object[] objs = jp.getArgs();
		for (Object object : objs) {
			if (object instanceof BoardVo) {
				BoardVo boardVo = (BoardVo) object;
				number = boardVo.getSeq();
			}
		}

		if (number > 0) {
			InputQueryUtil queryUtil = new InputQueryUtil("log_click_board");
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add(userid);
			queryUtil.add(method);
			queryUtil.add(number);

			producer.send(queryUtil.getQuery());
		}

		redisDao.zSetIncre("selectOne", method);
	}

	@After("execution(* com.singer.service.*Impl.like*(..))")
	public void likeLog(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		Signature signature = jp.getSignature();
		String method = signature.getName().substring(4, 8);

		int number = 0;
		Object[] objs = jp.getArgs();
		for (Object object : objs) {
			if (object instanceof BoardVo) {
				BoardVo boardVo = (BoardVo) object;
				number = boardVo.getSeq();
			}
		}

		if (number > 0) {
			InputQueryUtil queryUtil = new InputQueryUtil("log_click_goods");
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add(userid);
			queryUtil.add(method);
			queryUtil.add(number);
			queryUtil.add("like");

			producer.send(queryUtil.getQuery());
		}
	}

	@After("execution(* com.singer.service.*Impl.hate*(..))")
	public void hateLog(JoinPoint jp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		Signature signature = jp.getSignature();
		String method = signature.getName().substring(4, 8);

		int number = 0;
		Object[] objs = jp.getArgs();
		for (Object object : objs) {
			if (object instanceof BoardVo) {
				BoardVo boardVo = (BoardVo) object;
				number = boardVo.getSeq();
			}
		}

		if (number > 0) {
			InputQueryUtil queryUtil = new InputQueryUtil("log_click_goods");
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add(userid);
			queryUtil.add(method);
			queryUtil.add(number);
			queryUtil.add("hate");

			producer.send(queryUtil.getQuery());
		}
	}

	@Around("execution(* com.singer.dao.*Dao.*(..))")
	public Object aopDao(ProceedingJoinPoint pjp) throws Throwable {

		log.debug(Arrays.toString(pjp.getArgs()));

		// long startTime = System.currentTimeMillis();

		Object result = pjp.proceed();
		// long endTime = System.currentTimeMillis();

		// log.debug(pjp.getSignature().getName() + " 실행시간" + (endTime - startTime));
		return result;
	}

	@After("execution(* com.singer.controller.*Controller.show*(..))")
	public void aopShow(JoinPoint pjp) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		Signature signature = pjp.getSignature();
		String method = signature.getName().substring(4);

		InputQueryUtil queryUtil = new InputQueryUtil("log_click_board");
		queryUtil.add(DateUtil.getTodayTime());
		queryUtil.add(userid);
		queryUtil.add(method);
		queryUtil.add("0");

		producer.send(queryUtil.getQuery());

		redisDao.hmSetIncre(DateUtil.getToday(), method);

	}
}
