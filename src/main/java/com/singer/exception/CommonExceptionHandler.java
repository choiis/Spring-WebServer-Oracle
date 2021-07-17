package com.singer.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.singer.common.DateUtil;
import com.singer.kafka.Producer;
import com.singer.util.InputQueryUtil;

@ControllerAdvice
public class CommonExceptionHandler {
	private static final Log log = LogFactory.getLog(ExceptionHandler.class);

	@Inject
	private Producer producer;

	@ExceptionHandler(ClientException.class)
	public ModelAndView clientExceptionHandler(HttpServletRequest request, HttpServletResponse response,
			ClientException ext) throws IOException {
		boolean isAjax = false;

		if (StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		if (isAjax) {
			Function<ClientException, ModelAndView> func = (ex) -> {
				ModelAndView mv = new ModelAndView("forward:/error");
				mv.addObject("errorCode", ex.getHttpStatusCode());
				mv.addObject("errorMsg", ex.getLocalizedMessage());
				return mv;
			};
			return func.apply(ext);
		} else {
			Function<ClientException, ModelAndView> func = (ex) -> {
				ModelAndView mv = new ModelAndView("forward:/" + ex.getHttpStatusCode().value());
				mv.addObject("errorCode", ex.getHttpStatusCode());
				mv.addObject("errorMsg", ex.getLocalizedMessage());
				return mv;
			};
			return func.apply(ext);
		}
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "your message")
	public ModelAndView sQLExceptionHandler(HttpServletRequest request, SQLException ext) {
		boolean isAjax = false;
		log.info("SQLException");
		if (StringUtils.isEmpty(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		BiConsumer<String, String> queryConsumer = (uri, msg) -> {
			InputQueryUtil queryUtil = new InputQueryUtil("log_error");
			queryUtil.add(uri);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add(msg);
			producer.send(queryUtil.getQuery());
		};
		queryConsumer.accept(request.getRequestURI(), ext.getLocalizedMessage());

		if (StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("errorMsg", ext.getCause().getLocalizedMessage());
		return mv;
	}

	@ExceptionHandler(AppException.class)
	public ModelAndView appExceptionHandler(HttpServletRequest request, AppException ext) {
		boolean isAjax = false;
		log.info("AppException");
		if (StringUtils.isEmpty(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		if (StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("errorMsg", ext.getMessage());
		return mv;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ext) {
		boolean isAjax = false;
		log.info("NoHandlerFoundException");
		if (StringUtils.isEmpty(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		BiConsumer<String, String> queryConsumer = (uri, msg) -> {
			InputQueryUtil queryUtil = new InputQueryUtil("log_error");
			queryUtil.add(uri);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add(msg);
			producer.send(queryUtil.getQuery());
		};
		queryConsumer.accept(request.getRequestURI(), ext.getLocalizedMessage());

		String errorURL = request.getRequestURL().toString();
		if (StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("errorMsg", "error Url" + errorURL + " || " + ext.getMessage());
		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception ext) {
		boolean isAjax = false;
		log.info("defaultException");
		if (StringUtils.isEmpty(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		if (StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"))) {
			isAjax = true;
		}
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}

		mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("errorMsg", ext.getMessage());
		return mv;
	}
}
