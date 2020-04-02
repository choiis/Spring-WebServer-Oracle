package com.singer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.service.SB02Service;
import com.singer.vo.SB02Vo;

@Controller("sB02Controller")
public class SB02Controller extends BaseController {

	private final Log log = LogFactory.getLog(SB02Controller.class);

	@Resource(name = "sb02Service")
	private SB02Service sb02Service;

	@ResponseBody
	@RequestMapping(value = "/sb02/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SB02Vo> selectSB02Vo(@ModelAttribute SB02Vo sb02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sb02 get");

		String userid = getSessionId(request);
		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		sb02Vo.setList(list);

		log.debug("exit sb02 get");
		return new ResponseEntity<SB02Vo>(sb02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb02", method = RequestMethod.POST)
	public ResponseEntity<SB02Vo> insertSB02Vo(@RequestBody SB02Vo sb02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sb02 post");

		String userid = getSessionId(request);
		sb02Service.insertSB02Vo(sb02Vo, userid);

		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		sb02Vo.setList(list);

		log.debug("exit sb02 post");
		return new ResponseEntity<SB02Vo>(sb02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb02/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SB02Vo> deleteSB02Vo(@ModelAttribute SB02Vo sb02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sb02 delete");
		String userid = getSessionId(request);

		sb02Service.deleteSB02Vo(sb02Vo, userid);

		sb02Vo.setNowPage(1);
		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		sb02Vo.setList(list);

		log.debug("exit sb02 delete");
		return new ResponseEntity<SB02Vo>(sb02Vo, HttpStatus.OK);
	}
}
