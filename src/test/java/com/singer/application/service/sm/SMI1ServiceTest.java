package com.singer.application.service.sm;

import com.singer.application.dto.sm.SMI1ListResponse;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sm.SMI1Dao;
import com.singer.domain.entity.sm.SM01Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SMI1ServiceTest {

    @Mock
    private SMI1Dao smi1Dao;

    @InjectMocks
    private SMI1Service smi1Service;

    @Nested
    @DisplayName("selectSMI1List() 메서드 테스트")
    class SelectSMI1ListTest {

        @Test
        @DisplayName("이름으로 검색 성공")
        void selectSMI1List_byUsername_success() throws Exception {
            // given
            String searchCode = "username";
            String searchParam = "홍길동";

            SM01Entity user1 = new SM01Entity();
            user1.setUserid("user1");
            user1.setUsername("홍길동");

            SM01Entity user2 = new SM01Entity();
            user2.setUserid("user2");
            user2.setUsername("홍길동");

            List<SM01Entity> list = Arrays.asList(user1, user2);

            when(smi1Dao.selectByNameSMI1(any(SM01Entity.class))).thenReturn(list);

            // when
            SMI1ListResponse response = smi1Service.selectSMI1List(searchCode, searchParam);

            // then
            assertNotNull(response);
            verify(smi1Dao).selectByNameSMI1(any(SM01Entity.class));
            verify(smi1Dao, never()).selectByBrthSMI1(any(SM01Entity.class));
            verify(smi1Dao, never()).selectByPhoneSMI1(any(SM01Entity.class));
        }

        @Test
        @DisplayName("생년월일로 검색 성공")
        void selectSMI1List_byBrth_success() throws Exception {
            // given
            String searchCode = "brth";
            String searchParam = "19900101";

            SM01Entity user = new SM01Entity();
            user.setUserid("user1");
            user.setBrth("19900101");

            when(smi1Dao.selectByBrthSMI1(any(SM01Entity.class))).thenReturn(Collections.singletonList(user));

            // when
            SMI1ListResponse response = smi1Service.selectSMI1List(searchCode, searchParam);

            // then
            assertNotNull(response);
            verify(smi1Dao).selectByBrthSMI1(any(SM01Entity.class));
            verify(smi1Dao, never()).selectByNameSMI1(any(SM01Entity.class));
            verify(smi1Dao, never()).selectByPhoneSMI1(any(SM01Entity.class));
        }

        @Test
        @DisplayName("전화번호로 검색 성공")
        void selectSMI1List_byPhone_success() throws Exception {
            // given
            String searchCode = "cellpbnum";
            String searchParam = "5678";

            SM01Entity user = new SM01Entity();
            user.setUserid("user1");
            user.setCellpbnum("5678");

            when(smi1Dao.selectByPhoneSMI1(any(SM01Entity.class))).thenReturn(Collections.singletonList(user));

            // when
            SMI1ListResponse response = smi1Service.selectSMI1List(searchCode, searchParam);

            // then
            assertNotNull(response);
            verify(smi1Dao).selectByPhoneSMI1(any(SM01Entity.class));
            verify(smi1Dao, never()).selectByNameSMI1(any(SM01Entity.class));
            verify(smi1Dao, never()).selectByBrthSMI1(any(SM01Entity.class));
        }

        @Test
        @DisplayName("잘못된 검색 코드면 ClientException 발생")
        void selectSMI1List_invalidSearchCode_throwsException() {
            // given
            String searchCode = "invalidCode";
            String searchParam = "value";

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    smi1Service.selectSMI1List(searchCode, searchParam));
            assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatusCode());
        }

        @Test
        @DisplayName("검색 결과 없음")
        void selectSMI1List_noResults() throws Exception {
            // given
            String searchCode = "username";
            String searchParam = "존재하지않는이름";

            when(smi1Dao.selectByNameSMI1(any(SM01Entity.class))).thenReturn(Collections.emptyList());

            // when
            SMI1ListResponse response = smi1Service.selectSMI1List(searchCode, searchParam);

            // then
            assertNotNull(response);
        }
    }
}
