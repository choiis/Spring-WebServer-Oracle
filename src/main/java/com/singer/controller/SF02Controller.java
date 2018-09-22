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
import com.singer.service.SF02Service;
import com.singer.vo.SF02Vo;

@Controller("sF02Controller")
public class SF02Controller {

	private final Log log = LogFactory.getLog(SF02Controller.class);

	@Resource(name = "sf02Service")
	private SF02Service sf02Service;

	@ResponseBody
	@RequestMapping(value = "/sf02show.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectSF02Vo(@RequestBody SF02Vo sf02Vo, HttpSession session) throws Exception {

		log.debug("enter sf02show.do");
		log.debug("sf02Vo : " + sf02Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		String userid = (String) session.getAttribute("userid");
		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		hashmap.put("list", list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}

		log.debug("list : " + list);
		log.debug("exit sf02show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sf02insert.do", method = RequestMethod.POST)
	public HashMap<String, Object> toInsertSF02Vo(@RequestBody SF02Vo sf02Vo, HttpSession session) throws Exception {

		log.debug("enter sf02insert.do");
		log.debug("sf02Vo : " + sf02Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		String userid = (String) session.getAttribute("userid");

		sf02Vo.setUserid(userid);

		sf02Service.insertSF02Vo(sf02Vo);

		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
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
		log.debug("exit sf02insert.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sf02delete.do", method = RequestMethod.POST)
	public HashMap<String, Object> toDeleteSF02Vo(@RequestBody SF02Vo sf02Vo, HttpSession session) throws Exception {
		log.debug("enter sf02delete.do");
		log.debug("sf02Vo : " + sf02Vo);

		sf02Service.deleteSF02Vo(sf02Vo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		String userid = (String) session.getAttribute("userid");
		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
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
		log.debug("exit sf02delete.do");
		return hashmap;
	}

}
