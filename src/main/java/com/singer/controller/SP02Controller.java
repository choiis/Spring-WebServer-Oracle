package com.singer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	public ResponseEntity<SP02Vo> selectSP02Vo(SP02Vo sp02Vo, HttpSession session) throws Exception {

		log.debug("enter sp02show.do");
		log.debug("SP02Vo : " + sp02Vo);

		String userid = (String) session.getAttribute("userid");
		List<SP02Vo> list = sp02Service.selectSP02Vo(sp02Vo, userid);
		sp02Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sp02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sp02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sp02Vo.setTotCnt(0);
			sp02Vo.setNowPage(0);
		}

		log.debug("list : " + list);
		log.debug("exit sp02show.do");
		return new ResponseEntity<SP02Vo>(sp02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp02insert.do", method = RequestMethod.POST)
	public ResponseEntity<SP02Vo> insertSP02Vo(SP02Vo sp02Vo, HttpSession session) throws Exception {

		log.debug("enter sp02insert.do");
		log.debug("SP02Vo : " + sp02Vo);
		String userid = (String) session.getAttribute("userid");

		sp02Vo.setUserid(userid);

		sp02Service.insertSP02Vo(sp02Vo);

		List<SP02Vo> list = sp02Service.selectSP02Vo(sp02Vo, userid);
		sp02Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sp02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sp02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sp02Vo.setTotCnt(0);
			sp02Vo.setNowPage(0);
		}

		log.debug("SP02Vo : " + sp02Vo);
		log.debug("exit sp02insert.do");
		return new ResponseEntity<SP02Vo>(sp02Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sp02delete.do", method = RequestMethod.POST)
	public ResponseEntity<SP02Vo> deleteSP02Vo(SP02Vo sp02Vo, HttpSession session) throws Exception {
		log.debug("enter sp02delete.do");
		log.debug("SP02Vo : " + sp02Vo);

		sp02Service.deleteSP02Vo(sp02Vo);

		String userid = (String) session.getAttribute("userid");
		List<SP02Vo> list = sp02Service.selectSP02Vo(sp02Vo, userid);
		sp02Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sp02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sp02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sp02Vo.setTotCnt(0);
			sp02Vo.setNowPage(0);
		}
		log.debug("list : " + list);
		log.debug("exit sp02delete.do");
		return new ResponseEntity<SP02Vo>(sp02Vo, HttpStatus.OK);
	}
}
