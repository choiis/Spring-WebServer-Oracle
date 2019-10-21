package com.singer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
	@RequestMapping(value = "/commCode/{codegrp}", method = RequestMethod.GET)
	public ResponseEntity<CommVo> toSelectCommCode(@ModelAttribute CommVo commVo, HttpSession session)
			throws Exception {
		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.selectCode(commVo);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/commCodeGrp", method = RequestMethod.GET)
	public ResponseEntity<CommVo> toSelectCommCodeGrp(@ModelAttribute CommVo commVo, HttpSession session)
			throws Exception {

		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.selectCodeGrp(commVo);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/commMenu", method = RequestMethod.GET)
	public ResponseEntity<CommVo> toSelectMenu(HttpSession session) throws Exception {
		CommVo commVo = new CommVo();

		List<CommVo> menuList = (List<CommVo>) session.getAttribute("menuList");
		commVo.setCommList(menuList);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@RequestMapping(value = "/menupage", method = RequestMethod.GET)
	public ModelAndView toShowmenu() throws Exception {
		ModelAndView model = new ModelAndView("/menu");
		return model;
	}

	@RequestMapping(value = "/codepage", method = RequestMethod.GET)
	public ModelAndView toShowcode() throws Exception {
		ModelAndView model = new ModelAndView("/code");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/commMenu", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toInsertMenu(@RequestBody CommVo commVo, HttpSession session) throws Exception {

		log.debug("CommVo : " + commVo);

		int authlevel = (Integer) session.getAttribute("usertype");
		String userid = (String) session.getAttribute("userid");
		List<CommVo> list = commService.insertMenu(commVo, userid, authlevel);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/commMenu/{menucd}", method = RequestMethod.DELETE)
	public ResponseEntity<CommVo> toDeleteMenu(@ModelAttribute CommVo commVo, HttpSession session) throws Exception {

		log.debug("enter deleteMenu.do");
		log.debug("CommVo : " + commVo);

		int authlevel = (Integer) session.getAttribute("usertype");
		List<CommVo> list = commService.deleteMenu(commVo, authlevel);
		commVo.setCommList(list);

		log.debug("exit deleteMenu.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/commMenu", method = RequestMethod.PUT)
	public ResponseEntity<CommVo> toUpdateMenu(@RequestBody CommVo commVo, HttpSession session) throws Exception {

		log.debug("CommVo : " + commVo);

		int authlevel = (Integer) session.getAttribute("usertype");
		String userid = (String) session.getAttribute("userid");
		List<CommVo> list = commService.updateMenu(commVo, userid, authlevel);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/commCode", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toInsertCode(@RequestBody CommVo commVo, HttpSession session) throws Exception {

		log.debug("CommVo : " + commVo);

		String userid = (String) session.getAttribute("userid");
		List<CommVo> list = commService.insertCode(commVo, userid);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/commCode/{codegrp}/{codecd}", method = RequestMethod.DELETE)
	public ResponseEntity<CommVo> toDeleteCode(@ModelAttribute CommVo commVo, HttpSession session) throws Exception {

		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.deleteCode(commVo);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/commCode", method = RequestMethod.PUT)
	public ResponseEntity<CommVo> toUpdateCode(CommVo commVo, HttpSession session) throws Exception {

		log.debug("CommVo : " + commVo);

		commService.updateCode(commVo);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@RequestMapping(value = "/authExpire")
	public ModelAndView authExpire() throws Exception {

		ModelAndView model = new ModelAndView("/authExpire");

		return model;
	}

}
