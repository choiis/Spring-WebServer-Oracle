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
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.CommDao;
import com.singer.exception.ClientException;
import com.singer.kafka.Producer;
import com.singer.redis.RedisDao;
import com.singer.util.InputQueryUtil;
import com.singer.vo.BoardVo;
import com.singer.vo.CommVo;
import com.singer.vo.ReplyVo;

@Component
@Aspect
public class AopAdvice {

	private final Log log = LogFactory.getLog(AopAdvice.class);

	@Resource(name = "redisDao")
	private RedisDao redisDao;

	@Resource(name = "commDao")
	private CommDao commDao;

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

	@Before("execution(* com.singer.service.*Impl.delete*(..))")
	public void deleteCheck(JoinPoint pjp) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Signature signature = pjp.getSignature();
		String userid = null;
		CommVo commVo = new CommVo();
		int seq = 0;

		for (int i = 0; i < pjp.getArgs().length; i++) {
			Object obj = pjp.getArgs()[i];
			if (obj instanceof BoardVo) {
				BoardVo boardVo = (BoardVo) obj;
				commVo.setSeq(boardVo.getSeq());
				seq = boardVo.getSeq();
			} else if (obj instanceof ReplyVo) {
				ReplyVo replyVo = (ReplyVo) obj;
				commVo.setSeq(replyVo.getSeq());
				seq = replyVo.getSeq();
			} else if (obj instanceof String) {
				userid = (String) obj;
			}
		}

		String method = signature.getName().substring(6, 10);
		log.debug("delete method : " + method);
		commVo.setTableName(method);
		if (userid == null) {
			HttpSession session = request.getSession();
			userid = (String) session.getAttribute("userid");
		}

		if (seq > 0) {
			String createUser = commDao.checkCreateUser(commVo);
			log.debug("session user " + userid);
			log.debug("create user " + createUser);
			if (!CommonUtil.isNull(createUser)) {
				if (!createUser.equals(userid)) {
					throw new ClientException(HttpStatus.FORBIDDEN);
				}
			}
		}

	}
}
