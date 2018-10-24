package com.singer.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.singer.dao.CommDao;
import com.singer.vo.CommVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(LoginInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		log.debug("===================== postHandle =========================");

		if (userid == null) {
			log.debug("login need");
			response.sendRedirect("/common");
		} else {

		}
	}

	@Resource(name = "commDao")
	private CommDao commDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		String uri = request.getRequestURI();
		String usertype = (String) session.getAttribute("usertype");
		log.debug("===================== preHandle =========================");

		if (userid == null) { // 세션 없음
			log.debug("login need");
			response.sendRedirect("/common");
			return false;
		}

		// 초기 로그인 케이스 아니면
		if (!"/common/gomain.do".equals(uri)) {
			CommVo commVo = new CommVo();
			commVo.setMenuurl(uri);
			List<CommVo> list = commDao.checkMenuAuth(commVo);
			// 메뉴 권한 제어
			if (list.size() == 0) {
				return true;
			} else {
				int comp = usertype.compareTo(list.get(0).getAuthlevel());
				if (comp > 0) { // 권한 없는 메뉴 uri로 접속시
					log.debug("use denied");
					response.sendRedirect("/common");
					return false;
				}

			}
		}

		return true;
	}
}