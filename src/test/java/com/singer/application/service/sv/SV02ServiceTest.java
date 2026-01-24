package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV03ListRequest;
import com.singer.application.dto.sv.SV03Request;
import com.singer.common.exception.AppException;
import com.singer.domain.dao.sv.SV02Dao;
import com.singer.domain.entity.sv.SV02Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SV02ServiceTest {

    @Mock
    private SV02Dao sv02Dao;

    @InjectMocks
    private SV02Service sv02Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("updateSV01() 메서드 테스트")
    class UpdateSV01Test {

        @Test
        @DisplayName("SV02 수정 성공")
        void updateSV01_success() throws Exception {
            // given
            SV02Entity entity = new SV02Entity();
            entity.setSeq(1);
            entity.setContent("수정된 선택지");

            when(sv02Dao.updateSV02(any(SV02Entity.class))).thenReturn(1);

            // when
            int result = sv02Service.updateSV01(entity);

            // then
            assertEquals(1, result);
            verify(sv02Dao).updateSV02(entity);
        }
    }

    @Nested
    @DisplayName("insertSv03() 메서드 테스트")
    class InsertSv03Test {

        @Test
        @DisplayName("SV03 투표 등록 성공")
        void insertSv03_success() throws Exception {
            // given
            SV03Request sv03Request1 = new SV03Request();
            sv03Request1.setSeq(1);
            sv03Request1.setIdx(1);

            SV03Request sv03Request2 = new SV03Request();
            sv03Request2.setSeq(1);
            sv03Request2.setIdx(2);

            SV03ListRequest listRequest = new SV03ListRequest();
            listRequest.setList(Arrays.asList(sv03Request1, sv03Request2));

            // when
            int result = sv02Service.insertSv03(listRequest, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sv02Dao).insertSV03(anyList());
        }

        @Test
        @DisplayName("빈 리스트로 투표 등록 시 AppException 발생")
        void insertSv03_emptyList_throwsException() {
            // given
            SV03ListRequest listRequest = new SV03ListRequest();
            listRequest.setList(Collections.emptyList());

            // when & then
            assertThrows(AppException.class, () ->
                    sv02Service.insertSv03(listRequest, TEST_USER));
        }

        @Test
        @DisplayName("null 리스트로 투표 등록 시 예외 발생")
        void insertSv03_nullList_throwsException() {
            // given
            SV03ListRequest listRequest = new SV03ListRequest();
            listRequest.setList(null);

            // when & then - null 리스트로 호출 시 NullPointerException 발생
            assertThrows(Exception.class, () ->
                    sv02Service.insertSv03(listRequest, TEST_USER));
        }
    }
}
