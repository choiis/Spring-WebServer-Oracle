package com.singer.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.service.SV02Service;
import com.singer.vo.SV02Vo;

@Controller("sv02Controller")
public class SV02Controller {
	private final Log log = LogFactory.getLog(SV02Controller.class);

	@Resource(name = "sv02Service")
	private SV02Service sv02Service;

	@ResponseBody
	@RequestMapping(value = "/sv03insert.do", method = RequestMethod.POST)
	public HashMap<String, Object> toInsertSV01Vo(@RequestBody SV02Vo sv02Vo, HttpSession session) throws Exception {
		log.debug("enter sv03insert.do");
		log.debug("sv01Vo : " + sv02Vo);

		String userid = (String) session.getAttribute("userid");
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		int cnt = sv02Service.insertSv03Vo(sv02Vo, userid);

		hashmap.put("response", cnt);
		log.debug("exit sv03insert.do");
		return hashmap;
	}
}
