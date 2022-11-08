package com.singer.common.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.singer.common.util.CommonUtil;

@ControllerAdvice
public class CommonExceptionHandler {

    private static final Log log = LogFactory.getLog(ExceptionHandler.class);

    private ModelAndView getErrorModelAndView(boolean isAjax, @NonNull String message) {
        ModelAndView mv = null;
        mv = new ModelAndView("forward:/errors");
        mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
        mv.addObject("errorMsg", message);
        return mv;
    }

    @ExceptionHandler(ClientException.class)
    public ModelAndView clientExceptionHandler(HttpServletRequest request, HttpServletResponse response,
        ClientException ext) throws IOException {

        if (CommonUtil.ajaxCheck(request)) {
            Function<ClientException, ModelAndView> func = (ex) -> {
                ModelAndView mv = new ModelAndView("forward:/errors");
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

        boolean isAjax = CommonUtil.ajaxCheck(request);
        return getErrorModelAndView(isAjax, ext.getCause().getLocalizedMessage());
    }

    @ExceptionHandler(AppException.class)
    public ModelAndView appExceptionHandler(HttpServletRequest request, AppException ext) {
        log.info("AppException");
        if (StringUtils.isEmpty(ext.getMessage())) {
            return null;
        }
        log.info(ext.getMessage());

        return getErrorModelAndView(CommonUtil.ajaxCheck(request), ext.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ext) {

        log.info("NoHandlerFoundException");
        if (StringUtils.isEmpty(ext.getMessage())) {
            return null;
        }
        log.info(ext.getMessage());

        String errorURL = request.getRequestURL().toString();
        return getErrorModelAndView(CommonUtil.ajaxCheck(request), "error Url" + errorURL + " || " + ext.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response,
        MethodArgumentNotValidException ext) throws IOException {

        if (CommonUtil.ajaxCheck(request)) {
            Function<MethodArgumentNotValidException, ModelAndView> func = (ex) -> {
                ModelAndView mv = new ModelAndView("forward:/errors");
                mv.addObject("errorCode", HttpStatus.BAD_REQUEST);
                mv.addObject("errorMsg", makeValidErrorMessage(ex.getBindingResult()));
                return mv;
            };
            return func.apply(ext);
        } else {
            Function<MethodArgumentNotValidException, ModelAndView> func = (ex) -> {
                ModelAndView mv = new ModelAndView("forward:/" + HttpStatus.BAD_REQUEST.value());
                mv.addObject("errorCode", HttpStatus.BAD_REQUEST);
                mv.addObject("errorMsg", makeValidErrorMessage(ex.getBindingResult()));
                return mv;
            };
            return func.apply(ext);
        }
    }

    private String makeValidErrorMessage(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            // builder.append(fieldError.getField());
            builder.append(fieldError.getDefaultMessage());
            builder.append("]");
        }

        return builder.toString();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception ext) {
        log.info("defaultException");
        if (StringUtils.isEmpty(ext.getMessage())) {
            return null;
        }
        log.info(ext.getMessage());

        return getErrorModelAndView(CommonUtil.ajaxCheck(request), ext.getMessage());
    }
}
