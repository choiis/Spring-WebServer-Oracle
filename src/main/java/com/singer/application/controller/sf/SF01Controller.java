package com.singer.application.controller.sf;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sf.SF01ListResponse;
import com.singer.application.dto.sf.SF01Request;
import com.singer.application.dto.sf.SF01Response;
import com.singer.application.service.sf.SF01Service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

@Controller
@Slf4j
public class SF01Controller extends BaseController {

	@Autowired
	private SF01Service sf01Service;

	@RequestMapping(value = "/sf01/page", method = RequestMethod.GET)
	public ModelAndView showSF01() throws Exception {
		ModelAndView model = new ModelAndView("/sf01view");
		return model;
	}

	@RequestMapping(value = "/sf01/insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSF01() throws Exception {
		ModelAndView model = new ModelAndView("/sf01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSF01VoView(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01show_detail get");

		ModelAndView model = new ModelAndView("/sf01view_detail");

		String userid = getSessionId(request);
		SF01Response sf01Response = sf01Service.selectOneSF01Vo(seq, userid);
		model.addObject("sf01Vo", sf01Response);

		log.debug("exit sf01show_detail get");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01", method = RequestMethod.POST)
	public ResponseEntity<SF01Response> insertSF01Vo(@ModelAttribute @Valid SF01Request sf01Request,
			MultipartHttpServletRequest request) throws Exception {
		log.debug("enter sf01 post");

		String userid = getSessionId(request);

		SF01Response sf01Response = sf01Service.insertSF01Vo(sf01Request, request, userid);

		log.debug("exit sf01 post");
		return new ResponseEntity<SF01Response>(sf01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF01ListResponse> selectSF01Vo(@PathVariable int nowPage) throws Exception {
		log.debug("enter sf01 get");

		SF01ListResponse listResponse = sf01Service.selectSF01Vo(nowPage);

		log.debug("exit sf01 get");
		return new ResponseEntity<SF01ListResponse>(listResponse, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/seq/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SF01Response> selectOneSF01Vo(@PathVariable int seq, HttpServletRequest request)
			throws Exception {
		log.debug("enter /sf01/seq get");

		String userid = getSessionId(request);
		SF01Response sf01Response = sf01Service.selectOneSF01Vo(seq, userid);

		log.debug("exit /sf01/seq get");
		return new ResponseEntity<>(sf01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/file/{seq}", method = RequestMethod.GET)
	public ModelAndView selectFileSF01Vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01File get");

		String userid = getSessionId(request);
		HashMap<String, Object> downloadFile = sf01Service.selectFile(seq, userid);

		log.debug("exit sf01File get");
		return new ModelAndView("filedownloadView", "downloadFile", downloadFile);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SF01Response> deleteSF01Vo(@PathVariable int seq, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf01 delete");

		String sessionid = getSessionId(request);
		sf01Service.deleteSF01Vo(seq, sessionid);

		log.debug("exit sf01 delete");
		return new ResponseEntity<SF01Response>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/like/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SF01Response> likeSF01Vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01like put");

		String sessionid = getSessionId(request);

		SF01Response sf01Response = sf01Service.likeSF01Vo(seq, sessionid);

		log.debug("exit sf01like put");
		return new ResponseEntity<SF01Response>(sf01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/hate/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SF01Response> hateSF01Vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01hate put");

		String sessionid = getSessionId(request);

		SF01Response sf01Response = sf01Service.hateSF01Vo(seq, sessionid);

		log.debug("exit sf01hate put");
		return new ResponseEntity<SF01Response>(sf01Response, HttpStatus.OK);
	}
}