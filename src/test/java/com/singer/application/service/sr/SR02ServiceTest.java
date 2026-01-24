package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR02Request;
import com.singer.application.dto.sr.SR02Response;
import com.singer.domain.dao.sr.SR02Dao;
import com.singer.domain.entity.sr.SR01Entity;
import com.singer.domain.entity.sr.SR02Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SR02ServiceTest {

    @Mock
    private SR02Dao sr02Dao;

    @InjectMocks
    private SR02Service sr02Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSR02() 메서드 테스트")
    class InsertSR02Test {

        @Test
        @DisplayName("SR02 작성 성공")
        void insertSR02_success() throws Exception {
            // given
            SR02Request request = new SR02Request();
            request.setSeq(1);
            request.setGrade(5);

            SR01Entity gradeResult = new SR01Entity();
            gradeResult.setSeq(1);
            gradeResult.setGrade(5);

            when(sr02Dao.selectGradeSR02(any(SR01Entity.class))).thenReturn(gradeResult);

            // when
            SR02Response response = sr02Service.insertSR02(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr02Dao).insertSR02(any(SR02Entity.class));
            verify(sr02Dao).selectGradeSR02(any(SR01Entity.class));
        }
    }

    @Nested
    @DisplayName("selectOneSR02() 메서드 테스트")
    class SelectOneSR02Test {

        @Test
        @DisplayName("SR02 조회 성공")
        void selectOneSR02_success() throws Exception {
            // given
            int seq = 1;
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setGrade(5);

            when(sr02Dao.selectOneSR02(any(SR01Entity.class))).thenReturn(entity);

            // when
            SR02Response response = sr02Service.selectOneSR02(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr02Dao).selectOneSR02(any(SR01Entity.class));
        }

        @Test
        @DisplayName("SR02 조회 - 데이터 없음")
        void selectOneSR02_noData() throws Exception {
            // given
            int seq = 999;

            when(sr02Dao.selectOneSR02(any(SR01Entity.class))).thenReturn(null);

            // when
            SR02Response response = sr02Service.selectOneSR02(seq, TEST_USER);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("deleteSR02() 메서드 테스트")
    class DeleteSR02Test {

        @Test
        @DisplayName("SR02 삭제 성공")
        void deleteSR02_success() throws Exception {
            // given
            int seq = 1;

            when(sr02Dao.deleteSR02(any(SR01Entity.class))).thenReturn(1);

            // when
            int result = sr02Service.deleteSR02(seq, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sr02Dao).deleteSR02(any(SR01Entity.class));
        }

        @Test
        @DisplayName("SR02 삭제 - 데이터 없음")
        void deleteSR02_noData() throws Exception {
            // given
            int seq = 999;

            when(sr02Dao.deleteSR02(any(SR01Entity.class))).thenReturn(0);

            // when
            int result = sr02Service.deleteSR02(seq, TEST_USER);

            // then
            assertEquals(0, result);
        }
    }
}
