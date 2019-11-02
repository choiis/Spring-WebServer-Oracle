package com.singer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.service.SM02Service;
import com.singer.vo.SM02Vo;

@Controller("sM02Controller")
public class SM02Controller {

	private final Log log = LogFactory.getLog(SM02Controller.class);

	@Resource(name = "sm02Service")
	private SM02Service sm02Service;

	@RequestMapping(value = "/sm02page", method = RequestMethod.GET)
	public ModelAndView showSM02(HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("/sm02view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sm02/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SM02Vo> toSelectSM02Vo(@ModelAttribute SM02Vo sm02Vo, HttpSession session) throws Exception {
		log.debug("enter sm02 get");

		String userid = (String) session.getAttribute("userid");
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo, userid);
		sm02Vo.setList(list);

		log.debug("exit sm02 get");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SM02Vo> deleteSM02Vo(@ModelAttribute SM02Vo sm02Vo, HttpSession session) throws Exception {
		log.debug("enter sm02 delete");
		String userid = (String) session.getAttribute("userid");

		sm02Service.deleteSM02Vo(sm02Vo, userid);

		sm02Vo.setNowPage(1);
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo, userid);
		sm02Vo.setList(list);

		log.debug("exit sm02 delete");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02", method = RequestMethod.POST)
	public ResponseEntity<SM02Vo> insertSM02Vo(SM02Vo sm02Vo, HttpSession session) throws Exception {
		log.debug("enter sm02 post");

		String userid = (String) session.getAttribute("userid");
		sm02Service.insertSM02Vo(sm02Vo, userid);

		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo, userid);
		sm02Vo.setList(list);

		log.debug("exit sm02 post");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.CREATED);
	}
}