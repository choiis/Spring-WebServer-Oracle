package com.singer.application.service.sm;

import com.singer.application.dto.sm.SM02ListResponse;
import com.singer.application.dto.sm.SM02Request;
import com.singer.application.dto.sm.SM02Response;
import com.singer.domain.dao.sm.SM02Dao;
import com.singer.domain.entity.sm.SM02Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SM02ServiceTest {

    @Mock
    private SM02Dao sm02Dao;

    @InjectMocks
    private SM02Service sm02Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSM02() 메서드 테스트")
    class InsertSM02Test {

        @Test
        @DisplayName("등록 성공")
        void insertSM02_success() throws Exception {
            // given
            SM02Request request = new SM02Request();
            request.setTitle("제목");
            request.setText("내용");

            // when
            SM02Response response = sm02Service.insertSM02(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sm02Dao).insertSM02(any(SM02Entity.class));
        }
    }

    @Nested
    @DisplayName("selectSM02List() 메서드 테스트")
    class SelectSM02ListTest {

        @Test
        @DisplayName("목록 조회 성공")
        void selectSM02List_success() throws Exception {
            // given
            int nowPage = 1;

            SM02Entity entity1 = new SM02Entity();
            entity1.setSeq(1);

            SM02Entity entity2 = new SM02Entity();
            entity2.setSeq(2);

            List<SM02Entity> list = Arrays.asList(entity1, entity2);

            when(sm02Dao.selectSM02(any(SM02Entity.class))).thenReturn(list);

            // when
            SM02ListResponse response = sm02Service.selectSM02List(nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sm02Dao).selectSM02(any(SM02Entity.class));
        }

        @Test
        @DisplayName("빈 목록 조회")
        void selectSM02List_emptyList() throws Exception {
            // given
            int nowPage = 1;

            when(sm02Dao.selectSM02(any(SM02Entity.class))).thenReturn(Collections.emptyList());

            // when
            SM02ListResponse response = sm02Service.selectSM02List(nowPage, TEST_USER);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("updateSM02() 메서드 테스트")
    class UpdateSM02Test {

        @Test
        @DisplayName("수정 성공")
        void updateSM02_success() throws Exception {
            // given
            int seq = 1;
            SM02Request request = new SM02Request();
            request.setTitle("수정 제목");
            request.setText("수정 내용");

            // when
            sm02Service.updateSM02(seq, request, TEST_USER);

            // then
            verify(sm02Dao).updateSM02(any(SM02Entity.class));
        }
    }

    @Nested
    @DisplayName("deleteSM02() 메서드 테스트")
    class DeleteSM02Test {

        @Test
        @DisplayName("삭제 성공")
        void deleteSM02_success() throws Exception {
            // given
            int seq = 1;

            when(sm02Dao.deleteSM02(any(SM02Entity.class))).thenReturn(1);

            // when
            int result = sm02Service.deleteSM02(seq, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sm02Dao).deleteSM02(any(SM02Entity.class));
        }

        @Test
        @DisplayName("삭제할 데이터 없음")
        void deleteSM02_noData() throws Exception {
            // given
            int seq = 999;

            when(sm02Dao.deleteSM02(any(SM02Entity.class))).thenReturn(0);

            // when
            int result = sm02Service.deleteSM02(seq, TEST_USER);

            // then
            assertEquals(0, result);
        }
    }
}
