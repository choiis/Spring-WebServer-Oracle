package com.singer.exception;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.kafka.Producer;
import com.singer.util.InputQueryUtil;

@ControllerAdvice
public class CommonExceptionHandler {
	private static final Log log = LogFactory.getLog(ExceptionHandler.class);

	@Inject
	private Producer producer;

	@ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("defaultException");
		if (CommonUtil.isNull(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		InputQueryUtil queryUtil = new InputQueryUtil("log_error");
		queryUtil.add(request.getRequestURI());
		queryUtil.add(DateUtil.getTodayTime());
		queryUtil.add(ext.getLocalizedMessage());

		producer.send(queryUtil.getQuery());

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", "500");
		mv.addObject("errorMsg", ext.getLocalizedMessage());
		return mv;
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "your message")
	public ModelAndView SQLExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("SQLException");
		if (CommonUtil.isNull(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		InputQueryUtil queryUtil = new InputQueryUtil("log_error");
		queryUtil.add(request.getRequestURI());
		queryUtil.add(DateUtil.getTodayTime());
		queryUtil.add(ext.getCause().getLocalizedMessage());

		producer.send(queryUtil.getQuery());

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", "500");
		mv.addObject("errorMsg", ext.getCause().getLocalizedMessage());
		return mv;
	}

	@ExceptionHandler(AppException.class)
	public ModelAndView appExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("AppException");
		if (CommonUtil.isNull(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", "500");
		mv.addObject("errorMsg", ext.getMessage());
		return mv;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView NoHandlerFoundException(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("NoHandlerFoundException");
		if (CommonUtil.isNull(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		InputQueryUtil queryUtil = new InputQueryUtil("log_error");
		queryUtil.add(request.getRequestURI());
		queryUtil.add(DateUtil.getTodayTime());
		queryUtil.add(ext.getLocalizedMessage());

		producer.send(queryUtil.getQuery());

		String errorURL = request.getRequestURL().toString();
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", "500");
		mv.addObject("errorMsg", "error Url" + errorURL + " || " + ext.getMessage());
		return mv;
	}
}
