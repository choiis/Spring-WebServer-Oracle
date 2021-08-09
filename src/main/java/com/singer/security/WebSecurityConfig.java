package com.singer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Autowired
	private AuthenticationFailureHandler failureHandler;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowBackSlash(true);
		firewall.setAllowUrlEncodedDoubleSlash(true);
		return firewall;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall()).ignoring().antMatchers("/", "/resources/**", "/logout",
				"/sm**", "/main", "/sb**", "/sb**", "/sf**", "/sr**", "/sv**", "/chat/**", "/comm/*", "/index");

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()

				// .antMatchers("/", "/sm**", "/main", "/sb**", "/sb**", "/sf**", "/sr**",
				// "/sv**", "/chat/**", "/comm/*")
				.antMatchers("/main", "/index").permitAll().and().csrf().disable()

				.formLogin().loginPage("/").loginProcessingUrl("/login").failureForwardUrl("/")
				.usernameParameter("userid").passwordParameter("passwd").successHandler(successHandler)
				.failureHandler(failureHandler).and().authenticationProvider(authenticationProvider)

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logoutUrl", "POST"))
				.logoutSuccessUrl("/index").invalidateHttpSession(true).permitAll();

	}


}
