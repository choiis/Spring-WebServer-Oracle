package com.singer.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.singer.common.Constants.RESULT_CODE;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

	private final Log log = LogFactory.getLog(AuthenticationFailureHandlerImpl.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		log.error(exception.getMessage());

		response.getWriter().write(RESULT_CODE.FAIL.name());
		response.getWriter().flush();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
