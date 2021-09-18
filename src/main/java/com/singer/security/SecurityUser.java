package com.singer.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.singer.common.Constants.USER_CODE;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SecurityUser implements UserDetails {

	private static final long serialVersionUID = 4645574684826254971L;

	private String username;
	private String userid;
	private String password;
	private USER_CODE grade;
	private USER_CODE usertype;
	private String email;
	private String regdate;

	private Collection<SimpleGrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
