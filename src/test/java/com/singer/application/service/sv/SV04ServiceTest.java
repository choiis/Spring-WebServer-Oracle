package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV04ListResponse;
import com.singer.application.dto.sv.SV04Request;
import com.singer.application.dto.sv.SV04Response;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sv.SV04Dao;
import com.singer.domain.entity.sv.SV04Entity;
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
class SV04ServiceTest {

    @Mock
    private SV04Dao sv04Dao;

    @InjectMocks
    private SV04Service sv04Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSV04() 메서드 테스트")
    class InsertSV04Test {

        @Test
        @DisplayName("댓글 작성 성공")
        void insertSV04_success() throws Exception {
            // given
            SV04Request request = new SV04Request();
            request.setSeq01(1);
            request.setParents(0);
            request.setText("댓글 내용");

            // when
            SV04Response response = sv04Service.insertSV04(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv04Dao).insertSV04(any(SV04Entity.class));
        }

        @Test
        @DisplayName("대댓글 작성 성공")
        void insertSV04_reply_success() throws Exception {
            // given
            SV04Request request = new SV04Request();
            request.setSeq01(1);
            request.setParents(1);
            request.setText("대댓글 내용");

            // when
            SV04Response response = sv04Service.insertSV04(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv04Dao).insertSV04(any(SV04Entity.class));
        }
    }

    @Nested
    @DisplayName("likeSV04() 메서드 테스트")
    class LikeSV04Test {

        @Test
        @DisplayName("댓글 좋아요 성공")
        void likeSV04_success() throws Exception {
            // given
            SV04Entity entity = new SV04Entity();
            entity.setSeq(1);
            entity.setSeq01(1);

            when(sv04Dao.likeSV04(any(SV04Entity.class))).thenReturn(1);

            // when
            int result = sv04Service.likeSV04(entity);

            // then
            assertEquals(1, result);
            verify(sv04Dao).likeSV04(entity);
        }
    }

    @Nested
    @DisplayName("selectSV04List() 메서드 테스트")
    class SelectSV04ListTest {

        @Test
        @DisplayName("댓글 목록 조회 성공 - 첫 페이지")
        void selectSV04List_firstPage_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SV04Entity entity1 = new SV04Entity();
            entity1.setSeq(1);
            entity1.setText("댓글1");
            entity1.setUserid(TEST_USER);

            SV04Entity entity2 = new SV04Entity();
            entity2.setSeq(2);
            entity2.setText("댓글2");
            entity2.setUserid("otheruser");

            List<SV04Entity> list = Arrays.asList(entity1, entity2);

            when(sv04Dao.selectSV04Count(any(SV04Entity.class))).thenReturn(2);
            when(sv04Dao.selectSV04(any(SV04Entity.class))).thenReturn(list);

            // when
            SV04ListResponse response = sv04Service.selectSV04List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv04Dao).selectSV04Count(any(SV04Entity.class));
            verify(sv04Dao).selectSV04(any(SV04Entity.class));
        }

        @Test
        @DisplayName("대댓글 목록 조회 성공")
        void selectSV04List_reply_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 1;
            int nowPage = 1;

            SV04Entity reply = new SV04Entity();
            reply.setSeq(2);
            reply.setParents(1);
            reply.setText("대댓글");
            reply.setUserid(TEST_USER);

            List<SV04Entity> list = Collections.singletonList(reply);

            when(sv04Dao.selectSV04Count(any(SV04Entity.class))).thenReturn(1);
            when(sv04Dao.selectReplySV04(any(SV04Entity.class))).thenReturn(list);

            // when
            SV04ListResponse response = sv04Service.selectSV04List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv04Dao).selectReplySV04(any(SV04Entity.class));
        }

        @Test
        @DisplayName("두 번째 페이지 조회시 Count 조회 안함")
        void selectSV04List_secondPage_noCount() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 2;

            when(sv04Dao.selectSV04(any(SV04Entity.class))).thenReturn(Collections.emptyList());

            // when
            SV04ListResponse response = sv04Service.selectSV04List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sv04Dao, never()).selectSV04Count(any(SV04Entity.class));
        }

        @Test
        @DisplayName("본인 댓글은 deleteYn이 true")
        void selectSV04List_ownComment_deleteYnTrue() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SV04Entity ownComment = new SV04Entity();
            ownComment.setSeq(1);
            ownComment.setUserid(TEST_USER);

            when(sv04Dao.selectSV04Count(any(SV04Entity.class))).thenReturn(1);
            when(sv04Dao.selectSV04(any(SV04Entity.class))).thenReturn(Collections.singletonList(ownComment));

            // when
            sv04Service.selectSV04List(seq01, parents, nowPage, TEST_USER);

            // then
            assertTrue(ownComment.isDeleteYn());
        }
    }

    @Nested
    @DisplayName("updateSV04() 메서드 테스트")
    class UpdateSV04Test {

        @Test
        @DisplayName("댓글 수정 성공")
        void updateSV04_success() throws Exception {
            // given
            SV04Entity entity = new SV04Entity();
            entity.setSeq(1);
            entity.setText("수정된 댓글");

            when(sv04Dao.updateSV04(any(SV04Entity.class))).thenReturn(1);

            // when
            int result = sv04Service.updateSV04(entity);

            // then
            assertEquals(1, result);
            verify(sv04Dao).updateSV04(entity);
        }
    }

    @Nested
    @DisplayName("deleteSV04() 메서드 테스트")
    class DeleteSV04Test {

        @Test
        @DisplayName("댓글 삭제 성공 - 본인 댓글")
        void deleteSV04_ownComment_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SV04Entity entity = new SV04Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sv04Dao.checkUserSV04(any(SV04Entity.class))).thenReturn(entity);
            when(sv04Dao.deleteSV04(any(SV04Entity.class))).thenReturn(1);

            // when
            int result = sv04Service.deleteSV04(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sv04Dao).deleteSV04(any(SV04Entity.class));
        }

        @Test
        @DisplayName("대댓글 삭제 성공 - 자식 댓글도 삭제")
        void deleteSV04_replyWithChildren_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 1;

            SV04Entity entity = new SV04Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sv04Dao.checkUserSV04(any(SV04Entity.class))).thenReturn(entity);
            when(sv04Dao.deleteSV04(any(SV04Entity.class))).thenReturn(1);

            // when
            int result = sv04Service.deleteSV04(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sv04Dao).deleteChild(any(SV04Entity.class));
            verify(sv04Dao).deleteSV04(any(SV04Entity.class));
        }

        @Test
        @DisplayName("댓글 삭제 실패 - 타인 댓글")
        void deleteSV04_otherComment_throwsException() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SV04Entity entity = new SV04Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sv04Dao.checkUserSV04(any(SV04Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sv04Service.deleteSV04(seq, seq01, parents, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }
}
