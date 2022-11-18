package com.singer.application.controller.sm;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sm.SM02ListResponse;
import com.singer.application.dto.sm.SM02Request;
import com.singer.application.dto.sm.SM02Response;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.application.service.sm.SM02Service;

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
	public ResponseEntity<SM02ListResponse> selectSM02(@PathVariable int nowPage, HttpServletRequest request)
			throws Exception {
		log.debug("enter sm02 get");

		String userid = getSessionId(request);
		SM02ListResponse listResponse = sm02Service.selectSM02List(nowPage, userid);

		log.debug("exit sm02 get");
		return new ResponseEntity<>(listResponse, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SM02Response> deleteSM02(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sm02 delete");
		String userid = getSessionId(request);

		sm02Service.deleteSM02(seq, userid);

		log.debug("exit sm02 delete");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "/sm02", method = RequestMethod.POST)
	public ResponseEntity<SM02Response> insertSM02(@RequestBody @Valid SM02Request sm02Request,
			HttpServletRequest request) throws Exception {
		log.debug("enter sm02 post");

		String userid = getSessionId(request);
		SM02Response response = sm02Service.insertSM02(sm02Request, userid);

		log.debug("exit sm02 post");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}