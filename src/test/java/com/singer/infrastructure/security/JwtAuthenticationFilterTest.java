package com.singer.infrastructure.security;

import com.singer.common.util.Constants.USER_CODE;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private static final String TEST_SECRET = "dGhpc19pc19hX3NhbXBsZV9qd3Rfc2VjcmV0X2tleV9mb3JfZGVtbw==";
    private static final long EXPIRATION_SECONDS = 3600;

    @BeforeEach
    void setUp() {
        JwtProperties properties = new JwtProperties(TEST_SECRET, EXPIRATION_SECONDS);
        jwtService = new JwtService(properties);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Nested
    @DisplayName("Authorization 헤더 없는 경우")
    class NoAuthorizationHeaderTest {

        @Test
        @DisplayName("Authorization 헤더가 없으면 필터 통과")
        void doFilterInternal_withNoAuthHeader_passesFilter() throws ServletException, IOException {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }

        @Test
        @DisplayName("Authorization 헤더가 Bearer로 시작하지 않으면 필터 통과")
        void doFilterInternal_withNonBearerAuth_passesFilter() throws ServletException, IOException {
            request.addHeader("Authorization", "Basic sometoken");

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }
    }

    @Nested
    @DisplayName("유효한 토큰 테스트")
    class ValidTokenTest {

        @Test
        @DisplayName("유효한 토큰이면 인증 설정")
        void doFilterInternal_withValidToken_setsAuthentication() throws ServletException, IOException {
            String username = "testuser";
            String token = jwtService.generateToken(username, USER_CODE.NORMAL);
            request.addHeader("Authorization", "Bearer " + token);

            UserDetails userDetails = new User(
                username,
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertNotNull(authentication);
            assertEquals(username, authentication.getName());
        }

        @Test
        @DisplayName("ADMIN 권한 토큰 처리")
        void doFilterInternal_withAdminToken_setsAdminAuthentication() throws ServletException, IOException {
            String username = "admin";
            String token = jwtService.generateToken(username, USER_CODE.ADMIN);
            request.addHeader("Authorization", "Bearer " + token);

            UserDetails userDetails = new User(
                username,
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertNotNull(authentication);
            assertTrue(authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }
    }

    @Nested
    @DisplayName("잘못된 토큰 테스트")
    class InvalidTokenTest {

        @Test
        @DisplayName("잘못된 형식의 토큰이면 인증 설정 안 함")
        void doFilterInternal_withMalformedToken_noAuthentication() throws ServletException, IOException {
            request.addHeader("Authorization", "Bearer invalid.token.here");

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }

        @Test
        @DisplayName("만료된 토큰이면 인증 설정 안 함")
        void doFilterInternal_withExpiredToken_noAuthentication() throws ServletException, IOException {
            // 만료 시간이 0초인 JwtService 생성
            JwtProperties expiredProperties = new JwtProperties(TEST_SECRET, 0);
            JwtService expiredJwtService = new JwtService(expiredProperties);
            JwtAuthenticationFilter expiredFilter = new JwtAuthenticationFilter(expiredJwtService, userDetailsService);

            String token = expiredJwtService.generateToken("testuser", USER_CODE.NORMAL);
            request.addHeader("Authorization", "Bearer " + token);

            expiredFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }

        @Test
        @DisplayName("빈 토큰이면 필터 통과")
        void doFilterInternal_withEmptyToken_passesFilter() throws ServletException, IOException {
            request.addHeader("Authorization", "Bearer ");

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }
    }

    @Nested
    @DisplayName("이미 인증된 컨텍스트 테스트")
    class AlreadyAuthenticatedTest {

        @Test
        @DisplayName("이미 인증된 상태면 재인증 안 함")
        void doFilterInternal_alreadyAuthenticated_skipsAuthentication() throws ServletException, IOException {
            // 기존 인증 설정
            UserDetails existingUser = new User(
                "existinguser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            org.springframework.security.authentication.UsernamePasswordAuthenticationToken existingAuth =
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                    existingUser, null, existingUser.getAuthorities()
                );
            SecurityContextHolder.getContext().setAuthentication(existingAuth);

            // 다른 사용자의 토큰으로 요청
            String token = jwtService.generateToken("differentuser", USER_CODE.NORMAL);
            request.addHeader("Authorization", "Bearer " + token);

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            // UserDetailsService가 호출되지 않아야 함
            verify(userDetailsService, never()).loadUserByUsername(anyString());
            // 기존 인증이 유지되어야 함
            assertEquals("existinguser", SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

    @Nested
    @DisplayName("토큰 검증 실패 테스트")
    class TokenValidationFailureTest {

        @Test
        @DisplayName("토큰의 사용자가 DB에 없으면 UsernameNotFoundException 발생")
        void doFilterInternal_userNotFound_throwsException() {
            String token = jwtService.generateToken("nonexistent", USER_CODE.NORMAL);
            request.addHeader("Authorization", "Bearer " + token);

            when(userDetailsService.loadUserByUsername("nonexistent"))
                .thenThrow(new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found"));

            // 현재 구현에서는 UsernameNotFoundException을 catch하지 않아 예외가 전파됨
            assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class, () ->
                jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
        }
    }
}
