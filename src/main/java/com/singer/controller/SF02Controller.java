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
import com.singer.service.SF02Service;
import com.singer.vo.SF02Vo;

@Controller("sF02Controller")
public class SF02Controller {

	private final Log log = LogFactory.getLog(SF02Controller.class);

	@Resource(name = "sf02Service")
	private SF02Service sf02Service;

	@ResponseBody
	@RequestMapping(value = "/sf02show/{seq01}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF02Vo> selectSF02Vo(@ModelAttribute SF02Vo sf02Vo, HttpSession session) throws Exception {

		log.debug("enter sf02show.do");
		log.debug("sf02Vo : " + sf02Vo);

		String userid = (String) session.getAttribute("userid");
		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		sf02Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sf02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			sf02Vo.setTotCnt(0);
		}

		log.debug("list : " + list);
		log.debug("exit sf02show.do");
		return new ResponseEntity<SF02Vo>(sf02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf02insert.do", method = RequestMethod.POST)
	public ResponseEntity<SF02Vo> insertSF02Vo(SF02Vo sf02Vo, HttpSession session) throws Exception {

		log.debug("enter sf02insert.do");
		log.debug("sf02Vo : " + sf02Vo);

		String userid = (String) session.getAttribute("userid");

		sf02Vo.setUserid(userid);

		sf02Service.insertSF02Vo(sf02Vo);

		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		sf02Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sf02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sf02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sf02Vo.setTotCnt(0);
			sf02Vo.setNowPage(0);
		}
		log.debug("list : " + list);
		log.debug("exit sf02insert.do");
		return new ResponseEntity<SF02Vo>(sf02Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sf02delete/{seq}/{seq01}", method = RequestMethod.DELETE)
	public ResponseEntity<SF02Vo> deleteSF02Vo(@ModelAttribute SF02Vo sf02Vo, HttpSession session) throws Exception {
		sf02Vo.setNowPage(1);
		log.debug("enter sf02delete.do");
		log.debug("sf02Vo : " + sf02Vo);

		sf02Service.deleteSF02Vo(sf02Vo);

		String userid = (String) session.getAttribute("userid");
		List<SF02Vo> list = sf02Service.selectSF02Vo(sf02Vo, userid);
		sf02Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sf02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sf02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sf02Vo.setTotCnt(0);
			sf02Vo.setNowPage(0);
		}

		log.debug("list : " + list);
		log.debug("exit sf02delete.do");
		return new ResponseEntity<SF02Vo>(sf02Vo, HttpStatus.OK);
	}

}
