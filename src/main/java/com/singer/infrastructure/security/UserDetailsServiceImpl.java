package com.singer.infrastructure.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.singer.domain.dao.sm.SM01Dao;
import com.singer.domain.entity.sm.SM01Entity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SM01Dao sm01Dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SM01Entity sm01Entity = new SM01Entity();
		sm01Entity.setUserid(username);
		SecurityUser securityUser = new SecurityUser();
		try {
			sm01Entity = sm01Dao.selectOneSM01Vo(sm01Entity);
			securityUser.setUserid(username);
			securityUser.setPassword(sm01Entity.getPasswd());
			securityUser.setUsername(sm01Entity.getUsername());
			securityUser.setEmail(sm01Entity.getEmail());
			securityUser.setGrade(sm01Entity.getGrade());
			securityUser.setUsertype(sm01Entity.getUsertype());
			securityUser.setRegdate(sm01Entity.getRegdate());
			securityUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("USER")));
		} catch (Exception e) {

		}

		return securityUser;
	}

}
