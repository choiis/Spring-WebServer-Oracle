package com.singer.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.Constants.RESULT_CODE;
import com.singer.service.SV01Service;
import com.singer.vo.SV01Vo;

@Controller
public class SV01Controller extends BaseController {

	private final Log log = LogFactory.getLog(SV01Controller.class);

	@Inject
	private SV01Service sv01Service;

	@RequestMapping(value = "/sv01/page", method = RequestMethod.GET)
	public ModelAndView showSV01() throws Exception {
		ModelAndView model = new ModelAndView("/sv01view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectDetailSV01Vo(@ModelAttribute SV01Vo sv01Vo) throws Exception {
		log.debug("enter sv01show_detail get");

		ModelAndView model = new ModelAndView("/sv01view_detail");
		model.addObject("seq", sv01Vo.getSeq());

		log.debug("exit sv01show_detail get");
		return model;
	}

	@RequestMapping(value = "/sv01/insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSV01() throws Exception {
		ModelAndView model = new ModelAndView("/sv01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SV01Vo> selectSV01Vo(@ModelAttribute SV01Vo sv01Vo) throws Exception {
		log.debug("enter sv01 get");

		List<SV01Vo> list = sv01Service.selectSV01Vo(sv01Vo);
		sv01Vo.setList(list);

		log.debug("exit sv01 get");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/find/{selection}/{findText}", method = RequestMethod.GET)
	public ResponseEntity<SV01Vo> selectFindSV01Vo(@ModelAttribute SV01Vo sv01Vo) throws Exception {
		log.debug("enter sv01find get");

		List<SV01Vo> list = sv01Service.selectFindSV01Vo(sv01Vo);
		sv01Vo.setList(list);

		log.debug("exit sv01find get");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01", method = RequestMethod.POST)
	public ResponseEntity<SV01Vo> insertSV01Vo(@RequestBody SV01Vo sv01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sv01 post");

		String userid = getSessionId(request);
		sv01Service.insertSV01Vo(sv01Vo, userid);

		log.debug("exit sv01 post");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/one/{seq}/{recall}", method = RequestMethod.GET)
	public ResponseEntity<SV01Vo> selectOneSV01Vo(@ModelAttribute SV01Vo sv01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sv01One get");

		String userid = getSessionId(request);
		sv01Vo = sv01Service.selectOneSV01Vo(sv01Vo, userid);

		log.debug("exit sv01One get");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SV01Vo> deleteSV01Vo(@ModelAttribute SV01Vo sv01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sv01 delete");

		sv01Service.deleteSV01Vo(sv01Vo);
		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());

		log.debug("exit sv01 delete");
		return new ResponseEntity<SV01Vo>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/like/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SV01Vo> likeSV01vo(@ModelAttribute SV01Vo sv01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sv01like put");

		String sessionid = getSessionId(request);
		sv01Service.likeSV01Vo(sv01Vo, sessionid);

		log.debug("exit sv01like put");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sv01/hate/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SV01Vo> hateSV01vo(@ModelAttribute SV01Vo sv01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sv01hate put");

		String sessionid = getSessionId(request);
		sv01Service.hateSV01Vo(sv01Vo, sessionid);

		log.debug("exit sv01hate put");
		return new ResponseEntity<SV01Vo>(sv01Vo, HttpStatus.OK);
	}
}
