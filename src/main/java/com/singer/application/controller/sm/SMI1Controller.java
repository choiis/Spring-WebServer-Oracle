package com.singer.application.controller.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.application.service.sm.SMI1Service;
import com.singer.domain.entity.sm.SM01Vo;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SMI1Controller {


	@Autowired
	private SMI1Service smi1Service;

	@RequestMapping(value = "/smi1/page", method = RequestMethod.GET)
	public ModelAndView page() {
		ModelAndView model = new ModelAndView("smi1view");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/smi1", method = RequestMethod.GET)
	public ResponseEntity<SM01Vo> selectSMI1Vo(@RequestParam(value = "searchCode") String searchCode,
			@RequestParam(value = "searchParam") String searchParam) throws Exception {
		log.debug("enter smi1 get");

		List<SM01Vo> list = smi1Service.selectSMI1Vo(searchCode, searchParam);
		SM01Vo sm01Vo = new SM01Vo();
		sm01Vo.setList(list);

		log.debug("exit smi1 get");
		return new ResponseEntity<SM01Vo>(sm01Vo, HttpStatus.OK);
	}
}
