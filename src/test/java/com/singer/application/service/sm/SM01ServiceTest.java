package com.singer.application.service.sm;

import com.singer.application.dto.sm.SM01UpdateRequest;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.util.Constants;
import com.singer.common.util.Constants.PHONE_INFO_CODE;
import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.dao.sm.SM01Dao;
import com.singer.domain.entity.sm.SM01Entity;
import jakarta.servlet.http.HttpServletRequest;
import oracle.sql.BLOB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SM01ServiceTest {

    @Mock
    private SM01Dao sm01Dao;

    @InjectMocks
    private SM01Service sm01Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSM01Vo() 메서드 테스트")
    class InsertSM01VoTest {

        @Mock
        private MultipartHttpServletRequest multipartRequest;

        @Mock
        private MultipartFile photoFile;

        @Test
        @DisplayName("사용자 등록 성공")
        void insertSM01Vo_success() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid(TEST_USER);
            sm01Vo.setUsername("테스트유저");
            sm01Vo.setPasswd("password123");

            Iterator<String> fileNames = Collections.singletonList("photo").iterator();

            when(multipartRequest.getFileNames()).thenReturn(fileNames);
            when(multipartRequest.getFile("photo")).thenReturn(photoFile);
            when(photoFile.getOriginalFilename()).thenReturn("profile.jpg");
            when(photoFile.getBytes()).thenReturn("image content".getBytes());
            when(sm01Dao.insertSM01Vo(any(SM01Entity.class))).thenReturn(1);

            // when
            HashMap<String, Object> result = sm01Service.insertSM01Vo(sm01Vo, multipartRequest);

            // then
            assertNotNull(result);
            verify(sm01Dao).insertSM01Vo(any(SM01Entity.class));
            verify(sm01Dao).insertSMI1Vo(any(SM01Entity.class));
            verify(sm01Dao).insertImage(any(HashMap.class));
        }

        @Test
        @DisplayName("파일이 없으면 예외 발생")
        void insertSM01Vo_noFile_throwsException() {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid(TEST_USER);

            Iterator<String> emptyIterator = Collections.emptyIterator();
            when(multipartRequest.getFileNames()).thenReturn(emptyIterator);

            // when & then - 파일이 없으면 NullPointerException 발생
            assertThrows(Exception.class, () ->
                    sm01Service.insertSM01Vo(sm01Vo, multipartRequest));
        }

        @Test
        @DisplayName("이미지 파일이 아니면 AppException 발생")
        void insertSM01Vo_notImageFile_throwsException() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid(TEST_USER);

            Iterator<String> fileNames = Collections.singletonList("photo").iterator();

            when(multipartRequest.getFileNames()).thenReturn(fileNames);
            when(multipartRequest.getFile("photo")).thenReturn(photoFile);
            when(photoFile.getOriginalFilename()).thenReturn("document.pdf");

            // when & then
            assertThrows(AppException.class, () ->
                    sm01Service.insertSM01Vo(sm01Vo, multipartRequest));
        }
    }

    @Nested
    @DisplayName("selectSM01Vo() 메서드 테스트")
    class SelectSM01VoTest {

        @Test
        @DisplayName("사용자 목록 조회 성공")
        void selectSM01Vo_success() throws Exception {
            // given
            SM01Entity searchVo = new SM01Entity();
            searchVo.setNowPage(1);

            SM01Entity user1 = new SM01Entity();
            user1.setUserid("user1");
            user1.setUsername("유저1");

            SM01Entity user2 = new SM01Entity();
            user2.setUserid("user2");
            user2.setUsername("유저2");

            List<SM01Entity> list = Arrays.asList(user1, user2);

            when(sm01Dao.selectSM01Vo(any(SM01Entity.class))).thenReturn(list);

            // when
            List<SM01Entity> result = sm01Service.selectSM01Vo(searchVo);

            // then
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(sm01Dao).selectSM01Vo(any(SM01Entity.class));
        }

        @Test
        @DisplayName("빈 목록 조회")
        void selectSM01Vo_emptyList() throws Exception {
            // given
            SM01Entity searchVo = new SM01Entity();
            searchVo.setNowPage(1);

            when(sm01Dao.selectSM01Vo(any(SM01Entity.class))).thenReturn(Collections.emptyList());

            // when
            List<SM01Entity> result = sm01Service.selectSM01Vo(searchVo);

            // then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("selectOneSM01Vo() 메서드 테스트")
    class SelectOneSM01VoTest {

        @Test
        @DisplayName("사용자 상세 조회 성공 - 휴대폰 정보 포함")
        void selectOneSM01Vo_withCellPhone_success() throws Exception {
            // given
            SM01Entity searchVo = new SM01Entity();
            searchVo.setUserid(TEST_USER);

            SM01Entity resultEntity = new SM01Entity();
            resultEntity.setUserid(TEST_USER);
            resultEntity.setUsername("테스트유저");

            SM01Entity phoneInfo = new SM01Entity();
            phoneInfo.setInfocode(PHONE_INFO_CODE.CELL);
            phoneInfo.setPfnum("010");
            phoneInfo.setPcnum("1234");
            phoneInfo.setPbnum("5678");

            when(sm01Dao.selectOneSM01Vo(any(SM01Entity.class))).thenReturn(resultEntity);
            when(sm01Dao.selectSMI1Vo(any(SM01Entity.class))).thenReturn(Collections.singletonList(phoneInfo));

            // when
            SM01Entity result = sm01Service.selectOneSM01Vo(searchVo);

            // then
            assertNotNull(result);
            assertEquals("010", result.getCellpfnum());
            assertEquals("1234", result.getCellpcnum());
            assertEquals("5678", result.getCellpbnum());
        }

        @Test
        @DisplayName("사용자 상세 조회 성공 - 다양한 전화번호 정보")
        void selectOneSM01Vo_withAllPhoneTypes_success() throws Exception {
            // given
            SM01Entity searchVo = new SM01Entity();
            searchVo.setUserid(TEST_USER);

            SM01Entity resultEntity = new SM01Entity();
            resultEntity.setUserid(TEST_USER);

            SM01Entity cellPhone = new SM01Entity();
            cellPhone.setInfocode(PHONE_INFO_CODE.CELL);
            cellPhone.setPfnum("010");
            cellPhone.setPcnum("1234");
            cellPhone.setPbnum("5678");

            SM01Entity homePhone = new SM01Entity();
            homePhone.setInfocode(PHONE_INFO_CODE.HOME);
            homePhone.setPfnum("02");
            homePhone.setPcnum("123");
            homePhone.setPbnum("4567");

            SM01Entity companyPhone = new SM01Entity();
            companyPhone.setInfocode(PHONE_INFO_CODE.COMPANY);
            companyPhone.setPfnum("031");
            companyPhone.setPcnum("987");
            companyPhone.setPbnum("6543");

            SM01Entity otherPhone = new SM01Entity();
            otherPhone.setInfocode(PHONE_INFO_CODE.OTHER);
            otherPhone.setPfnum("070");
            otherPhone.setPcnum("111");
            otherPhone.setPbnum("2222");

            List<SM01Entity> phoneList = Arrays.asList(cellPhone, homePhone, companyPhone, otherPhone);

            when(sm01Dao.selectOneSM01Vo(any(SM01Entity.class))).thenReturn(resultEntity);
            when(sm01Dao.selectSMI1Vo(any(SM01Entity.class))).thenReturn(phoneList);

            // when
            SM01Entity result = sm01Service.selectOneSM01Vo(searchVo);

            // then
            assertNotNull(result);
            assertEquals("010", result.getCellpfnum());
            assertEquals("02", result.getHomepfnum());
            assertEquals("031", result.getCompanypfnum());
            assertEquals("070", result.getOtherpfnum());
        }
    }

    @Nested
    @DisplayName("login() 메서드 테스트")
    class LoginTest {

        @Test
        @DisplayName("로그인 성공")
        void login_success() throws Exception {
            // given
            SM01Entity loginVo = new SM01Entity();
            loginVo.setUserid(TEST_USER);
            loginVo.setPasswd("password123");

            SM01Entity resultEntity = new SM01Entity();
            resultEntity.setUserid(TEST_USER);
            resultEntity.setUsername("테스트유저");
            resultEntity.setGrade(USER_CODE.NORMAL);

            when(sm01Dao.selectLoginSM01Vo(any(SM01Entity.class))).thenReturn(resultEntity);

            // when
            SM01Entity result = sm01Service.login(loginVo);

            // then
            assertNotNull(result);
            assertEquals(TEST_USER, result.getUserid());
            verify(sm01Dao).selectLoginSM01Vo(loginVo);
        }

        @Test
        @DisplayName("로그인 실패 - 사용자 없음")
        void login_userNotFound() throws Exception {
            // given
            SM01Entity loginVo = new SM01Entity();
            loginVo.setUserid("nonexistent");
            loginVo.setPasswd("password");

            when(sm01Dao.selectLoginSM01Vo(any(SM01Entity.class))).thenReturn(null);

            // when
            SM01Entity result = sm01Service.login(loginVo);

            // then
            assertNull(result);
        }
    }

    @Nested
    @DisplayName("deleteSM01Vo() 메서드 테스트")
    class DeleteSM01VoTest {

        @Test
        @DisplayName("사용자 삭제 성공")
        void deleteSM01Vo_success() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid(TEST_USER);

            when(sm01Dao.deleteSM01Vo(any(SM01Entity.class))).thenReturn(1);

            // when
            int result = sm01Service.deleteSM01Vo(sm01Vo);

            // then
            assertEquals(1, result);
            verify(sm01Dao).deleteSM01Vo(sm01Vo);
        }
    }

    @Nested
    @DisplayName("selectImage() 메서드 테스트")
    class SelectImageTest {

        @Mock
        private HttpServletRequest request;

        @Mock
        private BLOB blob;

        @Test
        @DisplayName("이미지 조회 성공")
        void selectImage_success() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid(TEST_USER);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("PHOTO", blob);

            InputStream mockStream = new ByteArrayInputStream("image".getBytes());

            when(sm01Dao.selectImage(any(SM01Entity.class))).thenReturn(hashMap);
            when(blob.getBinaryStream()).thenReturn(mockStream);

            // when
            InputStream result = sm01Service.selectImage(sm01Vo, request);

            // then
            assertNotNull(result);
        }

        @Test
        @DisplayName("이미지가 없으면 ClientException 발생")
        void selectImage_notFound_throwsException() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid(TEST_USER);

            when(sm01Dao.selectImage(any(SM01Entity.class))).thenReturn(new HashMap<>());

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sm01Service.selectImage(sm01Vo, request));
            assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("updateSM01() 메서드 테스트")
    class UpdateSM01Test {

        @Test
        @DisplayName("사용자 정보 수정 성공 - 비밀번호 포함")
        void updateSM01_withPassword_success() throws Exception {
            // given
            SM01UpdateRequest request = new SM01UpdateRequest("홍길동", "19900101", "test@email.com", "newpassword");

            // when
            sm01Service.updateSM01(TEST_USER, request);

            // then
            verify(sm01Dao).updateSM01Vo(any(SM01Entity.class));
        }

        @Test
        @DisplayName("사용자 정보 수정 성공 - 비밀번호 없음")
        void updateSM01_withoutPassword_success() throws Exception {
            // given
            SM01UpdateRequest request = new SM01UpdateRequest("홍길동", "19900101", "test@email.com", null);

            // when
            sm01Service.updateSM01(TEST_USER, request);

            // then
            verify(sm01Dao).updateSM01Vo(any(SM01Entity.class));
        }
    }

    @Nested
    @DisplayName("updateSME1Vo() 메서드 테스트")
    class UpdateSME1VoTest {

        @Test
        @DisplayName("ADMIN으로 권한 변경 시 SME1 등록")
        void updateSME1Vo_toAdmin_success() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid("targetuser");
            sm01Vo.setUsertype(Constants.USER_CODE.ADMIN);

            // when
            int result = sm01Service.updateSME1Vo(sm01Vo, TEST_USER);

            // then
            assertEquals(0, result);
            verify(sm01Dao).updateUserType(any(SM01Entity.class));
            verify(sm01Dao).insertSME1Vo(any(SM01Entity.class));
            verify(sm01Dao, never()).deleteSME1Vo(any(SM01Entity.class));
        }

        @Test
        @DisplayName("NORMAL로 권한 변경 시 SME1 삭제")
        void updateSME1Vo_toNormal_success() throws Exception {
            // given
            SM01Entity sm01Vo = new SM01Entity();
            sm01Vo.setUserid("targetuser");
            sm01Vo.setUsertype(Constants.USER_CODE.NORMAL);

            // when
            int result = sm01Service.updateSME1Vo(sm01Vo, TEST_USER);

            // then
            assertEquals(0, result);
            verify(sm01Dao).updateUserType(any(SM01Entity.class));
            verify(sm01Dao, never()).insertSME1Vo(any(SM01Entity.class));
            verify(sm01Dao).deleteSME1Vo(any(SM01Entity.class));
        }
    }
}
