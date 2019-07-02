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
	public ModelAndView showSM02(HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("/sm02view");
		log.debug("enter sm02.do");

		log.debug("exit sm02.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sm02show/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SM02Vo> toSelectSM02Vo(@ModelAttribute SM02Vo sm02Vo, HttpSession session) throws Exception {

		log.debug("enter sm02show");
		log.debug("sm02Vo : " + sm02Vo);

		sm02Vo.setUserid((String) session.getAttribute("userid"));
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo);
		sm02Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sm02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sm02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sm02Vo.setTotCnt(0);
			sm02Vo.setNowPage(0);
		}

		log.debug("list : " + list);
		log.debug("exit sm02show.do");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02delete/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SM02Vo> toDeleteSM02Vo(@ModelAttribute SM02Vo sm02Vo, HttpSession session) throws Exception {
		sm02Vo.setNowPage(1);
		log.debug("enter sm02delete.do");

		sm02Vo.setUserid((String) session.getAttribute("userid"));

		log.debug("sm02Vo : " + sm02Vo);
		sm02Service.deleteSM02Vo(sm02Vo);

		sm02Vo.setUserid((String) session.getAttribute("userid"));
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo);
		sm02Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sm02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sm02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sm02Vo.setTotCnt(0);
			sm02Vo.setNowPage(0);
		}

		log.debug("list : " + list);
		log.debug("exit sm02delete.do");

		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02insert.do", method = RequestMethod.POST)
	public ResponseEntity<SM02Vo> toInsertSM02Vo(SM02Vo sm02Vo, HttpSession session) throws Exception {

		log.debug("enter sm02insert.do");

		sm02Vo.setUserid((String) session.getAttribute("userid"));

		log.debug("sm02Vo : " + sm02Vo);

		sm02Service.insertSM02Vo(sm02Vo);

		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo);
		sm02Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sm02Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
			sm02Vo.setNowPage(list.get(0).getNowPage());
		} else {
			sm02Vo.setTotCnt(0);
			sm02Vo.setNowPage(0);
		}
		log.debug("list : " + list);
		log.debug("exit sm02delete.do");

		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.CREATED);
	}
}