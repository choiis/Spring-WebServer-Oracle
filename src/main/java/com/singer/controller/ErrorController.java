package com.singer.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.singer.vo.CommVo;

@Controller("errorController")
public class ErrorController {

	private final Log log = LogFactory.getLog(ErrorController.class);

	// CommonExceptionHandler에서 Ajax Request들을 여기로 보낸다
	@RequestMapping(value = "/error")
	public ResponseEntity<CommVo> errorJson(CommVo commVo, HttpServletRequest request) {
		log.debug("server ajax error");
		commVo.setErrorCode((String) request.getAttribute("errorCode"));
		commVo.setErrorMsg((String) request.getAttribute("errorMsg"));

		return new ResponseEntity<CommVo>(commVo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// CommonExceptionHandler에서 Ajax 아닌것들을 여기로 보낸다
	@RequestMapping(value = "/forwardError")
	public ModelAndView forwardError(HttpServletRequest request) throws Exception {
		log.debug("server not ajax error");

		ModelAndView mv = new ModelAndView("/serverError");
		String errorCode = (String) request.getAttribute("errorCode");
		String errorMsg = (String) request.getAttribute("errorMsg");
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/400")
	public ModelAndView clientError400(HttpServletRequest request) throws Exception {
		log.debug("client error 400");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.BAD_REQUEST;
		String errorMsg = HttpStatus.BAD_REQUEST.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/401")
	public ModelAndView clientError401(HttpServletRequest request) throws Exception {
		log.debug("client error 401");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.UNAUTHORIZED;
		String errorMsg = HttpStatus.UNAUTHORIZED.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/403")
	public ModelAndView clientError403(HttpServletRequest request) throws Exception {
		log.debug("client error 403");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.FORBIDDEN;
		String errorMsg = HttpStatus.FORBIDDEN.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/404")
	public ModelAndView clientError404(HttpServletRequest request) throws Exception {
		log.debug("client error 404");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.NOT_FOUND;
		String errorMsg = HttpStatus.NOT_FOUND.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/405")
	public ModelAndView clientError405(HttpServletRequest request) throws Exception {
		log.debug("client error 405");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.METHOD_NOT_ALLOWED;
		String errorMsg = HttpStatus.METHOD_NOT_ALLOWED.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/415")
	public ModelAndView clientError415(HttpServletRequest request) throws Exception {
		log.debug("client error 415");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		String errorMsg = HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/502")
	public ModelAndView clientError502(HttpServletRequest request) throws Exception {
		log.debug("client error 502");

		ModelAndView mv = new ModelAndView("/serverError");
		HttpStatus errorCode = HttpStatus.BAD_GATEWAY;
		String errorMsg = HttpStatus.BAD_GATEWAY.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/503")
	public ModelAndView clientError503(HttpServletRequest request) throws Exception {
		log.debug("client error 503");

		ModelAndView mv = new ModelAndView("/serverError");
		HttpStatus errorCode = HttpStatus.SERVICE_UNAVAILABLE;
		String errorMsg = HttpStatus.SERVICE_UNAVAILABLE.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}
}
