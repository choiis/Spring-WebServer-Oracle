package com.singer.application.controller.sr;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sr.SR01ListResponse;
import com.singer.application.dto.sr.SR01Request;
import com.singer.application.dto.sr.SR01Response;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.application.service.sr.SR01Service;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SR01Controller extends BaseController {

	@Autowired
	private SR01Service sr01Service;

	@RequestMapping(value = "/sr01/page", method = RequestMethod.GET)
	public ModelAndView showSR01() throws Exception {
		ModelAndView model = new ModelAndView("/sr01view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectDetailSR01Vo(@PathVariable int seq) throws Exception {
		log.debug("enter sr01show_detail get");

		ModelAndView model = new ModelAndView("/sr01view_detail");
		model.addObject("seq", seq);

		log.debug("exit sr01show_detail get");
		return model;
	}

	@RequestMapping(value = "/sr01/insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSR01() throws Exception {
		ModelAndView model = new ModelAndView("/sr01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SR01ListResponse> selectSR01Vo(@PathVariable int nowPage) throws Exception {
		log.debug("enter sr01 get");

		SR01ListResponse list = sr01Service.selectSR01List(nowPage);

		log.debug("exit sr01 get");
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01", method = RequestMethod.POST)
	public ResponseEntity<SR01Response> insertSR01Vo(@ModelAttribute @Valid SR01Request sr01Request,
			MultipartHttpServletRequest request) throws Exception {
		log.debug("enter sr01 post");

		String userid = getSessionId(request);
		SR01Response sr01Response = sr01Service.insertSR01(sr01Request, request, userid);

		log.debug("exit sr01 post");
		return new ResponseEntity<>(sr01Response, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/one/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SR01Response> selectOneSR01Vo(@PathVariable int seq, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr01One get");

		String userid = getSessionId(request);
		SR01Response sr01Response = sr01Service.selectOneSR01(seq, userid);

		log.debug("exit sr01One get");
		return new ResponseEntity<SR01Response>(sr01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SR01Response> deleteSR01Vo(@PathVariable int seq, HttpServletRequest request)
			throws Exception {
		log.debug("enter sr01 delete");

        String sessionid = getSessionId(request);
		sr01Service.deleteSR01(seq, sessionid);

		log.debug("exit sr01 delete");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/like/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SR01Response> likeSR01vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sr01like put");

		String sessionid = getSessionId(request);
		SR01Response sr01Response = sr01Service.likeSR01(seq, sessionid);

		log.debug("exit sr01like put");
		return new ResponseEntity<>(sr01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/hate/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SR01Response> hateSR01vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sr01hate put");

		String sessionid = getSessionId(request);
		SR01Response sr01Response = sr01Service.hateSR01(seq, sessionid);

		log.debug("exit sr01hate put");
		return new ResponseEntity<>(sr01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/photo/{seq}/{idx}", method = RequestMethod.GET)
	public void selectPhotoSR01Vo(@PathVariable int seq, @PathVariable int idx, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("enter sr01photo get");
		@Cleanup
		InputStream is = sr01Service.selectPhoto(seq, idx);

		IOUtils.copy(is, response.getOutputStream());

		log.debug("exit sr01photo get");
	}
}
