package com.singer.security;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.singer.service.SM01Service;
import com.singer.vo.SM01Vo;

@Component
public class AuthProvider implements AuthenticationProvider {

	@Inject
	private SM01Service sm01Service;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userid = authentication.getName();
		String passwd = (String) authentication.getCredentials();

		SM01Vo sm01Vo = new SM01Vo();
		try {
			sm01Vo.setUserid(userid);
			sm01Vo = Optional.of(sm01Service.login(sm01Vo))
					.orElseThrow(() -> new BadCredentialsException("account not found"));

			if (StringUtils.equals(passwd, sm01Vo.getPasswd())) {
				return new UsernamePasswordAuthenticationToken(userid, passwd);
			} else {
				throw new BadCredentialsException("password mismatch");
			}
		} catch (Exception e) {
			throw new BadCredentialsException("SQL fail");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
