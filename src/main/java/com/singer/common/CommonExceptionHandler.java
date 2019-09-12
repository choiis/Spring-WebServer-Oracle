package com.singer.common;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.singer.dao.ErrorDao;
import com.singer.vo.ErrorVo;

@ControllerAdvice
public class CommonExceptionHandler {
	private static final Log log = LogFactory.getLog(ExceptionHandler.class);

	@Resource(name = "errorDao")
	private ErrorDao errorDao;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "your message")
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("defaultException");
		log.info(ext.getMessage());
		ErrorVo errorVo = new ErrorVo(request.getRequestURI(), DateUtil.getTodayTime(),
				ext.getCause().getLocalizedMessage());
		errorDao.insertError(errorVo);

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

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "your message")
	public ModelAndView SQLExceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("SQLException");
		log.info(ext.getMessage());
		ErrorVo errorVo = new ErrorVo(request.getRequestURI(), DateUtil.getTodayTime(),
				ext.getCause().getLocalizedMessage());
		errorDao.insertError(errorVo);

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
		log.info(ext.getMessage());

		ErrorVo errorVo = new ErrorVo(request.getRequestURI(), DateUtil.getTodayTime(), ext.getMessage());
		errorDao.insertError(errorVo);

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
		log.info(ext.getMessage());

		ErrorVo errorVo = new ErrorVo(request.getRequestURI(), DateUtil.getTodayTime(), ext.getMessage());
		errorDao.insertError(errorVo);

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
