package com.singer.infrastructure.security;

import com.singer.common.util.Constants.USER_CODE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class SecurityUser implements UserDetails {

    private String userid;
    private String password;
    private String username; // 사용자 이름 (실제 이름)
    private String email;
    private USER_CODE grade;
    private USER_CODE usertype;
    private String regdate;
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(String userid,
                        String password,
                        String username,
                        String email,
                        USER_CODE grade,
                        USER_CODE usertype,
                        String regdate,
                        Collection<? extends GrantedAuthority> authorities) {
        this.userid = userid;
        this.password = password;
        this.username = username;
        this.email = email;
        this.grade = grade;
        this.usertype = usertype;
        this.regdate = regdate;
        this.authorities = authorities;
    }

    public USER_CODE getUsertype() {
        return usertype;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Spring Security 가 사용하는 username 은 로그인 ID (userid) 로 매핑.
     */
    @Override
    public String getUsername() {
        return userid;
    }

    @Override
    public String getPassword() {
        return password;
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
