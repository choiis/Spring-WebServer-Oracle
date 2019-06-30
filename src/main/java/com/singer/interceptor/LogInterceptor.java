package com.singer.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.singer.common.DateUtil;
import com.singer.dao.CommDao;

public class LogInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(LogInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		log.debug("===================== postHandle =========================");
		String uri = request.getRequestURI();
		if (!"/commCode.do".equals(uri) && !"/commMenu.do".equals(uri)
				&& !"/commCodeGrp.do".equals(uri) && !"/sessionExpire.do".equals(uri)
				&& !"/gomain.do".equals(uri)) {
			log.debug("addr  : " + request.getRemoteAddr());
			log.debug("host  : " + request.getRemoteHost());
			log.debug("port  : " + request.getRemotePort());
			log.debug("method  : " + request.getMethod());
			log.debug("uri : " + uri);
			log.debug("protocol : " + request.getProtocol());
			log.debug("servletpath : " + request.getServletPath());
			log.debug("userid : " + userid);
			log.debug("time" + DateUtil.getTodayTime());

		}
	}

	@Resource(name = "commDao")
	private CommDao commDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}
}
