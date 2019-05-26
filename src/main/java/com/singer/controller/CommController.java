package com.singer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.singer.service.CommService;
import com.singer.vo.CommVo;

@Controller("commController")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CommController {
	private final Log log = LogFactory.getLog(SB01Controller.class);

	@Resource(name = "commService")
	private CommService commService;

	@ResponseBody
	@RequestMapping(value = "/commCode.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toSelectCommCode(@RequestBody CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter commCode.do");
		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.selectCode(commVo);
		commVo.setCommList(list);

		log.debug("exit sb01show.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/commCodeGrp.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toSelectCommCodeGrp(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter commCodeGrp.do");
		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.selectCodeGrp(commVo);
		commVo.setCommList(list);

		log.debug("exit commCodeGrp.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/commMenu.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toSelectMenu(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter commMenu.do");
		log.debug("CommVo : " + commVo);

		String authlevel = (String) session.getAttribute("usertype");
		List<CommVo> list = commService.selectMenu(commVo, authlevel);
		commVo.setCommList(list);

		log.debug("exit commMenu.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@RequestMapping(value = "/menu.do", method = RequestMethod.GET)
	public ModelAndView toShowmenu() throws Exception {
		ModelAndView model = new ModelAndView("/menu");
		log.debug("enter menu.do");

		log.debug("exit menu.do");
		return model;
	}

	@RequestMapping(value = "/code.do", method = RequestMethod.GET)
	public ModelAndView toShowcode() throws Exception {
		ModelAndView model = new ModelAndView("/code");
		log.debug("enter code.do");

		log.debug("exit code.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/insertMenu.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toInsertMenu(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter insertMenu.do");
		log.debug("CommVo : " + commVo);

		String authlevel = (String) session.getAttribute("usertype");
		String userid = (String) session.getAttribute("userid");
		List<CommVo> list = commService.insertMenu(commVo, userid, authlevel);
		commVo.setCommList(list);

		log.debug("exit insertMenu.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteMenu.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toDeleteMenu(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter deleteMenu.do");
		log.debug("CommVo : " + commVo);

		String authlevel = (String) session.getAttribute("usertype");
		List<CommVo> list = commService.deleteMenu(commVo, authlevel);
		commVo.setCommList(list);

		log.debug("exit deleteMenu.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/updateMenu.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toUpdateMenu(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter updateMenu.do");
		log.debug("CommVo : " + commVo);

		String authlevel = (String) session.getAttribute("usertype");
		String userid = (String) session.getAttribute("userid");
		List<CommVo> list = commService.updateMenu(commVo, userid, authlevel);
		commVo.setCommList(list);

		log.debug("exit updateMenu.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/insertCode.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toInsertCode(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter insertCode.do");
		log.debug("CommVo : " + commVo);

		String userid = (String) session.getAttribute("userid");
		List<CommVo> list = commService.insertCode(commVo, userid);
		commVo.setCommList(list);

		log.debug("exit insertCode.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteCode.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toDeleteCode(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter deleteCode.do");
		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.deleteCode(commVo);
		commVo.setCommList(list);

		log.debug("exit deleteCode.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/updateCode.do", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toUpdateCode(CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter updateCode.do");
		log.debug("CommVo : " + commVo);

		commService.updateCode(commVo);

		log.debug("exit updateCode.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@RequestMapping(value = "/authExpire.do")
	public ModelAndView authExpire() throws Exception {
		log.debug("enter authExpire.do");

		ModelAndView model = new ModelAndView("/authExpire");

		log.debug("exit authExpire.do");

		return model;
	}

	@RequestMapping(value = "/error.do")
	public ResponseEntity<CommVo> errorJson(CommVo commVo, HttpServletRequest request) {
		log.debug("enter error.do");

		commVo.setErrorCode((String) request.getAttribute("errorCode"));
		commVo.setErrorMsg((String) request.getAttribute("errorMsg"));

		log.debug("exit error.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/forwardError.do")
	public ModelAndView forwardError(HttpServletRequest request) throws Exception {
		log.debug("enter forwardError.do");

		ModelAndView mv = new ModelAndView("/forwardError");
		String errorCode = (String) request.getAttribute("errorCode");
		String errorMsg = (String) request.getAttribute("errorMsg");
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		log.debug("exit forwardError.do");
		return mv;
	}
}
