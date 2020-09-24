package com.singer.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.common.Constants.BROWSER_CODE;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.exception.ExceptionMsg;
import com.singer.kafka.Producer;
import com.singer.service.CommService;
import com.singer.service.SM01Service;
import com.singer.util.InputQueryUtil;
import com.singer.vo.CommVo;
import com.singer.vo.SM01Vo;

@Controller("loginController")
public class LoginController {

	private final Log log = LogFactory.getLog(LoginController.class);

	@Resource(name = "sm01Service")
	private SM01Service sm01Service;

	@Inject
	private Producer producer;

	@Resource(name = "commService")
	private CommService commService;

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session) throws Exception {

		ModelAndView model = new ModelAndView("/index");

		session.removeAttribute("userid");
		session.removeAttribute("username");
		session.removeAttribute("grade");
		session.removeAttribute("email");
		session.removeAttribute("usertype");
		session.removeAttribute("menuList");

		model.addObject("message", "로그아웃!");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HashMap<String, Object> login(@RequestBody SM01Vo sm01Vo, HttpSession session, HttpServletRequest request)
			throws Exception {
		log.debug("sm01Vo : " + sm01Vo);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		String userId = sm01Vo.getUserid();

		SM01Vo one = sm01Service.login(sm01Vo);
		if (CommonUtil.isNull(one)) { // 계정없음

			hashMap.put("code", RESULT_CODE.FAIL.getValue());
			hashMap.put("msg", ExceptionMsg.EXT_MSG_LOGIN_1);
			return hashMap;
		} else if (!one.getPasswd().equals(sm01Vo.getPasswd())) { // 비밀번호틀림

			hashMap.put("code", RESULT_CODE.FAIL.getValue());
			hashMap.put("msg", ExceptionMsg.EXT_MSG_LOGIN_2);
			return hashMap;
		} else {

			session.setAttribute("userid", userId);
			session.setAttribute("username", one.getUsername());
			session.setAttribute("grade", one.getGrade());
			session.setAttribute("regdate", one.getRegdate());
			session.setAttribute("email", one.getEmail());
			session.setAttribute("usertype", one.getUsertype());

			List<CommVo> menuList = commService.selectMenu(one.getUsertype());
			session.setAttribute("menuList", menuList);

			BROWSER_CODE brower = CommonUtil.getBrower(request);
			log.info("login brower " + brower);

			InputQueryUtil queryUtil = new InputQueryUtil("log_login");
			queryUtil.add(userId);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add(brower.toString());
			queryUtil.add(sm01Vo.getDevice());
			queryUtil.add(CommonUtil.getIp(request));

			producer.send(queryUtil.getQuery());

			hashMap.put("code", RESULT_CODE.SUCCESS.getValue());

			log.debug("code : " + RESULT_CODE.SUCCESS.getValue());
			return hashMap;
		}

	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView goMain() throws Exception {
		ModelAndView model = new ModelAndView("/main");

		return model;
	}

	@RequestMapping(value = "/sessionNotExist")
	public ModelAndView redirect_uri(HttpServletRequest request) throws Exception {
		String redirect = request.getParameter("redirect_uri");
		ModelAndView model = new ModelAndView("/index");
		model.addObject("redirect_uri", redirect);
		return model;
	}
}
