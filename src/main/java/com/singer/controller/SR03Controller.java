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

import com.singer.service.SR03Service;
import com.singer.vo.SR03Vo;

@Controller("sr03Controller")
public class SR03Controller {

	private final Log log = LogFactory.getLog(SR03Controller.class);

	@Resource(name = "sr03Service")
	private SR03Service sr03Service;

	@ResponseBody
	@RequestMapping(value = "/sr03/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SR03Vo> selectsr03Vo(@ModelAttribute SR03Vo sr03Vo, HttpSession session) throws Exception {

		log.debug("sr03Vo : " + sr03Vo);

		String userid = (String) session.getAttribute("userid");
		List<SR03Vo> list = sr03Service.selectSR03Vo(sr03Vo, userid);
		sr03Vo.setList(list);
		log.debug("list : " + list);
		return new ResponseEntity<SR03Vo>(sr03Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr03", method = RequestMethod.POST)
	public ResponseEntity<SR03Vo> insertsr03Vo(SR03Vo sr03Vo, HttpSession session) throws Exception {

		log.debug("enter sr03insert.do");
		log.debug("sr03Vo : " + sr03Vo);

		String userid = (String) session.getAttribute("userid");

		sr03Vo.setUserid(userid);

		sr03Service.insertSR03Vo(sr03Vo);

		List<SR03Vo> list = sr03Service.selectSR03Vo(sr03Vo, userid);
		sr03Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sr03insert.do");
		return new ResponseEntity<SR03Vo>(sr03Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sr03/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SR03Vo> deletesr03Vo(@ModelAttribute SR03Vo sr03Vo, HttpSession session) throws Exception {
		sr03Vo.setNowPage(1);
		log.debug("sr03Vo : " + sr03Vo);

		sr03Service.deleteSR03Vo(sr03Vo);

		String userid = (String) session.getAttribute("userid");
		List<SR03Vo> list = sr03Service.selectSR03Vo(sr03Vo, userid);
		sr03Vo.setList(list);
		log.debug("list : " + list);
		return new ResponseEntity<SR03Vo>(sr03Vo, HttpStatus.OK);
	}
}
