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

		log.debug("SV04Vo : " + sv04Vo);

		String userid = (String) session.getAttribute("userid");
		List<SV04Vo> list = sv04Service.selectSV04Vo(sv04Vo, userid);
		sv04Vo.setList(list);
		log.debug("list : " + list);
		return new ResponseEntity<SV04Vo>(sv04Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv04", method = RequestMethod.POST)
	public ResponseEntity<SV04Vo> insertSV04Vo(SV04Vo sv04Vo, HttpSession session) throws Exception {

		log.debug("enter sf02insert.do");
		log.debug("SV04Vo : " + sv04Vo);

		String userid = (String) session.getAttribute("userid");

		sv04Vo.setUserid(userid);

		sv04Service.insertSV04Vo(sv04Vo);

		List<SV04Vo> list = sv04Service.selectSV04Vo(sv04Vo, userid);
		sv04Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sf02insert.do");
		return new ResponseEntity<SV04Vo>(sv04Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sv04/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SV04Vo> deleteSV04Vo(@ModelAttribute SV04Vo sv04Vo, HttpSession session) throws Exception {
		sv04Vo.setNowPage(1);
		log.debug("SV04Vo : " + sv04Vo);

		sv04Service.deleteSV04Vo(sv04Vo);

		String userid = (String) session.getAttribute("userid");
		List<SV04Vo> list = sv04Service.selectSV04Vo(sv04Vo, userid);
		sv04Vo.setList(list);
		log.debug("list : " + list);
		return new ResponseEntity<SV04Vo>(sv04Vo, HttpStatus.OK);
	}
}
