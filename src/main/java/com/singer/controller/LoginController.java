package com.singer.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.service.SM01Service;
import com.singer.vo.SM01Vo;

@Controller("loginController")
public class LoginController {

	private final Log log = LogFactory.getLog(LoginController.class);

	@Resource(name = "sm01Service")
	private SM01Service sm01Service;

	@RequestMapping(value = "/logout.do")
	public ModelAndView logout(HttpSession session) throws Exception {
		log.debug("enter logout.do");

		ModelAndView model = new ModelAndView("/index");

		session.removeAttribute("userid");
		session.removeAttribute("adminyn");
		session.removeAttribute("username");
		session.removeAttribute("brth");
		session.removeAttribute("grade");
		session.removeAttribute("regdate");
		session.removeAttribute("phone");
		session.removeAttribute("email");
		session.removeAttribute("usertype");

		model.addObject("message", "로그아웃!");

		log.debug("exit logout.do");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public HashMap<String, Object> login(SM01Vo sm01Vo, HttpSession session) throws Exception {
		log.debug("enter login.do");
		log.debug("sm01Vo : " + sm01Vo);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		String userId = sm01Vo.getUserid();

		SM01Vo one = sm01Service.selectOneSM01Vo(sm01Vo);
		if (CommonUtil.isNull(one)) { // 로그인 실패

			hashMap.put("code", Constants.ERROR_LOGIN_FAIL);
			log.debug("code : " + Constants.ERROR_LOGIN_FAIL);
			log.debug("exit login.do");

			return hashMap;
		} else {

			session.setAttribute("userid", userId);
			session.setAttribute("adminyn", one.getAdminyn());
			session.setAttribute("username", one.getUsername());
			session.setAttribute("brth", one.getBrth());
			session.setAttribute("grade", one.getGrade());
			session.setAttribute("regdate", one.getRegdate());
			session.setAttribute("phone", one.getPhone());
			session.setAttribute("email", one.getEmail());
			session.setAttribute("usertype", one.getUsertype());

			hashMap.put("code", Constants.SUCCESS_CODE);

			log.debug("code : " + Constants.SUCCESS_CODE);
			log.debug("exit login.do");
			return hashMap;
		}

	}

	@RequestMapping(value = "/gomain.do", method = RequestMethod.GET)
	public ModelAndView goMain() throws Exception {
		ModelAndView model = new ModelAndView("/main");

		log.debug("enter gomain.do");

		log.debug("exit gomain.do");

		return model;
	}
}
