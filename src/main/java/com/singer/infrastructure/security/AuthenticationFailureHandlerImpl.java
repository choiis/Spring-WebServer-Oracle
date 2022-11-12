package com.singer.infrastructure.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.singer.common.util.Constants.RESULT_CODE;

import lombok.extern.slf4j.Slf4j;

@Component@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		log.error(exception.getMessage());

		response.getWriter().write(RESULT_CODE.FAIL.name());
		response.getWriter().flush();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
