package com.singer.infrastructure.security;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.dao.sm.SM01Dao;
import com.singer.domain.entity.sm.SM01Entity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SM01Dao sm01Dao;

    public UserDetailsServiceImpl(SM01Dao sm01Dao) {
        this.sm01Dao = sm01Dao;
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        SM01Entity criteria = new SM01Entity();
        criteria.setUserid(userid);

        SM01Entity sm01Entity;
        try {
            sm01Entity = sm01Dao.selectLoginSM01Vo(criteria);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to load user: " + userid, e);
        }

        if (sm01Entity == null) {
            throw new UsernameNotFoundException("User not found: " + userid);
        }

        USER_CODE grade = sm01Entity.getGrade();
        USER_CODE usertype = sm01Entity.getUsertype();

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserid(sm01Entity.getUserid());
        securityUser.setPassword(sm01Entity.getPasswd());
        securityUser.setUsername(sm01Entity.getUsername());
        securityUser.setEmail(sm01Entity.getEmail());
        securityUser.setGrade(grade);
        securityUser.setUsertype(usertype);
        securityUser.setRegdate(sm01Entity.getRegdate());
        securityUser.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        return securityUser;
    }
}
