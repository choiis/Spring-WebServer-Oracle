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

import com.singer.service.SF02Service;
import com.singer.vo.SF02Vo;

@Controller("sF02Controller")
public class SF02Controller extends BaseController {

	private final Log log = LogFactory.getLog(SF02Controller.class);

	@Resource(name = "sf02Service")
	private SF02Service sf02Service;

	@ResponseBody
	@RequestMapping(value = "/sf02/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF02Vo> selectSF02Vo(@ModelAttribute SF02Vo sf02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf02 get");

		String userid = getSessionId(request);
		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		sf02Vo.setList(list);

		log.debug("exit sf02 get");
		return new ResponseEntity<SF02Vo>(sf02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf02", method = RequestMethod.POST)
	public ResponseEntity<SF02Vo> insertSF02Vo(@RequestBody SF02Vo sf02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf02 post");

		String userid = getSessionId(request);
		sf02Service.insertSF02Vo(sf02Vo, userid);

		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		sf02Vo.setList(list);

		log.debug("exit sf02 post");
		return new ResponseEntity<SF02Vo>(sf02Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sf02/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SF02Vo> deleteSF02Vo(@ModelAttribute SF02Vo sf02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf02 delete");
		String userid = getSessionId(request);

		sf02Service.deleteSF02Vo(sf02Vo, userid);

		sf02Vo.setNowPage(1);
		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		sf02Vo.setList(list);

		log.debug("exit sf02 delete");
		return new ResponseEntity<SF02Vo>(sf02Vo, HttpStatus.OK);
	}

}
