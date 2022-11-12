package com.singer.application.controller.sm;

import com.singer.application.controller.BaseController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.application.service.sm.SM02Service;
import com.singer.domain.entity.sm.SM02Vo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SM02Controller extends BaseController {

	@Autowired
	private SM02Service sm02Service;

	@RequestMapping(value = "/sm02/page", method = RequestMethod.GET)
	public ModelAndView showSM02() throws Exception {
		ModelAndView model = new ModelAndView("/sm02view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sm02/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SM02Vo> selectSM02Vo(@ModelAttribute SM02Vo sm02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sm02 get");

		String userid = getSessionId(request);
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo, userid);
		sm02Vo.setList(list);

		log.debug("exit sm02 get");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SM02Vo> deleteSM02Vo(@ModelAttribute SM02Vo sm02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sm02 delete");
		String userid = getSessionId(request);

		sm02Service.deleteSM02Vo(sm02Vo, userid);

		sm02Vo.setNowPage(1);
		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo, userid);
		sm02Vo.setList(list);

		log.debug("exit sm02 delete");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02", method = RequestMethod.POST)
	public ResponseEntity<SM02Vo> insertSM02Vo(@RequestBody @Valid SM02Vo sm02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sm02 post");

		String userid = getSessionId(request);
		sm02Service.insertSM02Vo(sm02Vo, userid);

		List<SM02Vo> list = sm02Service.selectSM02Vo(sm02Vo, userid);
		sm02Vo.setList(list);

		log.debug("exit sm02 post");
		return new ResponseEntity<SM02Vo>(sm02Vo, HttpStatus.CREATED);
	}
}