package com.singer.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/sm**").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll().antMatchers("/main").permitAll().antMatchers("/sb**").permitAll()
				.antMatchers("/sf**").permitAll().antMatchers("/sr**").permitAll().antMatchers("/sv**").permitAll()
				.antMatchers("/chat/**").permitAll().antMatchers("/comm/*").permitAll();

	}

}
