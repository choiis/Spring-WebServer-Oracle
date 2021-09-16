package com.singer.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.singer.service.SR03Service;
import com.singer.vo.SR03Vo;

@RequestMapping("/sr01")
@Controller
public class SR03Controller extends BaseController {

	private final Log log = LogFactory.getLog(SR03Controller.class);

	@Inject
	private SR03Service sr03Service;

	@ResponseBody
	@RequestMapping(value = "/sr03/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SR03Vo> selectsr03Vo(@ModelAttribute SR03Vo sr03Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr03 get");

		String userid = getSessionId(request);
		List<SR03Vo> list = sr03Service.selectSR03Vo(sr03Vo, userid);
		sr03Vo.setList(list);

		log.debug("exit sr03 get");
		return new ResponseEntity<SR03Vo>(sr03Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr03", method = RequestMethod.POST)
	public ResponseEntity<SR03Vo> insertsr03Vo(@RequestBody @Valid SR03Vo sr03Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr03 post");

		String userid = getSessionId(request);
		sr03Service.insertSR03Vo(sr03Vo, userid);

		log.debug("exit sr03 post");
		return new ResponseEntity<SR03Vo>(sr03Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sr03/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SR03Vo> deletesr03Vo(@ModelAttribute SR03Vo sr03Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr03 delete");

		sr03Service.deleteSR03Vo(sr03Vo);

		log.debug("exit sr03 delete");
		return new ResponseEntity<SR03Vo>(HttpStatus.NO_CONTENT);
	}
}
