package com.singer.infrastructure.security;

import com.singer.common.util.Constants.USER_CODE;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String TEST_SECRET = "dGhpc19pc19hX3NhbXBsZV9qd3Rfc2VjcmV0X2tleV9mb3JfZGVtbw==";
    private static final long EXPIRATION_SECONDS = 3600;

    @BeforeEach
    void setUp() {
        JwtProperties properties = new JwtProperties(TEST_SECRET, EXPIRATION_SECONDS);
        jwtService = new JwtService(properties);
    }

    @Nested
    @DisplayName("generateToken() 메서드 테스트")
    class GenerateTokenTest {

        @Test
        @DisplayName("유효한 사용자 정보로 토큰 생성")
        void generateToken_withValidUser_returnsToken() {
            String token = jwtService.generateToken("testuser", USER_CODE.NORMAL);

            assertNotNull(token);
            assertFalse(token.isEmpty());
            // JWT는 3개의 부분으로 구성됨 (header.payload.signature)
            assertEquals(3, token.split("\\.").length);
        }

        @Test
        @DisplayName("userType이 null이면 NORMAL로 설정")
        void generateToken_withNullUserType_usesNormal() {
            String token = jwtService.generateToken("testuser", null);

            assertNotNull(token);
            String username = jwtService.extractUsername(token);
            assertEquals("testuser", username);
        }

        @Test
        @DisplayName("ADMIN 권한으로 토큰 생성")
        void generateToken_withAdminRole_returnsToken() {
            String token = jwtService.generateToken("admin", USER_CODE.ADMIN);

            assertNotNull(token);
            assertEquals("admin", jwtService.extractUsername(token));
        }

        @Test
        @DisplayName("각 userType별로 토큰 생성 가능")
        void generateToken_withDifferentUserTypes_allSucceed() {
            for (USER_CODE userType : USER_CODE.values()) {
                String token = jwtService.generateToken("user", userType);
                assertNotNull(token);
            }
        }
    }

    @Nested
    @DisplayName("extractUsername() 메서드 테스트")
    class ExtractUsernameTest {

        @Test
        @DisplayName("토큰에서 사용자명 추출")
        void extractUsername_withValidToken_returnsUsername() {
            String token = jwtService.generateToken("testuser", USER_CODE.NORMAL);

            String username = jwtService.extractUsername(token);

            assertEquals("testuser", username);
        }

        @Test
        @DisplayName("한글 사용자명도 추출 가능")
        void extractUsername_withKoreanUsername_returnsUsername() {
            String token = jwtService.generateToken("테스트유저", USER_CODE.NORMAL);

            String username = jwtService.extractUsername(token);

            assertEquals("테스트유저", username);
        }

        @Test
        @DisplayName("잘못된 토큰 형식이면 예외 발생")
        void extractUsername_withInvalidToken_throwsException() {
            assertThrows(MalformedJwtException.class, () -> {
                jwtService.extractUsername("invalid.token.format");
            });
        }
    }

    @Nested
    @DisplayName("isTokenValid() 메서드 테스트")
    class IsTokenValidTest {

        @Test
        @DisplayName("유효한 토큰과 일치하는 UserDetails면 true 반환")
        void isTokenValid_withValidTokenAndMatchingUser_returnsTrue() {
            String token = jwtService.generateToken("testuser", USER_CODE.NORMAL);
            UserDetails userDetails = new User(
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

            boolean isValid = jwtService.isTokenValid(token, userDetails);

            assertTrue(isValid);
        }

        @Test
        @DisplayName("토큰의 사용자명과 UserDetails가 다르면 false 반환")
        void isTokenValid_withMismatchedUser_returnsFalse() {
            String token = jwtService.generateToken("testuser", USER_CODE.NORMAL);
            UserDetails userDetails = new User(
                "differentuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

            boolean isValid = jwtService.isTokenValid(token, userDetails);

            assertFalse(isValid);
        }

        @Test
        @DisplayName("만료된 토큰은 예외 발생")
        void isTokenValid_withExpiredToken_throwsException() {
            // 만료 시간이 0초인 JwtService 생성
            JwtProperties expiredProperties = new JwtProperties(TEST_SECRET, 0);
            JwtService expiredJwtService = new JwtService(expiredProperties);
            String token = expiredJwtService.generateToken("testuser", USER_CODE.NORMAL);

            UserDetails userDetails = new User(
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

            // 토큰이 즉시 만료되므로 예외 발생
            assertThrows(ExpiredJwtException.class, () -> {
                expiredJwtService.isTokenValid(token, userDetails);
            });
        }
    }

    @Nested
    @DisplayName("토큰 일관성 테스트")
    class TokenConsistencyTest {

        @Test
        @DisplayName("같은 사용자로 생성한 토큰도 매번 다름 (시간 포함)")
        void generateToken_samePurpose_differentTokens() throws InterruptedException {
            String token1 = jwtService.generateToken("testuser", USER_CODE.NORMAL);
            Thread.sleep(1000); // 1초 대기
            String token2 = jwtService.generateToken("testuser", USER_CODE.NORMAL);

            // 토큰은 다르지만 사용자명은 같음
            assertNotEquals(token1, token2);
            assertEquals(
                jwtService.extractUsername(token1),
                jwtService.extractUsername(token2)
            );
        }

        @Test
        @DisplayName("토큰 생성 후 바로 검증 가능")
        void generateAndValidateToken_immediately_works() {
            String username = "immediateuser";
            String token = jwtService.generateToken(username, USER_CODE.NORMAL);

            UserDetails userDetails = new User(
                username,
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

            assertTrue(jwtService.isTokenValid(token, userDetails));
            assertEquals(username, jwtService.extractUsername(token));
        }
    }
}
