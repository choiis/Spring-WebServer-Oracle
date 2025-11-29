package com.singer.application.controller;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.entity.CommEntity;
import com.singer.infrastructure.security.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class BaseController {

    /**
     * 기존에는 HttpSession 에 저장된 userid 를 사용했으나,
     * 이제는 Spring Security Authentication 의 username 을 사용한다.
     */
    protected String getSessionId(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * USER_CODE (유저등급) 조회.
     * SecurityUser 에 usertype 이 들어있다고 가정한다.
     */
    protected USER_CODE getUsertype(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            return securityUser.getUsertype();
        }

        return null;
    }

    /**
     * 세션 기반 메뉴 관리 대신, 필요 시 별도 API 로 구성하는 것을 권장.
     * 현재는 더 이상 세션을 사용하지 않으므로 null 을 반환한다.
     */
    @SuppressWarnings("unchecked")
    protected List<CommEntity> getMenuList(HttpServletRequest request) {
        return null;
    }

}
