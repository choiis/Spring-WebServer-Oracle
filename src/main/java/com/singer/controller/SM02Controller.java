package com.singer.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.service.SM02Service;
import com.singer.vo.SM02Vo;

@Controller("sM02Controller")
public class SM02Controller {

	private final Log log = LogFactory.getLog(SM02Controller.class);

	@Resource(name = "sm02Service")
	private SM02Service sm02Service;

	@RequestMapping(value = "/sm02.do", method = RequestMethod.GET)
	public ModelAndView toSM02Vo(@RequestParam(value = "idx", defaultValue = "0") int idx,
			@ModelAttribute("SM02Vo") SM02Vo sM02Vo, HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("/sm02view");
		log.debug("enter sm02.do");

		log.debug("exit sm02.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sm02show.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectSM02Vo(@RequestBody SM02Vo sm02Vo, HttpSession session) throws Exception {

		log.debug("enter sm02show.do");
		log.debug("sm02Vo : " + sm02Vo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		sm02Vo.setUserid((String) session.getAttribute("userid"));
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo);
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
		log.debug("exit sm02show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sm02delete.do", method = RequestMethod.POST)
	public HashMap<String, Object> toDeleteSM02Vo(@RequestParam(value = "idx", defaultValue = "0") int idx,
			@RequestBody SM02Vo sm02Vo, HttpSession session) throws Exception {

		log.debug("enter sm02delete.do");

		sm02Vo.setUserid((String) session.getAttribute("userid"));

		log.debug("sm02Vo : " + sm02Vo);
		sm02Service.deleteSM02Vo(sm02Vo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		sm02Vo.setNowPage(idx);
		sm02Vo.setUserid((String) session.getAttribute("userid"));
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo);
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
		log.debug("exit sm02delete.do");

		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sm02insert.do", method = RequestMethod.POST)
	public HashMap<String, Object> toInsertSM02Vo(@RequestBody SM02Vo sm02Vo, HttpSession session) throws Exception {

		log.debug("enter sm02insert.do");

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		sm02Vo.setUserid((String) session.getAttribute("userid"));

		log.debug("sm02Vo : " + sm02Vo);

		sm02Service.insertSM02Vo(sm02Vo);

		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo);
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
		log.debug("exit sm02delete.do");

		return hashmap;
	}
}
