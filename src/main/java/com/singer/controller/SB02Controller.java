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

import com.singer.common.CommonUtil;
import com.singer.service.SB02Service;
import com.singer.vo.SB02Vo;

@Controller("sB02Controller")
public class SB02Controller {

	private final Log log = LogFactory.getLog(SB02Controller.class);

	@Resource(name = "sb02Service")
	private SB02Service sb02Service;

	@ResponseBody
	@RequestMapping(value = "/sb02/{seq01}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SB02Vo> selectSB02Vo(@ModelAttribute SB02Vo sb02Vo, HttpSession session) throws Exception {
		log.debug("enter sb02show.do");
		log.debug("sb02Vo : " + sb02Vo);

		String userid = (String) session.getAttribute("userid");
		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		sb02Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sb02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sb02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sb02Vo.setTotCnt(0);
			sb02Vo.setNowPage(0);
		}

		log.debug("list : " + list);
		log.debug("exit sb02show.do");
		return new ResponseEntity<SB02Vo>(sb02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb02", method = RequestMethod.POST)
	public ResponseEntity<SB02Vo> insertSB02Vo(SB02Vo sb02Vo, HttpSession session) throws Exception {

		log.debug("enter sb02insert.do");
		log.debug("sb02Vo : " + sb02Vo);

		String userid = (String) session.getAttribute("userid");

		sb02Vo.setUserid(userid);

		sb02Service.insertSB02Vo(sb02Vo);

		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		sb02Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sb02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sb02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sb02Vo.setTotCnt(0);
			sb02Vo.setNowPage(0);
		}

		log.debug("sb02Vo : " + sb02Vo);
		log.debug("exit sb02insert.do");
		return new ResponseEntity<SB02Vo>(sb02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb02/{seq}/{seq01}", method = RequestMethod.DELETE)
	public ResponseEntity<SB02Vo> deleteSB02Vo(@ModelAttribute SB02Vo sb02Vo, HttpSession session) throws Exception {
		sb02Vo.setNowPage(1);
		log.debug("enter sb02delete.do");
		log.debug("sb02Vo : " + sb02Vo);

		sb02Service.deleteSB02Vo(sb02Vo);

		String userid = (String) session.getAttribute("userid");
		List<SB02Vo> list = sb02Service.selectSB02Vo(sb02Vo, userid);
		sb02Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sb02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sb02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sb02Vo.setTotCnt(0);
			sb02Vo.setNowPage(0);
		}

		log.debug("list : " + list);
		log.debug("exit sb02delete.do");
		return new ResponseEntity<SB02Vo>(sb02Vo, HttpStatus.OK);
	}
}
