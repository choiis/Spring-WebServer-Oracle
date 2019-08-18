package com.singer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.singer.common.Constants;
import com.singer.service.SB01Service;
import com.singer.vo.SB01Vo;

@Controller("sB01Controller")
public class SB01Controller {

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
	public ModelAndView insertSB01Vo(@ModelAttribute("SB01Vo") SB01Vo sb01Vo, HttpSession session,
			MultipartHttpServletRequest request) throws Exception {

		log.debug("sb01Vo : " + sb01Vo);

		String userid = (String) session.getAttribute("userid");
		sb01Vo.setUserid(userid);

		sb01Service.insertSB01Vo(sb01Vo, request);

		ModelAndView model = new ModelAndView("/sb01view");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SB01Vo> selectSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpSession session) throws Exception {

		log.debug("enter sb01show.do");
		log.debug("sb01Vo : " + sb01Vo);

		List<SB01Vo> list = sb01Service.selectSB01Vo(sb01Vo);
		sb01Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sb01show.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01showFind.do", method = RequestMethod.POST)
	public ResponseEntity<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo, HttpSession session) throws Exception {

		log.debug("enter sb01showFind.do");
		log.debug("sb01Vo : " + sb01Vo);

		List<SB01Vo> list = sb01Service.selectFindSB01Vo(sb01Vo);
		sb01Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sb01showFind.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01show_detail");
		log.debug("sb01Vo : " + sb01Vo);

		ModelAndView model = new ModelAndView("/sb01view_detail");
		String userid = (String) session.getAttribute("userid");
		sb01Vo = sb01Service.selectOneSB01Vo(sb01Vo, userid);
		model.addObject("sb01Vo", sb01Vo);

		log.debug("sb01Vo : " + sb01Vo);
		log.debug("exit sb01show_detail");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01Video/{seq}/{title}", method = RequestMethod.GET)
	public void selectVideoSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("enter selectVideo.do");
		File file = sb01Service.selectVideo(sb01Vo, request);

		InputStream is = null;
		try {
			is = new FileInputStream(file);
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {

				}
			}
			log.debug("exit selectVideo.do");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/sb01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SB01Vo> deleteSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01delete.do");
		log.debug("sb01Vo : " + sb01Vo);

		sb01Service.deleteSB01Vo(sb01Vo);

		sb01Vo.setResult(Constants.SUCCESS_CODE);
		log.debug("exit sf01delete.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01like/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SB01Vo> likeSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01like.do");
		log.debug("sb01Vo : " + sb01Vo);
		String sessionid = (String) session.getAttribute("userid");

		sb01Vo = sb01Service.likeSB01Vo(sb01Vo, sessionid);

		log.debug("exit sb01like.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01hate/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SB01Vo> hateSB01Vo(@ModelAttribute SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01hate.do");
		log.debug("sb01Vo : " + sb01Vo);
		String sessionid = (String) session.getAttribute("userid");
		sb01Vo = sb01Service.hateSB01Vo(sb01Vo, sessionid);

		log.debug("exit sb01hate.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}
}