package com.singer.infrastructure.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.dao.CommDao;
import com.singer.domain.entity.CommVo;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Component
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {


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
        // �꽭�뀡 留뚮즺 耳��씠�뒪
        if ("/index".equals(uri) || "/sessionExpire".equals(uri) || "/".equals(uri) || "/forwardError".equals(uri)
            || "/error".equals(uri)) {
            return true;
        }

        if (StringUtils.isEmpty(userid)) { // �꽭�뀡 �뾾�쓬
            log.debug("login need");
            response.sendRedirect("/sessionNotExist?redirect_uri=" + uri);
            return false;
        }

        USER_CODE usertype = (USER_CODE) session.getAttribute("usertype");
        // 珥덇린 濡쒓렇�씤 耳��씠�뒪 �븘�땲硫�
        if (!"/main".equals(uri)) {
            List<CommVo> list = commDao.selectAllMenu();

            // 硫붾돱 沅뚰븳 �젣�뼱
            if (list.stream().anyMatch(s -> StringUtils.equals(uri, s.getMenuurl()))) {
                Stream<CommVo> stream = list.stream().filter(s -> StringUtils.equals(uri, s.getMenuurl()));
                for (Iterator<CommVo> i = stream.iterator(); i.hasNext();) {
                    CommVo comm = i.next();
                    if (usertype.compareTo(comm.getAuthlevel()) > 0) {// 沅뚰븳 �뾾�뒗 硫붾돱 uri濡� �젒�냽�떆
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
