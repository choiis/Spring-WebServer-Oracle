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

import com.singer.common.CommonUtil;
import com.singer.service.SP02Service;
import com.singer.vo.SP02Vo;

@Controller("sp02Controller")
public class SP02Controller {

	private final Log log = LogFactory.getLog(SP02Controller.class);

	@Resource(name = "sp02Service")
	private SP02Service sp02Service;

	@ResponseBody
	@RequestMapping(value = "/sp02show.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectSP02Vo(@RequestBody SP02Vo SP02Vo, HttpSession session) throws Exception {

		log.debug("enter sp02show.do");
		log.debug("SP02Vo : " + SP02Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		String userid = (String) session.getAttribute("userid");
		List<SP02Vo> list = sp02Service.selectSP02Vo(SP02Vo, userid);
		hashmap.put("list", list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			hashmap.put("nowPage", list.get(0).getNowPage());
		} else {
			hashmap.put("size", 0);
			hashmap.put("nowPage", 0);
		}

		log.debug("list : " + list);
		log.debug("exit sp02show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp02insert.do", method = RequestMethod.POST)
	public HashMap<String, Object> toInsertSP02Vo(@RequestBody SP02Vo SP02Vo, HttpSession session) throws Exception {

		log.debug("enter sp02insert.do");
		log.debug("SP02Vo : " + SP02Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		String userid = (String) session.getAttribute("userid");

		SP02Vo.setUserid(userid);

		sp02Service.insertSP02Vo(SP02Vo);

		List<SP02Vo> list = sp02Service.selectSP02Vo(SP02Vo, userid);
		hashmap.put("list", list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			hashmap.put("nowPage", list.get(0).getNowPage());
		} else {
			hashmap.put("size", 0);
			hashmap.put("nowPage", 0);
		}

		log.debug("SP02Vo : " + SP02Vo);
		log.debug("exit sp02insert.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp02delete.do", method = RequestMethod.POST)
	public HashMap<String, Object> toDeleteSP02Vo(@RequestBody SP02Vo SP02Vo, HttpSession session) throws Exception {
		log.debug("enter sp02delete.do");
		log.debug("SP02Vo : " + SP02Vo);

		sp02Service.deleteSP02Vo(SP02Vo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		String userid = (String) session.getAttribute("userid");
		List<SP02Vo> list = sp02Service.selectSP02Vo(SP02Vo, userid);
		hashmap.put("list", list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}

		log.debug("list : " + list);
		log.debug("exit sp02delete.do");
		return hashmap;
	}
}
