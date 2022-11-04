package com.singer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.singer.common.Constants.USER_CODE;
import com.singer.vo.CommVo;

public class BaseController {

    protected String getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object sessionId = session.getAttribute("userid");
        if (sessionId != null) {
            return (String) sessionId;
        } else {
            return null;
        }
    }

    protected String getUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        if (username != null) {
            return (String) username;
        } else {
            return null;
        }
    }

    protected USER_CODE getUsertype(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object usertype = session.getAttribute("usertype");
        if (usertype != null) {
            return (USER_CODE) usertype;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected List<CommVo> getMenuList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object menuList = session.getAttribute("menuList");
        if (menuList != null) {
            return (List<CommVo>) menuList;
        } else {
            return null;
        }
    }

}
