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

@Controller
public class ErrorController {

	private final Log log = LogFactory.getLog(ErrorController.class);

	// CommonExceptionHandler에서 Ajax Request들을 여기로 보낸다
	@RequestMapping(value = "/errors")
	public ResponseEntity<CommVo> errorJson(CommVo commVo, HttpServletRequest request) {
		log.debug("server ajax error");
		HttpStatus errorCode = (HttpStatus) request.getAttribute("errorCode");
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg((String) request.getAttribute("errorMsg"));

		return new ResponseEntity<CommVo>(commVo, errorCode);
	}

	// CommonExceptionHandler에서 Ajax 아닌것들을 여기로 보낸다
	@RequestMapping(value = "/forwardError")
	public ModelAndView forwardError(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView("/serverError");
		HttpStatus errorCode = (HttpStatus) request.getAttribute("errorCode");
		String errorMsg = (String) request.getAttribute("errorMsg");
		log.debug("server not ajax error ");
		log.error("error msg " + errorMsg);
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}

	@RequestMapping(value = "/400")
	public ResponseEntity<CommVo> clientError400(CommVo commVo, HttpServletRequest request) throws Exception {
		log.debug("client error 400");

		HttpStatus errorCode = HttpStatus.BAD_REQUEST;
		String errorMsg = HttpStatus.BAD_REQUEST.toString();
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg(errorMsg);

		return new ResponseEntity<CommVo>(commVo, errorCode);
	}

	@RequestMapping(value = "/401")
	public ResponseEntity<CommVo> clientError401(CommVo commVo, HttpServletRequest request) throws Exception {
		log.debug("client error 401");

		HttpStatus errorCode = HttpStatus.UNAUTHORIZED;
		String errorMsg = HttpStatus.UNAUTHORIZED.toString();
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg(errorMsg);

		return new ResponseEntity<CommVo>(commVo, errorCode);
	}

	@RequestMapping(value = "/403")
	public ResponseEntity<CommVo> clientError403(CommVo commVo, HttpServletRequest request) throws Exception {
		log.debug("client error 403");

		HttpStatus errorCode = HttpStatus.FORBIDDEN;
		String errorMsg = HttpStatus.FORBIDDEN.toString();
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg(errorMsg);

		return new ResponseEntity<CommVo>(commVo, errorCode);
	}

	@RequestMapping(value = "/404")
	public ResponseEntity<CommVo> clientError404(CommVo commVo, HttpServletRequest request) throws Exception {
		log.debug("client error 404");

		HttpStatus errorCode = HttpStatus.NOT_FOUND;
		String errorMsg = HttpStatus.NOT_FOUND.toString();
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg(errorMsg);

		return new ResponseEntity<CommVo>(commVo, errorCode);
	}

	@RequestMapping(value = "/405")
	public ResponseEntity<CommVo> clientError405(CommVo commVo, HttpServletRequest request) throws Exception {
		log.debug("client error 405");

		HttpStatus errorCode = HttpStatus.METHOD_NOT_ALLOWED;
		String errorMsg = HttpStatus.METHOD_NOT_ALLOWED.toString();
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg(errorMsg);

		return new ResponseEntity<CommVo>(commVo, errorCode);
	}

	@RequestMapping(value = "/415")
	public ResponseEntity<CommVo> clientError415(CommVo commVo, HttpServletRequest request) throws Exception {
		log.debug("client error 415");

		HttpStatus errorCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		String errorMsg = HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString();
		commVo.setErrorCode(errorCode);
		commVo.setErrorMsg(errorMsg);

		return new ResponseEntity<CommVo>(commVo, errorCode);
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

	@RequestMapping(value = "/forbiddenPage")
	public ModelAndView forbiddenPage(HttpServletRequest request) throws Exception {
		log.debug("client error 403");

		ModelAndView mv = new ModelAndView("/clientError");
		HttpStatus errorCode = HttpStatus.FORBIDDEN;
		String errorMsg = HttpStatus.FORBIDDEN.toString();
		mv.addObject("errorCode", errorCode);
		mv.addObject("errorMsg", errorMsg);
		return mv;
	}
}
