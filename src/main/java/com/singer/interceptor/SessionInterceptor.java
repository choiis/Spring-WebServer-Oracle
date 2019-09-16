package com.singer.interceptor;

import java.util.Iterator;
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
import com.singer.util.MenuListStruct;
import com.singer.vo.CommVo;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(SessionInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// log.debug("===================== postHandle
		// =========================");
	}

	@Resource(name = "commDao")
	private CommDao commDao;

	@Resource
	private MenuListStruct menuListStruct;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		String uri = request.getRequestURI();
		String usertype = (String) session.getAttribute("usertype");
		log.debug("===================== preHandle =========================");
		// 세션 만료 케이스
		if ("/sessionExpire".equals(uri) || "/".equals(uri) || "/forwardError".equals(uri)
				|| "/forwardError".equals(uri) || "/error".equals(uri)) {
			return true;
		}

		if (userid == null) { // 세션 없음
			log.debug("login need");
			response.sendRedirect("/sessionExpire");
			return false;
		}

		// 초기 로그인 케이스 아니면
		if (!"/main".equals(uri)) {
			CommVo commVo = new CommVo();
			commVo.setMenuurl(uri);
			List<CommVo> list = menuListStruct.getAllMenuList();

			// 메뉴 권한 제어
			if (list.stream().anyMatch(s -> uri.equals(s.getMenuurl()))) {
				Iterator<CommVo> iter = list.stream().filter(s -> uri.equals(s.getMenuurl())).iterator();
				while (iter.hasNext()) {
					CommVo comm = iter.next();
					int comp = usertype.compareTo(comm.getAuthlevel());
					if (comp > 0) { // 권한 없는 메뉴 uri로 접속시
						log.debug("use denied");
						response.sendRedirect("/authExpire");
						return false;
					}
				}
			} else {
				return true;
			}
		}

		return true;
	}
}
