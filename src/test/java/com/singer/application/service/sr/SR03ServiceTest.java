package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR03ListResponse;
import com.singer.application.dto.sr.SR03Request;
import com.singer.application.dto.sr.SR03Response;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sr.SR03Dao;
import com.singer.domain.entity.sr.SR03Entity;
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
class SR03ServiceTest {

    @Mock
    private SR03Dao sr03Dao;

    @InjectMocks
    private SR03Service sr03Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSR03() 메서드 테스트")
    class InsertSR03Test {

        @Test
        @DisplayName("댓글 작성 성공")
        void insertSR03_success() throws Exception {
            // given
            SR03Request request = new SR03Request();
            request.setSeq01(1);
            request.setParents(0);
            request.setText("댓글 내용");

            // when
            SR03Response response = sr03Service.insertSR03(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr03Dao).insertSR03(any(SR03Entity.class));
        }

        @Test
        @DisplayName("대댓글 작성 성공")
        void insertSR03_reply_success() throws Exception {
            // given
            SR03Request request = new SR03Request();
            request.setSeq01(1);
            request.setParents(1);
            request.setText("대댓글 내용");

            // when
            SR03Response response = sr03Service.insertSR03(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr03Dao).insertSR03(any(SR03Entity.class));
        }
    }

    @Nested
    @DisplayName("likeSR03() 메서드 테스트")
    class LikeSR03Test {

        @Test
        @DisplayName("댓글 좋아요 성공")
        void likeSR03_success() throws Exception {
            // given
            SR03Entity entity = new SR03Entity();
            entity.setSeq(1);
            entity.setSeq01(1);

            when(sr03Dao.likeSR03(any(SR03Entity.class))).thenReturn(1);

            // when
            int result = sr03Service.likeSR03(entity);

            // then
            assertEquals(1, result);
            verify(sr03Dao).likeSR03(entity);
        }
    }

    @Nested
    @DisplayName("selectSR03List() 메서드 테스트")
    class SelectSR03ListTest {

        @Test
        @DisplayName("댓글 목록 조회 성공 - 첫 페이지")
        void selectSR03List_firstPage_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SR03Entity entity1 = new SR03Entity();
            entity1.setSeq(1);
            entity1.setText("댓글1");
            entity1.setUserid(TEST_USER);

            SR03Entity entity2 = new SR03Entity();
            entity2.setSeq(2);
            entity2.setText("댓글2");
            entity2.setUserid("otheruser");

            List<SR03Entity> list = Arrays.asList(entity1, entity2);

            when(sr03Dao.selectSR03Count(any(SR03Entity.class))).thenReturn(2);
            when(sr03Dao.selectSR03(any(SR03Entity.class))).thenReturn(list);

            // when
            SR03ListResponse response = sr03Service.selectSR03List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr03Dao).selectSR03Count(any(SR03Entity.class));
            verify(sr03Dao).selectSR03(any(SR03Entity.class));
        }

        @Test
        @DisplayName("대댓글 목록 조회 성공")
        void selectSR03List_reply_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 1;
            int nowPage = 1;

            SR03Entity reply = new SR03Entity();
            reply.setSeq(2);
            reply.setParents(1);
            reply.setText("대댓글");
            reply.setUserid(TEST_USER);

            List<SR03Entity> list = Collections.singletonList(reply);

            when(sr03Dao.selectSR03Count(any(SR03Entity.class))).thenReturn(1);
            when(sr03Dao.selectReplySR03(any(SR03Entity.class))).thenReturn(list);

            // when
            SR03ListResponse response = sr03Service.selectSR03List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr03Dao).selectReplySR03(any(SR03Entity.class));
        }

        @Test
        @DisplayName("두 번째 페이지 조회시 Count 조회 안함")
        void selectSR03List_secondPage_noCount() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 2;

            when(sr03Dao.selectSR03(any(SR03Entity.class))).thenReturn(Collections.emptyList());

            // when
            SR03ListResponse response = sr03Service.selectSR03List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr03Dao, never()).selectSR03Count(any(SR03Entity.class));
        }

        @Test
        @DisplayName("본인 댓글은 deleteYn이 true")
        void selectSR03List_ownComment_deleteYnTrue() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SR03Entity ownComment = new SR03Entity();
            ownComment.setSeq(1);
            ownComment.setUserid(TEST_USER);

            when(sr03Dao.selectSR03Count(any(SR03Entity.class))).thenReturn(1);
            when(sr03Dao.selectSR03(any(SR03Entity.class))).thenReturn(Collections.singletonList(ownComment));

            // when
            sr03Service.selectSR03List(seq01, parents, nowPage, TEST_USER);

            // then
            assertTrue(ownComment.isDeleteYn());
        }
    }

    @Nested
    @DisplayName("updateSR03() 메서드 테스트")
    class UpdateSR03Test {

        @Test
        @DisplayName("댓글 수정 성공")
        void updateSR03_success() throws Exception {
            // given
            SR03Entity entity = new SR03Entity();
            entity.setSeq(1);
            entity.setText("수정된 댓글");

            when(sr03Dao.updateSR03(any(SR03Entity.class))).thenReturn(1);

            // when
            int result = sr03Service.updateSR03(entity);

            // then
            assertEquals(1, result);
            verify(sr03Dao).updateSR03(entity);
        }
    }

    @Nested
    @DisplayName("deleteSR03() 메서드 테스트")
    class DeleteSR03Test {

        @Test
        @DisplayName("댓글 삭제 성공 - 본인 댓글")
        void deleteSR03_ownComment_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SR03Entity entity = new SR03Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sr03Dao.checkUserSR03(any(SR03Entity.class))).thenReturn(entity);
            when(sr03Dao.deleteSR03(any(SR03Entity.class))).thenReturn(1);

            // when
            int result = sr03Service.deleteSR03(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sr03Dao).deleteSR03(any(SR03Entity.class));
        }

        @Test
        @DisplayName("대댓글 삭제 성공 - 자식 댓글도 삭제")
        void deleteSR03_replyWithChildren_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 1;

            SR03Entity entity = new SR03Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sr03Dao.checkUserSR03(any(SR03Entity.class))).thenReturn(entity);
            when(sr03Dao.deleteSR03(any(SR03Entity.class))).thenReturn(1);

            // when
            int result = sr03Service.deleteSR03(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sr03Dao).deleteChild(any(SR03Entity.class));
            verify(sr03Dao).deleteSR03(any(SR03Entity.class));
        }

        @Test
        @DisplayName("댓글 삭제 실패 - 타인 댓글")
        void deleteSR03_otherComment_throwsException() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SR03Entity entity = new SR03Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sr03Dao.checkUserSR03(any(SR03Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sr03Service.deleteSR03(seq, seq01, parents, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }
}
