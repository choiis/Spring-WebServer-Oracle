package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV01ListResponse;
import com.singer.application.dto.sv.SV01Request;
import com.singer.application.dto.sv.SV01Response;
import com.singer.application.dto.sv.SV02Request;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.util.Constants.YES_NO;
import com.singer.domain.dao.sv.SV01Dao;
import com.singer.domain.dao.sv.SV02Dao;
import com.singer.domain.dao.sv.SV04Dao;
import com.singer.domain.entity.sv.SV01Entity;
import com.singer.domain.entity.sv.SV02Entity;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SV01ServiceTest {

    @Mock
    private SV01Dao sv01Dao;

    @Mock
    private SV02Dao sv02Dao;

    @Mock
    private SV04Dao sv04Dao;

    @InjectMocks
    private SV01Service sv01Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSV01() 메서드 테스트")
    class InsertSV01Test {

        @Test
        @DisplayName("설문 작성 성공")
        void insertSV01_success() throws Exception {
            // given
            SV02Request sv02Request1 = new SV02Request();
            sv02Request1.setContent("선택지1");

            SV02Request sv02Request2 = new SV02Request();
            sv02Request2.setContent("선택지2");

            SV01Request request = new SV01Request();
            request.setTitle("설문 제목");
            request.setText("설문 내용");
            request.setList(Arrays.asList(sv02Request1, sv02Request2));

            // when
            SV01Response response = sv01Service.insertSV01(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv01Dao).insertSV01(any(SV01Entity.class));
            verify(sv02Dao).insertSV02(anyList());
        }

        @Test
        @DisplayName("선택지 없이 설문 작성 시 AppException 발생")
        void insertSV01_noOptions_throwsException() {
            // given
            SV01Request request = new SV01Request();
            request.setTitle("설문 제목");
            request.setText("설문 내용");
            request.setList(Collections.emptyList());

            // when & then
            assertThrows(AppException.class, () ->
                    sv01Service.insertSV01(request, TEST_USER));
        }

        @Test
        @DisplayName("빈 선택지 내용으로 설문 작성 시 AppException 발생")
        void insertSV01_emptyOptionContent_throwsException() {
            // given
            SV02Request sv02Request = new SV02Request();
            sv02Request.setContent("");

            SV01Request request = new SV01Request();
            request.setTitle("설문 제목");
            request.setText("설문 내용");
            request.setList(Collections.singletonList(sv02Request));

            // when & then
            assertThrows(AppException.class, () ->
                    sv01Service.insertSV01(request, TEST_USER));
        }
    }

    @Nested
    @DisplayName("selectSV01List() 메서드 테스트")
    class SelectSV01ListTest {

        @Test
        @DisplayName("설문 목록 조회 성공")
        void selectSV01List_success() throws Exception {
            // given
            int nowPage = 1;
            SV01Entity entity1 = new SV01Entity();
            entity1.setSeq(1);
            entity1.setTitle("설문1");
            entity1.setTotCnt(2);

            SV01Entity entity2 = new SV01Entity();
            entity2.setSeq(2);
            entity2.setTitle("설문2");

            List<SV01Entity> list = Arrays.asList(entity1, entity2);

            when(sv01Dao.selectSV01(any(SV01Entity.class))).thenReturn(list);

            // when
            SV01ListResponse response = sv01Service.selectSV01List(nowPage);

            // then
            assertNotNull(response);
            verify(sv01Dao).selectSV01(any(SV01Entity.class));
        }

        @Test
        @DisplayName("빈 목록 조회")
        void selectSV01List_emptyList() throws Exception {
            // given
            int nowPage = 1;

            when(sv01Dao.selectSV01(any(SV01Entity.class))).thenReturn(Collections.emptyList());

            // when
            SV01ListResponse response = sv01Service.selectSV01List(nowPage);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("selectOneSV01() 메서드 테스트")
    class SelectOneSV01Test {

        @Test
        @DisplayName("설문 상세 조회 성공 - 본인 설문")
        void selectOneSV01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            int recall = YES_NO.NO.getValue();

            SV01Entity entity = new SV01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            SV02Entity sv02Entity = new SV02Entity();
            sv02Entity.setSeq(seq);
            sv02Entity.setContent("선택지1");

            when(sv01Dao.selectOneSV01(any(SV01Entity.class))).thenReturn(entity);
            when(sv02Dao.selectSV02(any(SV02Entity.class))).thenReturn(Collections.singletonList(sv02Entity));
            when(sv02Dao.selectCount(any(SV02Entity.class))).thenReturn(1);

            // when
            SV01Response response = sv01Service.selectOneSV01(seq, recall, TEST_USER);

            // then
            assertNotNull(response);
            assertTrue(entity.isDeleteYn());
            verify(sv01Dao).clickSV01(any(SV01Entity.class));
        }

        @Test
        @DisplayName("설문 상세 조회 성공 - 재조회(click 증가 안함)")
        void selectOneSV01_recall_noClick() throws Exception {
            // given
            int seq = 1;
            int recall = YES_NO.YES.getValue();

            SV01Entity entity = new SV01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sv01Dao.selectOneSV01(any(SV01Entity.class))).thenReturn(entity);
            when(sv02Dao.selectSV02(any(SV02Entity.class))).thenReturn(Collections.emptyList());
            when(sv02Dao.selectCount(any(SV02Entity.class))).thenReturn(0);

            // when
            SV01Response response = sv01Service.selectOneSV01(seq, recall, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv01Dao, never()).clickSV01(any(SV01Entity.class));
        }
    }

    @Nested
    @DisplayName("deleteSV01() 메서드 테스트")
    class DeleteSV01Test {

        @Test
        @DisplayName("설문 삭제 성공 - 본인 설문")
        void deleteSV01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SV01Entity entity = new SV01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sv01Dao.selectOneSV01(any(SV01Entity.class))).thenReturn(entity);

            // when
            SV01Response response = sv01Service.deleteSV01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv04Dao).deleteSeqSV04(any());
            verify(sv01Dao).deleteSV01(any(SV01Entity.class));
        }

        @Test
        @DisplayName("설문 삭제 실패 - 타인 설문")
        void deleteSV01_otherPost_throwsException() throws Exception {
            // given
            int seq = 1;
            SV01Entity entity = new SV01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sv01Dao.selectOneSV01(any(SV01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sv01Service.deleteSV01(seq, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("updateSV01(int, SV01Request, String) 메서드 테스트")
    class UpdateSV01RequestTest {

        @Test
        @DisplayName("설문 수정 성공 - 본인 설문")
        void updateSV01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SV01Request request = new SV01Request();
            request.setTitle("수정된 제목");
            request.setText("수정된 내용");

            SV01Entity entity = new SV01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sv01Dao.selectOneSV01(any(SV01Entity.class))).thenReturn(entity);
            when(sv01Dao.updateSV01(any(SV01Entity.class))).thenReturn(1);

            // when
            sv01Service.updateSV01(seq, request, TEST_USER);

            // then
            verify(sv01Dao).updateSV01(any(SV01Entity.class));
        }

        @Test
        @DisplayName("설문 수정 실패 - 타인 설문")
        void updateSV01_otherPost_throwsException() throws Exception {
            // given
            int seq = 1;
            SV01Request request = new SV01Request();
            request.setTitle("수정된 제목");

            SV01Entity entity = new SV01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sv01Dao.selectOneSV01(any(SV01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sv01Service.updateSV01(seq, request, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("likeSV01() 메서드 테스트")
    class LikeSV01Test {

        @Test
        @DisplayName("좋아요 성공")
        void likeSV01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SV01Response response = sv01Service.likeSV01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv01Dao).likeSV01(any(SV01Entity.class));
            verify(sv01Dao).likelogSV01(any(SV01Entity.class));
        }
    }

    @Nested
    @DisplayName("hateSV01() 메서드 테스트")
    class HateSV01Test {

        @Test
        @DisplayName("싫어요 성공")
        void hateSV01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SV01Response response = sv01Service.hateSV01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv01Dao).hateSV01(any(SV01Entity.class));
            verify(sv01Dao).hatelogSV01(any(SV01Entity.class));
        }
    }
}
