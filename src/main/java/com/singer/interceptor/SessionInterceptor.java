package com.singer.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.singer.common.Constants.USER_CODE;
import com.singer.dao.CommDao;
import com.singer.vo.CommVo;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private static final Log log = LogFactory.getLog(SessionInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        // log.debug("===================== postHandle
        // =========================");
    }

    @Autowired
    private CommDao commDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        String uri = request.getRequestURI();
        // 세션 만료 케이스
        if ("/index".equals(uri) || "/sessionExpire".equals(uri) || "/".equals(uri) || "/forwardError".equals(uri)
            || "/error".equals(uri)) {
            return true;
        }

        if (StringUtils.isEmpty(userid)) { // 세션 없음
            log.debug("login need");
            response.sendRedirect("/sessionNotExist?redirect_uri=" + uri);
            return false;
        }

        USER_CODE usertype = (USER_CODE) session.getAttribute("usertype");
        // 초기 로그인 케이스 아니면
        if (!"/main".equals(uri)) {
            List<CommVo> list = commDao.selectAllMenu();

            // 메뉴 권한 제어
            if (list.stream().anyMatch(s -> StringUtils.equals(uri, s.getMenuurl()))) {
                Stream<CommVo> stream = list.stream().filter(s -> StringUtils.equals(uri, s.getMenuurl()));
                for (Iterator<CommVo> i = stream.iterator(); i.hasNext();) {
                    CommVo comm = i.next();
                    if (usertype.compareTo(comm.getAuthlevel()) > 0) {// 권한 없는 메뉴 uri로 접속시
                        log.debug("use denied");
                        // 403 forbidden
                        response.sendRedirect("/forbiddenPage");
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
