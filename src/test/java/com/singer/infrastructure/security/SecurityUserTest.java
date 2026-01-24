package com.singer.infrastructure.security;

import com.singer.common.util.Constants.USER_CODE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserTest {

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTest {

        @Test
        @DisplayName("기본 생성자로 인스턴스 생성")
        void noArgsConstructor_createsInstance() {
            SecurityUser user = new SecurityUser();

            assertNotNull(user);
            assertNull(user.getUserid());
            assertNull(user.getPassword());
        }

        @Test
        @DisplayName("전체 인자 생성자로 인스턴스 생성")
        void allArgsConstructor_createsInstanceWithAllFields() {
            Collection<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            SecurityUser user = new SecurityUser(
                "userid123",
                "password123",
                "홍길동",
                "test@example.com",
                USER_CODE.NORMAL,
                USER_CODE.NORMAL,
                "20231225",
                authorities
            );

            assertEquals("userid123", user.getUserid());
            assertEquals("password123", user.getPassword());
            assertEquals("userid123", user.getUsername()); // getUsername()은 Spring Security 표준에 따라 userid를 반환
            assertEquals("test@example.com", user.getEmail());
            assertEquals(USER_CODE.NORMAL, user.getGrade());
            assertEquals(USER_CODE.NORMAL, user.getUsertype());
            assertEquals("20231225", user.getRegdate());
            assertEquals(authorities, user.getAuthorities());
        }
    }

    @Nested
    @DisplayName("UserDetails 인터페이스 구현 테스트")
    class UserDetailsImplementationTest {

        @Test
        @DisplayName("getUsername()은 userid를 반환")
        void getUsername_returnsUserid() {
            SecurityUser user = new SecurityUser();
            user.setUserid("testuser");
            user.setUsername("테스트사용자"); // 실제 이름

            // Spring Security의 getUsername()은 userid를 반환해야 함
            assertEquals("testuser", user.getUsername());
        }

        @Test
        @DisplayName("getPassword()는 비밀번호 반환")
        void getPassword_returnsPassword() {
            SecurityUser user = new SecurityUser();
            user.setPassword("secret123");

            assertEquals("secret123", user.getPassword());
        }

        @Test
        @DisplayName("getAuthorities()는 권한 목록 반환")
        void getAuthorities_returnsAuthorities() {
            List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            SecurityUser user = new SecurityUser();
            user.setAuthorities(authorities);

            assertEquals(2, user.getAuthorities().size());
            assertTrue(user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
            assertTrue(user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }

        @Test
        @DisplayName("isAccountNonExpired()는 항상 true 반환")
        void isAccountNonExpired_returnsTrue() {
            SecurityUser user = new SecurityUser();

            assertTrue(user.isAccountNonExpired());
        }

        @Test
        @DisplayName("isAccountNonLocked()는 항상 true 반환")
        void isAccountNonLocked_returnsTrue() {
            SecurityUser user = new SecurityUser();

            assertTrue(user.isAccountNonLocked());
        }

        @Test
        @DisplayName("isCredentialsNonExpired()는 항상 true 반환")
        void isCredentialsNonExpired_returnsTrue() {
            SecurityUser user = new SecurityUser();

            assertTrue(user.isCredentialsNonExpired());
        }

        @Test
        @DisplayName("isEnabled()는 항상 true 반환")
        void isEnabled_returnsTrue() {
            SecurityUser user = new SecurityUser();

            assertTrue(user.isEnabled());
        }
    }

    @Nested
    @DisplayName("Getter/Setter 테스트")
    class GetterSetterTest {

        @Test
        @DisplayName("userid getter/setter 동작 확인")
        void userid_getterSetter_works() {
            SecurityUser user = new SecurityUser();

            user.setUserid("newuserid");

            assertEquals("newuserid", user.getUserid());
        }

        @Test
        @DisplayName("email getter/setter 동작 확인")
        void email_getterSetter_works() {
            SecurityUser user = new SecurityUser();

            user.setEmail("new@email.com");

            assertEquals("new@email.com", user.getEmail());
        }

        @Test
        @DisplayName("grade getter/setter 동작 확인")
        void grade_getterSetter_works() {
            SecurityUser user = new SecurityUser();

            user.setGrade(USER_CODE.ADMIN);

            assertEquals(USER_CODE.ADMIN, user.getGrade());
        }

        @Test
        @DisplayName("usertype getter/setter 동작 확인")
        void usertype_getterSetter_works() {
            SecurityUser user = new SecurityUser();

            user.setUsertype(USER_CODE.SPECIAL);

            assertEquals(USER_CODE.SPECIAL, user.getUsertype());
        }

        @Test
        @DisplayName("regdate getter/setter 동작 확인")
        void regdate_getterSetter_works() {
            SecurityUser user = new SecurityUser();

            user.setRegdate("20231231");

            assertEquals("20231231", user.getRegdate());
        }
    }

    @Nested
    @DisplayName("USER_CODE 테스트")
    class UserCodeTest {

        @Test
        @DisplayName("ADMIN usertype 설정 확인")
        void usertype_admin_works() {
            SecurityUser user = new SecurityUser();
            user.setUsertype(USER_CODE.ADMIN);

            assertEquals(USER_CODE.ADMIN, user.getUsertype());
            assertEquals(1, user.getUsertype().getValue());
        }

        @Test
        @DisplayName("다양한 grade 설정 확인")
        void grade_allTypes_work() {
            SecurityUser user = new SecurityUser();

            for (USER_CODE code : USER_CODE.values()) {
                user.setGrade(code);
                assertEquals(code, user.getGrade());
            }
        }
    }

    @Nested
    @DisplayName("복합 시나리오 테스트")
    class ComplexScenarioTest {

        @Test
        @DisplayName("관리자 사용자 생성 시나리오")
        void createAdminUser_scenario() {
            List<GrantedAuthority> adminAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
            );

            SecurityUser admin = new SecurityUser(
                "admin",
                "admin1234",
                "관리자",
                "admin@example.com",
                USER_CODE.ADMIN,
                USER_CODE.ADMIN,
                "20231101",
                adminAuthorities
            );

            assertEquals("admin", admin.getUsername()); // userid 반환
            assertEquals(USER_CODE.ADMIN, admin.getUsertype());
            assertEquals(2, admin.getAuthorities().size());
            assertTrue(admin.isEnabled());
            assertTrue(admin.isAccountNonLocked());
        }

        @Test
        @DisplayName("일반 사용자 생성 시나리오")
        void createNormalUser_scenario() {
            List<GrantedAuthority> userAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
            );

            SecurityUser normalUser = new SecurityUser(
                "user01",
                "pass1234",
                "일반사용자",
                "user@example.com",
                USER_CODE.NORMAL,
                USER_CODE.NORMAL,
                "20231201",
                userAuthorities
            );

            assertEquals("user01", normalUser.getUsername());
            assertEquals(USER_CODE.NORMAL, normalUser.getUsertype());
            assertEquals(1, normalUser.getAuthorities().size());
        }
    }
}
