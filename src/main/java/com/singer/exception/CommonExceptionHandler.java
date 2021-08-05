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
import org.springframework.lang.NonNull;
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

	private boolean ajaxCheck(HttpServletRequest request) {

		return StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With")) ? true : false;
	}

	private ModelAndView getErrorModelAndView(boolean isAjax, @NonNull String message) {
		ModelAndView mv = null;
		if (isAjax) {
			mv = new ModelAndView("forward:/error");
		} else {
			mv = new ModelAndView("forward:/forwardError");
		}
		mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
		mv.addObject("errorMsg", message);
		return mv;
	}

	@ExceptionHandler(ClientException.class)
	public ModelAndView clientExceptionHandler(HttpServletRequest request, HttpServletResponse response,
			ClientException ext) throws IOException {
		boolean isAjax = ajaxCheck(request);

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

		boolean isAjax = ajaxCheck(request);
		return getErrorModelAndView(isAjax, ext.getCause().getLocalizedMessage());
	}

	@ExceptionHandler(AppException.class)
	public ModelAndView appExceptionHandler(HttpServletRequest request, AppException ext) {
		log.info("AppException");
		if (StringUtils.isEmpty(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		boolean isAjax = ajaxCheck(request);
		return getErrorModelAndView(isAjax, ext.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ext) {

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
		boolean isAjax = ajaxCheck(request);
		return getErrorModelAndView(isAjax, "error Url" + errorURL + " || " + ext.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception ext) {
		log.info("defaultException");
		if (StringUtils.isEmpty(ext.getMessage())) {
			return null;
		}
		log.info(ext.getMessage());

		boolean isAjax = ajaxCheck(request);
		return getErrorModelAndView(isAjax, ext.getMessage());
	}
}
