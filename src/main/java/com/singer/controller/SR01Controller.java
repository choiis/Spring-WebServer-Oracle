package com.singer.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.Constants;
import com.singer.service.SR01Service;
import com.singer.vo.SR01Vo;

@Controller("sR01Controller")
public class SR01Controller {

	private final Log log = LogFactory.getLog(SR01Controller.class);

	@Resource(name = "sr01Service")
	private SR01Service sr01Service;

	@RequestMapping(value = "/sr01page", method = RequestMethod.GET)
	public ModelAndView showSR01() throws Exception {
		ModelAndView model = new ModelAndView("/sr01view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sr01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectDetailSR01Vo(@ModelAttribute SR01Vo sr01Vo) throws Exception {
		log.debug("enter sr01show_detail.do");

		ModelAndView model = new ModelAndView("/sr01view_detail");

		model.addObject("seq", sr01Vo.getSeq());

		log.debug("exit sr01show_detail.do");
		return model;
	}

	@RequestMapping(value = "sr01insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSR01() throws Exception {
		ModelAndView model = new ModelAndView("/sr01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sr01/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SR01Vo> selectsr01Vo(@ModelAttribute SR01Vo sr01Vo) throws Exception {

		log.debug("enter sr01show.do");
		log.debug("sr01Vo : " + sr01Vo);

		List<SR01Vo> list = sr01Service.selectSR01Vo(sr01Vo);
		sr01Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sr01show.do");
		return new ResponseEntity<SR01Vo>(sr01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01", method = RequestMethod.POST)
	public ResponseEntity<SR01Vo> insertSR01Vo(@RequestBody SR01Vo sr01Vo, HttpSession session,
			MultipartHttpServletRequest request) throws Exception {
		log.debug("enter sr01insert.do");
		log.debug("sr01Vo : " + sr01Vo);

		String userid = (String) session.getAttribute("userid");

		sr01Service.insertSR01Vo(sr01Vo, request, userid);

		log.debug("exit sr01insert.do");
		return new ResponseEntity<SR01Vo>(sr01Vo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01One/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SR01Vo> selectOneSR01Vo(@ModelAttribute SR01Vo sr01Vo, HttpSession session) throws Exception {
		log.debug("enter sr01selectOne.do");
		log.debug("sr01Vo : " + sr01Vo);
		String userid = (String) session.getAttribute("userid");

		sr01Vo = sr01Service.selectOneSR01Vo(sr01Vo, userid);
		log.debug("exit sr01selectOne.do");
		return new ResponseEntity<SR01Vo>(sr01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "sr01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SR01Vo> deleteSR01Vo(@ModelAttribute SR01Vo sr01Vo, HttpSession session) throws Exception {
		log.debug("enter sr01delete.do");
		log.debug("SR01Vo : " + sr01Vo);

		sr01Service.deleteSR01Vo(sr01Vo);

		sr01Vo.setResult(Constants.SUCCESS_CODE);
		log.debug("exit sr01delete.do");
		return new ResponseEntity<SR01Vo>(sr01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "sr01like/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SR01Vo> likeSR01vo(@ModelAttribute SR01Vo sr01Vo, HttpSession session) throws Exception {
		log.debug("enter sr01like.do");
		log.debug("sr01Vo : " + sr01Vo);
		String sessionid = (String) session.getAttribute("userid");
		sr01Service.likeSR01Vo(sr01Vo, sessionid);

		return new ResponseEntity<SR01Vo>(sr01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "sr01hate/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SR01Vo> hateSR01vo(@ModelAttribute SR01Vo sr01Vo, HttpSession session) throws Exception {
		log.debug("enter sr01hate.do");
		log.debug("sr01Vo : " + sr01Vo);
		String sessionid = (String) session.getAttribute("userid");
		sr01Service.hateSR01Vo(sr01Vo, sessionid);

		return new ResponseEntity<SR01Vo>(sr01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sr01photo/{seq}/{idx}", method = RequestMethod.GET)
	public void selectPhotoSR01Vo(@ModelAttribute SR01Vo sr01Vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("enter selectVideo.do");
		InputStream is = sr01Service.selectPhoto(sr01Vo);

		IOUtils.copy(is, response.getOutputStream());
		if (is != null) {
			try {
				is.close();
			} catch (Exception e2) {

			}
		}
	}
}
