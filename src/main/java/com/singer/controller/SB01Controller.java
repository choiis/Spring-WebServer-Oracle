package com.singer.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.Constants.RESULT_CODE;
import com.singer.service.SB01Service;
import com.singer.vo.SB01Vo;

import lombok.Cleanup;

@Controller("sB01Controller")
public class SB01Controller extends BaseController {

	private final Log log = LogFactory.getLog(SB01Controller.class);

	@Resource(name = "sb01Service")
	private SB01Service sb01Service;

	@RequestMapping(value = "/sb01page", method = RequestMethod.GET)
	public ModelAndView showSB01() throws Exception {
		ModelAndView model = new ModelAndView("/sb01view");
		return model;
	}

	@RequestMapping(value = "/sb01insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSB01() throws Exception {
		ModelAndView model = new ModelAndView("/sb01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01", method = RequestMethod.POST)
	public ResponseEntity<SB01Vo> insertSB01Vo(@ModelAttribute SB01Vo sb01Vo, MultipartHttpServletRequest request)
			throws Exception {
		log.debug("enter sb01 post");

		String userid = getSessionId(request);

		int success = sb01Service.insertSB01Vo(sb01Vo, request, userid);
		sb01Vo.setResult(success);

		log.debug("exit sb01 post");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SB01Vo> selectSB01Vo(@ModelAttribute SB01Vo sb01Vo) throws Exception {
		log.debug("enter sb01 get");

		List<SB01Vo> list = sb01Service.selectSB01Vo(sb01Vo);
		sb01Vo.setList(list);

		log.debug("exit sb01 get");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01find/{selection}/{findText}", method = RequestMethod.GET)
	public ResponseEntity<SB01Vo> selectFindSB01Vo(@ModelAttribute SB01Vo sb01Vo) throws Exception {
		log.debug("enter sb01find get");

		List<SB01Vo> list = sb01Service.selectFindSB01Vo(sb01Vo);
		sb01Vo.setList(list);

		log.debug("exit sb01find get");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpServletRequest request) throws Exception {
		log.debug("enter sb01show_detail get");

		ModelAndView model = new ModelAndView("/sb01view_detail");

		String userid = getSessionId(request);
		sb01Vo = sb01Service.selectOneSB01Vo(sb01Vo, userid);
		model.addObject("sb01Vo", sb01Vo);

		log.debug("exit sb01show_detail get");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01Video/{seq}/{title}", method = RequestMethod.GET)
	public void selectVideoSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("enter sb01Video get");
		@Cleanup
		InputStream is = sb01Service.selectVideo(sb01Vo, request);

		IOUtils.copy(is, response.getOutputStream());

		log.debug("exit sb01Video get");
	}

	@ResponseBody
	@RequestMapping(value = "/sb01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SB01Vo> deleteSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sb01 delete");

		sb01Service.deleteSB01Vo(sb01Vo);
		sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());

		log.debug("exit sb01 delete");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01like/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SB01Vo> likeSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sb01like put");

		String sessionid = getSessionId(request);

		sb01Vo = sb01Service.likeSB01Vo(sb01Vo, sessionid);

		log.debug("exit sb01like put");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01hate/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SB01Vo> hateSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sb01hate put");

		String sessionid = getSessionId(request);
		sb01Vo = sb01Service.hateSB01Vo(sb01Vo, sessionid);

		log.debug("exit sb01hate put");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}
}