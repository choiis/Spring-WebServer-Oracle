package com.singer.controller;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.singer.service.SF01Service;
import com.singer.vo.SF01Vo;

@Controller
public class SF01Controller extends BaseController {

	private final Log log = LogFactory.getLog(SF01Controller.class);

	@Inject
	private SF01Service sf01Service;

	@RequestMapping(value = "/sf01page", method = RequestMethod.GET)
	public ModelAndView showSF01() throws Exception {
		ModelAndView model = new ModelAndView("/sf01view");
		return model;
	}

	@RequestMapping(value = "/sf01insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSF01() throws Exception {
		ModelAndView model = new ModelAndView("/sf01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01", method = RequestMethod.POST)
	public ResponseEntity<SF01Vo> insertSF01Vo(@ModelAttribute SF01Vo sf01Vo, MultipartHttpServletRequest request)
			throws Exception {
		log.debug("enter sf01 post");

		String userid = getSessionId(request);

		int success = sf01Service.insertSF01Vo(sf01Vo, request, userid);
		sf01Vo.setResult(success);

		log.debug("exit sf01 post");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF01Vo> selectSF01Vo(@ModelAttribute SF01Vo sf01Vo) throws Exception {
		log.debug("enter sf01 get");

		List<SF01Vo> list = sf01Service.selectSF01Vo(sf01Vo);
		sf01Vo.setList(list);

		log.debug("exit sf01 get");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01find/{selection}/{findText}", method = RequestMethod.GET)
	public ResponseEntity<SF01Vo> selectFindSF01Vo(@ModelAttribute SF01Vo sf01Vo) throws Exception {
		log.debug("enter sf01find get");

		List<SF01Vo> list = sf01Service.selectFindSF01Vo(sf01Vo);
		sf01Vo.setList(list);

		log.debug("exit sf01find get");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpServletRequest request) throws Exception {
		log.debug("enter sf01show_detail get");

		ModelAndView model = new ModelAndView("/sf01view_detail");

		String userid = getSessionId(request);
		sf01Vo = sf01Service.selectOneSF01Vo(sf01Vo, userid);
		model.addObject("sf01Vo", sf01Vo);

		log.debug("exit sf01show_detail get");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01File/{seq}/{regdate}", method = RequestMethod.GET)
	public ModelAndView selectFileSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("enter sf01File get");

		HashMap<String, Object> downloadFile = sf01Service.selectFile(sf01Vo);

		log.debug("exit sf01File get");
		return new ModelAndView("filedownloadView", "downloadFile", downloadFile);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SF01Vo> deleteSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf01 delete");

		sf01Service.deleteSF01Vo(sf01Vo);
		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());

		log.debug("exit sf01 delete");
		return new ResponseEntity<SF01Vo>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01like/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SF01Vo> likeSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf01like put");

		String sessionid = getSessionId(request);

		sf01Vo = sf01Service.likeSF01Vo(sf01Vo, sessionid);

		log.debug("exit sf01like put");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01hate/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SF01Vo> hateSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf01hate put");

		String sessionid = getSessionId(request);

		sf01Vo = sf01Service.hateSF01Vo(sf01Vo, sessionid);

		log.debug("exit sf01hate put");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}
}