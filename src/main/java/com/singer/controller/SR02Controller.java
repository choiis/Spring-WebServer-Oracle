package com.singer.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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

import com.singer.common.Constants.RESULT_CODE;
import com.singer.service.SR02Service;
import com.singer.vo.SR01Vo;

@RequestMapping("/sr01")
@Controller
public class SR02Controller extends BaseController {
	private final Log log = LogFactory.getLog(SR02Controller.class);

	@Inject
	private SR02Service sr02Service;

	@ResponseBody
	@RequestMapping(value = "/sr02", method = RequestMethod.POST)
	public ResponseEntity<SR01Vo> insertSR02Vo(@RequestBody SR01Vo sr02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr02 post");

		String userid = getSessionId(request);
		sr02Vo = sr02Service.insertSR02Vo(sr02Vo, userid);

		log.debug("exit sr02 post");
		return new ResponseEntity<SR01Vo>(sr02Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "sr02/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SR01Vo> deleteSR02Vo(@ModelAttribute SR01Vo sr02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr02 delete");
		sr02Vo.setUserid(getSessionId(request));
		sr02Service.deleteSR02Vo(sr02Vo);
		sr02Vo.setResult(RESULT_CODE.SUCCESS.getValue());

		log.debug("exit sr02 delete");
		return new ResponseEntity<SR01Vo>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "sr02/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SR01Vo> selectOneSR02Vo(@ModelAttribute SR01Vo sr02Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr02 get");

		String userid = getSessionId(request);
		sr02Vo = sr02Service.selectOneSR02Vo(sr02Vo, userid);

		if (sr02Vo == null) {
			SR01Vo sr02Vo2 = new SR01Vo();
			sr02Vo2.setResult(RESULT_CODE.FAIL.getValue());

			log.debug("exit sr02 get");
			return new ResponseEntity<SR01Vo>(sr02Vo, HttpStatus.OK);
		} else {

			sr02Vo.setResult(RESULT_CODE.SUCCESS.getValue());

			log.debug("exit sr02 get");
			return new ResponseEntity<SR01Vo>(sr02Vo, HttpStatus.OK);
		}
	}
}
