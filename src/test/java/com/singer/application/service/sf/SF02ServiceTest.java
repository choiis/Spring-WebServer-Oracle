package com.singer.application.service.sf;

import com.singer.application.dto.sf.SF02ListResponse;
import com.singer.application.dto.sf.SF02Request;
import com.singer.application.dto.sf.SF02Response;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sf.SF02Dao;
import com.singer.domain.entity.sf.SF02Entity;
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
class SF02ServiceTest {

    @Mock
    private SF02Dao sf02Dao;

    @InjectMocks
    private SF02Service sf02Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSF02() 메서드 테스트")
    class InsertSF02Test {

        @Test
        @DisplayName("댓글 작성 성공")
        void insertSF02_success() throws Exception {
            // given
            SF02Request request = new SF02Request();
            request.setSeq01(1);
            request.setParents(0);
            request.setText("댓글 내용");

            // when
            SF02Response response = sf02Service.insertSF02(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf02Dao).insertSF02(any(SF02Entity.class));
        }

        @Test
        @DisplayName("대댓글 작성 성공")
        void insertSF02_reply_success() throws Exception {
            // given
            SF02Request request = new SF02Request();
            request.setSeq01(1);
            request.setParents(1);
            request.setText("대댓글 내용");

            // when
            SF02Response response = sf02Service.insertSF02(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf02Dao).insertSF02(any(SF02Entity.class));
        }
    }

    @Nested
    @DisplayName("likeSF02() 메서드 테스트")
    class LikeSF02Test {

        @Test
        @DisplayName("댓글 좋아요 성공")
        void likeSF02_success() throws Exception {
            // given
            SF02Entity entity = new SF02Entity();
            entity.setSeq(1);
            entity.setSeq01(1);

            when(sf02Dao.likeSF02(any(SF02Entity.class))).thenReturn(1);

            // when
            int result = sf02Service.likeSF02(entity);

            // then
            assertEquals(1, result);
            verify(sf02Dao).likeSF02(entity);
        }
    }

    @Nested
    @DisplayName("selectSF02List() 메서드 테스트")
    class SelectSF02ListTest {

        @Test
        @DisplayName("댓글 목록 조회 성공 - 첫 페이지")
        void selectSF02List_firstPage_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SF02Entity entity1 = new SF02Entity();
            entity1.setSeq(1);
            entity1.setText("댓글1");
            entity1.setUserid(TEST_USER);

            SF02Entity entity2 = new SF02Entity();
            entity2.setSeq(2);
            entity2.setText("댓글2");
            entity2.setUserid("otheruser");

            List<SF02Entity> list = Arrays.asList(entity1, entity2);

            when(sf02Dao.selectSF02Count(any(SF02Entity.class))).thenReturn(2);
            when(sf02Dao.selectSF02(any(SF02Entity.class))).thenReturn(list);

            // when
            SF02ListResponse response = sf02Service.selectSF02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf02Dao).selectSF02Count(any(SF02Entity.class));
            verify(sf02Dao).selectSF02(any(SF02Entity.class));
        }

        @Test
        @DisplayName("대댓글 목록 조회 성공")
        void selectSF02List_reply_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 1;
            int nowPage = 1;

            SF02Entity reply = new SF02Entity();
            reply.setSeq(2);
            reply.setParents(1);
            reply.setText("대댓글");
            reply.setUserid(TEST_USER);

            List<SF02Entity> list = Collections.singletonList(reply);

            when(sf02Dao.selectSF02Count(any(SF02Entity.class))).thenReturn(1);
            when(sf02Dao.selectReplySF02(any(SF02Entity.class))).thenReturn(list);

            // when
            SF02ListResponse response = sf02Service.selectSF02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf02Dao).selectReplySF02(any(SF02Entity.class));
        }

        @Test
        @DisplayName("두 번째 페이지 조회시 Count 조회 안함")
        void selectSF02List_secondPage_noCount() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 2;

            when(sf02Dao.selectSF02(any(SF02Entity.class))).thenReturn(Collections.emptyList());

            // when
            SF02ListResponse response = sf02Service.selectSF02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf02Dao, never()).selectSF02Count(any(SF02Entity.class));
        }

        @Test
        @DisplayName("본인 댓글은 deleteYn이 true")
        void selectSF02List_ownComment_deleteYnTrue() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SF02Entity ownComment = new SF02Entity();
            ownComment.setSeq(1);
            ownComment.setUserid(TEST_USER);

            when(sf02Dao.selectSF02Count(any(SF02Entity.class))).thenReturn(1);
            when(sf02Dao.selectSF02(any(SF02Entity.class))).thenReturn(Collections.singletonList(ownComment));

            // when
            sf02Service.selectSF02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertTrue(ownComment.isDeleteYn());
        }
    }

    @Nested
    @DisplayName("updateSF02() 메서드 테스트")
    class UpdateSF02Test {

        @Test
        @DisplayName("댓글 수정 성공")
        void updateSF02_success() throws Exception {
            // given
            SF02Entity entity = new SF02Entity();
            entity.setSeq(1);
            entity.setText("수정된 댓글");

            when(sf02Dao.updateSF02(any(SF02Entity.class))).thenReturn(1);

            // when
            int result = sf02Service.updateSF02(entity);

            // then
            assertEquals(1, result);
            verify(sf02Dao).updateSF02(entity);
        }
    }

    @Nested
    @DisplayName("deleteSF02() 메서드 테스트")
    class DeleteSF02Test {

        @Test
        @DisplayName("댓글 삭제 성공 - 본인 댓글")
        void deleteSF02_ownComment_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SF02Entity entity = new SF02Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sf02Dao.checkUserSF02(any(SF02Entity.class))).thenReturn(entity);
            when(sf02Dao.deleteSF02(any(SF02Entity.class))).thenReturn(1);

            // when
            int result = sf02Service.deleteSF02(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sf02Dao).deleteSF02(any(SF02Entity.class));
        }

        @Test
        @DisplayName("대댓글 삭제 성공 - 자식 댓글도 삭제")
        void deleteSF02_replyWithChildren_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 1;

            SF02Entity entity = new SF02Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sf02Dao.checkUserSF02(any(SF02Entity.class))).thenReturn(entity);
            when(sf02Dao.deleteSF02(any(SF02Entity.class))).thenReturn(1);

            // when
            int result = sf02Service.deleteSF02(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sf02Dao).deleteChild(any(SF02Entity.class));
            verify(sf02Dao).deleteSF02(any(SF02Entity.class));
        }

        @Test
        @DisplayName("댓글 삭제 실패 - 타인 댓글")
        void deleteSF02_otherComment_throwsException() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SF02Entity entity = new SF02Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sf02Dao.checkUserSF02(any(SF02Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sf02Service.deleteSF02(seq, seq01, parents, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }
}
