package com.singer.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.singer.dao.SM01Dao;
import com.singer.vo.SM01Vo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SM01Dao sm01Dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SM01Vo sm01Vo = new SM01Vo();
		sm01Vo.setUserid(username);
		SecurityUser securityUser = new SecurityUser();
		try {
			sm01Vo = sm01Dao.selectOneSM01Vo(sm01Vo);
			securityUser.setUserid(username);
			securityUser.setPassword(sm01Vo.getPasswd());
			securityUser.setUsername(sm01Vo.getUsername());
			securityUser.setEmail(sm01Vo.getEmail());
			securityUser.setGrade(sm01Vo.getGrade());
			securityUser.setUsertype(sm01Vo.getUsertype());
			securityUser.setRegdate(sm01Vo.getRegdate());
			securityUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("USER")));
		} catch (Exception e) {

		}

		return securityUser;
	}

}
