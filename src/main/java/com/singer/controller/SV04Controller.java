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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.service.SV04Service;
import com.singer.vo.SV04Vo;

@Controller("sV04Controller")
public class SV04Controller {

	private final Log log = LogFactory.getLog(SV04Controller.class);

	@Resource(name = "sv04Service")
	private SV04Service sv04Service;

	@ResponseBody
	@RequestMapping(value = "/sv04/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SV04Vo> selectSV04Vo(@ModelAttribute SV04Vo sv04Vo, HttpSession session) throws Exception {
		log.debug("enter sv04 get");

		String userid = (String) session.getAttribute("userid");
		List<SV04Vo> list = sv04Service.selectSV04Vo(sv04Vo, userid);
		sv04Vo.setList(list);

		log.debug("exit sv04 get");
		return new ResponseEntity<SV04Vo>(sv04Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv04", method = RequestMethod.POST)
	public ResponseEntity<SV04Vo> insertSV04Vo(@RequestBody SV04Vo sv04Vo, HttpSession session) throws Exception {
		log.debug("enter sv04 post");

		String userid = (String) session.getAttribute("userid");

		sv04Service.insertSV04Vo(sv04Vo, userid);

		List<SV04Vo> list = sv04Service.selectSV04Vo(sv04Vo, userid);
		sv04Vo.setList(list);

		log.debug("exit sv04 post");
		return new ResponseEntity<SV04Vo>(sv04Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sv04/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SV04Vo> deleteSV04Vo(@ModelAttribute SV04Vo sv04Vo, HttpSession session) throws Exception {
		log.debug("enter sv04 delete");
		String userid = (String) session.getAttribute("userid");

		sv04Service.deleteSV04Vo(sv04Vo, userid);

		sv04Vo.setNowPage(1);
		List<SV04Vo> list = sv04Service.selectSV04Vo(sv04Vo, userid);
		sv04Vo.setList(list);

		log.debug("exit sv04 delete");
		return new ResponseEntity<SV04Vo>(sv04Vo, HttpStatus.OK);
	}
}
