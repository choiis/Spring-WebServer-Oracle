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
import com.singer.service.SB02Service;
import com.singer.vo.SB02Vo;

@Controller("sB02Controller")
public class SB02Controller {

	private final Log log = LogFactory.getLog(SB02Controller.class);

	@Resource(name = "sb02Service")
	private SB02Service sb02Service;

	@ResponseBody
	@RequestMapping(value = "/sb02show.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectSB02Vo(@RequestBody SB02Vo sb02Vo, HttpSession session) throws Exception {

		log.debug("enter sb02show.do");
		log.debug("sb02Vo : " + sb02Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		String userid = (String) session.getAttribute("userid");
		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
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
		log.debug("exit sb02show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sb02insert.do", method = RequestMethod.POST)
	public HashMap<String, Object> toInsertSB02Vo(@RequestBody SB02Vo sb02Vo, HttpSession session) throws Exception {

		log.debug("enter sb02insert.do");
		log.debug("sb02Vo : " + sb02Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		String userid = (String) session.getAttribute("userid");

		sb02Vo.setUserid(userid);

		sb02Service.insertSB02Vo(sb02Vo);

		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		hashmap.put("list", list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			hashmap.put("nowPage", list.get(0).getNowPage());
		} else {
			hashmap.put("size", 0);
			hashmap.put("nowPage", 0);
		}

		log.debug("sb02Vo : " + sb02Vo);
		log.debug("exit sb02insert.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sb02delete.do", method = RequestMethod.POST)
	public HashMap<String, Object> toDeleteSB02Vo(@RequestBody SB02Vo sb02Vo, HttpSession session) throws Exception {
		log.debug("enter sb02delete.do");
		log.debug("sb02Vo : " + sb02Vo);

		sb02Service.deleteSB02Vo(sb02Vo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		String userid = (String) session.getAttribute("userid");
		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		hashmap.put("list", list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}

		log.debug("list : " + list);
		log.debug("exit sb02delete.do");
		return hashmap;
	}
}
