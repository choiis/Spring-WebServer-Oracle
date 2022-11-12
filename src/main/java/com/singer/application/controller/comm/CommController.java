package com.singer.application.controller.comm;

import com.singer.application.controller.BaseController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

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
import com.singer.common.util.Constants.USER_CODE;
import com.singer.application.service.comm.CommService;
import com.singer.domain.entity.CommVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Slf4j
public class CommController extends BaseController {

	@Autowired
	private CommService commService;

	@RequestMapping(value = "/comm/chat/page", method = RequestMethod.GET)
	public ModelAndView toShowChatting() throws Exception {
		ModelAndView model = new ModelAndView("/chatting");
		return model;
	}

	@RequestMapping(value = "/comm/menu/page", method = RequestMethod.GET)
	public ModelAndView toShowmenu() throws Exception {
		ModelAndView model = new ModelAndView("/menu");
		return model;
	}

	@RequestMapping(value = "/comm/code/page", method = RequestMethod.GET)
	public ModelAndView toShowcode() throws Exception {
		ModelAndView model = new ModelAndView("/code");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/comm/code/{codegrp}", method = RequestMethod.GET)
	public ResponseEntity<CommVo> toSelectCommCode(@ModelAttribute CommVo commVo) throws Exception {
		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.selectCode(commVo);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/codeGrp", method = RequestMethod.GET)
	public ResponseEntity<CommVo> toSelectCommCodeGrp(@ModelAttribute CommVo commVo) throws Exception {

		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.selectCodeGrp(commVo);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/menu", method = RequestMethod.GET)
	public ResponseEntity<CommVo> toSelectMenu(HttpServletRequest request) throws Exception {
		CommVo commVo = new CommVo();

		List<CommVo> menuList = getMenuList(request);
		commVo.setCommList(menuList);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/menu", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toInsertMenu(@RequestBody CommVo commVo, HttpServletRequest request)
			throws Exception {

		log.debug("CommVo : " + commVo);

		USER_CODE authlevel = getUsertype(request);
		String userid = getSessionId(request);
		List<CommVo> list = commService.insertMenu(commVo, userid, authlevel);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/menu/{menucd}", method = RequestMethod.DELETE)
	public ResponseEntity<CommVo> toDeleteMenu(@ModelAttribute CommVo commVo, HttpServletRequest request)
			throws Exception {

		log.debug("enter deleteMenu.do");
		log.debug("CommVo : " + commVo);

		USER_CODE authlevel = getUsertype(request);
		List<CommVo> list = commService.deleteMenu(commVo, authlevel);
		commVo.setCommList(list);

		log.debug("exit deleteMenu.do");
		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/menu", method = RequestMethod.PUT)
	public ResponseEntity<CommVo> toUpdateMenu(@RequestBody CommVo commVo, HttpServletRequest request)
			throws Exception {

		log.debug("CommVo : " + commVo);

		USER_CODE authlevel = getUsertype(request);
		String userid = getSessionId(request);
		List<CommVo> list = commService.updateMenu(commVo, userid, authlevel);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/code", method = RequestMethod.POST)
	public ResponseEntity<CommVo> toInsertCode(@RequestBody CommVo commVo, HttpServletRequest request)
			throws Exception {

		log.debug("CommVo : " + commVo);

		String userid = getSessionId(request);
		List<CommVo> list = commService.insertCode(commVo, userid);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/code/{codegrp}/{codecd}", method = RequestMethod.DELETE)
	public ResponseEntity<CommVo> toDeleteCode(@ModelAttribute CommVo commVo, HttpServletRequest request)
			throws Exception {

		log.debug("CommVo : " + commVo);

		List<CommVo> list = commService.deleteCode(commVo);
		commVo.setCommList(list);

		return new ResponseEntity<CommVo>(commVo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/comm/code", method = RequestMethod.PUT)
	public ResponseEntity<CommVo> toUpdateCode(CommVo commVo, HttpServletRequest request) throws Exception {

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
