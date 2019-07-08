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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.service.SV01Service;
import com.singer.vo.SV01Vo;

@Controller("sv01Controller")
public class SV01Controller {

	private final Log log = LogFactory.getLog(SV01Controller.class);

	@Resource(name = "sv01Service")
	private SV01Service sv01Service;

	@RequestMapping(value = "/sv01.do", method = RequestMethod.GET)
	public ModelAndView showSV01() throws Exception {
		ModelAndView model = new ModelAndView("/sv01view");
		log.debug("enter sv01.do");

		log.debug("exit sv01.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sv01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectDetailSV01Vo(@ModelAttribute SV01Vo sv01Vo) throws Exception {
		log.debug("enter sv01show_detail.do");

		ModelAndView model = new ModelAndView("/sv01view_detail");

		model.addObject("seq", sv01Vo.getSeq());

		log.debug("exit sv01show_detail.do");
		return model;
	}

	@RequestMapping(value = "sv01write.do", method = RequestMethod.GET)
	public ModelAndView writeSV01() throws Exception {
		ModelAndView model = new ModelAndView("/sv01insert");
		log.debug("enter sv01write.do");

		log.debug("exit sv01write.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SV01Vo> selectSV01Vo(@ModelAttribute SV01Vo sv01Vo, HttpSession session) throws Exception {

		log.debug("enter sv01show.do");
		log.debug("SV01Vo : " + sv01Vo);

		List<SV01Vo> list = sv01Service.selectSV01Vo(sv01Vo);
		sv01Vo.setList(list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sv01Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			sv01Vo.setTotCnt(0);
		}
		log.debug("list : " + list);
		log.debug("exit sv01show.do");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01", method = RequestMethod.POST)
	public ResponseEntity<SV01Vo> insertSV01Vo(@RequestBody SV01Vo sv01Vo, HttpSession session) throws Exception {
		log.debug("enter sv01insert.do");
		log.debug("sv01Vo : " + sv01Vo);

		String userid = (String) session.getAttribute("userid");

		sv01Service.insertSV01Vo(sv01Vo, userid);

		log.debug("exit sv01insert.do");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01One/{seq}/{recall}", method = RequestMethod.GET)
	public ResponseEntity<SV01Vo> selectOneSV01Vo(@ModelAttribute SV01Vo sv01Vo, HttpSession session) throws Exception {
		log.debug("enter sv01selectOne.do");
		log.debug("sv01Vo : " + sv01Vo);
		String userid = (String) session.getAttribute("userid");

		sv01Vo = sv01Service.selectOneSV01Vo(sv01Vo, userid);
		log.debug("exit sv01selectOne.do");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "sv01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SV01Vo> deleteSV01Vo(@ModelAttribute SV01Vo sv01Vo, HttpSession session) throws Exception {
		log.debug("enter sv01delete.do");
		log.debug("SP01Vo : " + sv01Vo);

		sv01Service.deleteSV01Vo(sv01Vo);

		sv01Vo.setResult(Constants.SUCCESS_CODE);
		log.debug("exit sv01delete.do");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}
}
