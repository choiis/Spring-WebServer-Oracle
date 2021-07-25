package com.singer.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.service.SV02Service;
import com.singer.vo.SV02Vo;

@RequestMapping("/sv01")
@Controller
public class SV02Controller extends BaseController {
	private final Log log = LogFactory.getLog(SV02Controller.class);

	@Inject
	private SV02Service sv02Service;

	@ResponseBody
	@RequestMapping(value = "/sv03", method = RequestMethod.POST)
	public ResponseEntity<SV02Vo> insertSV03Vo(@RequestBody SV02Vo sv02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sv03 post");

		String userid = getSessionId(request);
		sv02Service.insertSv03Vo(sv02Vo, userid);

		log.debug("exit sv03 post");
		return new ResponseEntity<SV02Vo>(sv02Vo, HttpStatus.CREATED);
	}
}
