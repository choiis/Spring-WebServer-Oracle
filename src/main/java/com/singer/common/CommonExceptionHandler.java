package com.singer.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionHandler {
	private static final Log log = LogFactory.getLog(ExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "your message")
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("defaultException");
		log.info(ext.getMessage());
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/common/error.json");
		} else {
			mv = new ModelAndView("forward:/common/forwardError.do");
		}

		mv.addObject("errorCode", "500");
		mv.addObject("errorMsg", ext.getMessage());
		return mv;
	}

	@ExceptionHandler(AppException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "your message")
	public ModelAndView appExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("AppException");
		log.info(ext.getMessage());
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/common/error.json");
		} else {
			mv = new ModelAndView("forward:/common/forwardError.do");
		}

		mv.addObject("errorCode", "500");
		mv.addObject("errorMsg", ext.getMessage());
		return mv;
	}
}
