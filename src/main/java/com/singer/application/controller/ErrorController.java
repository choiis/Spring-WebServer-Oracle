package com.singer.application.controller;

import com.singer.application.dto.comm.ErrorResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Slf4j
public class ErrorController {

    // CommonExceptionHandler에서 500 Request들을 여기로 보낸다
    @RequestMapping(value = "/errors")
    public ResponseEntity<ErrorResponse> errorJson(HttpServletRequest request) {
        log.debug("server error");
        HttpStatus errorCode = (HttpStatus) request.getAttribute("errorCode");
        String errorMsg = (String) request.getAttribute("errorMsg");
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg);
        log.error("error " + errorMsg);
        return new ResponseEntity<>(errorResponse, errorCode);
    }


    @RequestMapping(value = "/400")
    public ResponseEntity<ErrorResponse> clientError400(HttpServletRequest request) {
        log.debug("client error 400");

        HttpStatus errorCode = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(errorCode, (String) request.getAttribute("errorMsg"));

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @RequestMapping(value = "/401")
    public ResponseEntity<ErrorResponse> clientError401() {
        log.debug("client error 401");

        HttpStatus errorCode = HttpStatus.UNAUTHORIZED;
        String errorMsg = HttpStatus.UNAUTHORIZED.toString();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg);

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @RequestMapping(value = "/403")
    public ResponseEntity<ErrorResponse> clientError403() {
        log.debug("client error 403");

        HttpStatus errorCode = HttpStatus.FORBIDDEN;
        String errorMsg = HttpStatus.FORBIDDEN.toString();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg);

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @RequestMapping(value = "/404")
    public ResponseEntity<ErrorResponse> clientError404() {
        log.debug("client error 404");

        HttpStatus errorCode = HttpStatus.NOT_FOUND;
        String errorMsg = HttpStatus.NOT_FOUND.toString();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg);

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @RequestMapping(value = "/405")
    public ResponseEntity<ErrorResponse> clientError405() {
        log.debug("client error 405");

        HttpStatus errorCode = HttpStatus.METHOD_NOT_ALLOWED;
        String errorMsg = HttpStatus.METHOD_NOT_ALLOWED.toString();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg);

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @RequestMapping(value = "/415")
    public ResponseEntity<ErrorResponse> clientError415() {
        log.debug("client error 415");

        HttpStatus errorCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        String errorMsg = HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg);

        return new ResponseEntity<>(errorResponse, errorCode);
    }


    @RequestMapping(value = "/forbiddenPage")
    public ModelAndView forbiddenPage() {
        log.debug("client error 403");

        ModelAndView mv = new ModelAndView("/clientError");
        HttpStatus errorCode = HttpStatus.FORBIDDEN;
        String errorMsg = HttpStatus.FORBIDDEN.toString();
        mv.addObject("errorCode", errorCode);
        mv.addObject("errorMsg", errorMsg);
        return mv;
    }
}
