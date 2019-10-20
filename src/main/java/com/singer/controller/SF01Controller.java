package com.singer.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.singer.common.Constants;
import com.singer.service.SF01Service;
import com.singer.vo.SF01Vo;

@Controller("sF01Controller")
public class SF01Controller {

	private final Log log = LogFactory.getLog(SF01Controller.class);

	@Resource(name = "sf01Service")
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
	public ResponseEntity<SF01Vo> insertSF01Vo(@ModelAttribute("SF01Vo") SF01Vo sf01Vo, HttpSession session,
			MultipartHttpServletRequest request) throws Exception {
		log.debug("enter sf01insert.do");
		log.debug("sf01Vo : " + sf01Vo);

		String userid = (String) session.getAttribute("userid");

		int success = sf01Service.insertSF01Vo(sf01Vo, request, userid);
		sf01Vo.setResult(success);
		log.debug("exit sf01insert.do");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF01Vo> selectSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01show.do");
		log.debug("sf01Vo : " + sf01Vo);

		List<SF01Vo> list = sf01Service.selectSF01Vo(sf01Vo);
		sf01Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sf01show.do");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01showFind.do", method = RequestMethod.POST)
	public ResponseEntity<SF01Vo> selectFindSF01Vo(SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01showFind.do");
		log.debug("sf01Vo : " + sf01Vo);

		List<SF01Vo> list = sf01Service.selectFindSF01Vo(sf01Vo);
		sf01Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sf01showFind.do");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01show_detail");
		log.debug("sf01Vo : " + sf01Vo);

		ModelAndView model = new ModelAndView("/sf01view_detail");
		String userid = (String) session.getAttribute("userid");
		sf01Vo = sf01Service.selectOneSF01Vo(sf01Vo, userid);
		model.addObject("sf01Vo", sf01Vo);

		log.debug("sf01Vo : " + sf01Vo);
		log.debug("exit sf01showFind.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01File/{seq}/{regdate}", method = RequestMethod.GET)
	public ModelAndView selectFileSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("sf01Vo : " + sf01Vo);
		HashMap<String, Object> downloadFile = sf01Service.selectFile(sf01Vo);

		return new ModelAndView("filedownloadView", "downloadFile", downloadFile);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SF01Vo> deleteSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01delete.do");
		log.debug("sf01Vo : " + sf01Vo);

		sf01Service.deleteSF01Vo(sf01Vo);

		sf01Vo.setResult(Constants.SUCCESS_CODE);
		log.debug("exit sf01delete.do");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01like/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SF01Vo> likeSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01like.do");
		log.debug("sf01Vo : " + sf01Vo);
		String sessionid = (String) session.getAttribute("userid");
		sf01Vo = sf01Service.likeSF01Vo(sf01Vo, sessionid);

		log.debug("exit sf01like.do");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sf01hate/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SF01Vo> hateSF01Vo(@ModelAttribute SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01hate.do");
		log.debug("sf01Vo : " + sf01Vo);
		String sessionid = (String) session.getAttribute("userid");
		sf01Vo = sf01Service.hateSF01Vo(sf01Vo, sessionid);

		log.debug("exit sf01hate.do");
		return new ResponseEntity<SF01Vo>(sf01Vo, HttpStatus.OK);
	}
}