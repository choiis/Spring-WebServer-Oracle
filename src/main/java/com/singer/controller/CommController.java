package com.singer.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.service.CommService;
import com.singer.vo.CommVo;

@Controller("commController")
public class CommController {
	private final Log log = LogFactory.getLog(SB01Controller.class);

	@Resource(name = "commService")
	private CommService commService;

	@ResponseBody
	@RequestMapping(value = "/commCode.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectCommCode(@RequestBody CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter commCode.do");
		log.debug("CommVo : " + commVo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		List<CommVo> list = commService.selectCode(commVo);
		hashmap.put("commList", list);

		log.debug("exit sb01show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/commMenu.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectMenu(@RequestBody CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter commMenu.do");
		log.debug("CommVo : " + commVo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		String authlevel = (String) session.getAttribute("usertype");
		List<CommVo> list = commService.selectMenu(commVo, authlevel);
		hashmap.put("commList", list);

		log.debug("exit commMenu.do");
		return hashmap;
	}
}
