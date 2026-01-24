package com.singer.application.service.sb;

import com.singer.application.dto.sb.SB02ListResponse;
import com.singer.application.dto.sb.SB02Request;
import com.singer.application.dto.sb.SB02Response;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sb.SB02Dao;
import com.singer.domain.entity.sb.SB02Entity;
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
class SB02ServiceTest {

    @Mock
    private SB02Dao sb02Dao;

    @InjectMocks
    private SB02Service sb02Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSB02() 메서드 테스트")
    class InsertSB02Test {

        @Test
        @DisplayName("댓글 작성 성공")
        void insertSB02_success() throws Exception {
            // given
            SB02Request request = new SB02Request();
            request.setSeq01(1);
            request.setParents(0);
            request.setText("댓글 내용");

            // when
            SB02Response response = sb02Service.insertSB02(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb02Dao).insertSB02(any(SB02Entity.class));
        }

        @Test
        @DisplayName("대댓글 작성 성공")
        void insertSB02_reply_success() throws Exception {
            // given
            SB02Request request = new SB02Request();
            request.setSeq01(1);
            request.setParents(1); // 부모 댓글 존재
            request.setText("대댓글 내용");

            // when
            SB02Response response = sb02Service.insertSB02(request, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb02Dao).insertSB02(any(SB02Entity.class));
        }
    }

    @Nested
    @DisplayName("likeSB02() 메서드 테스트")
    class LikeSB02Test {

        @Test
        @DisplayName("댓글 좋아요 성공")
        void likeSB02_success() throws Exception {
            // given
            SB02Entity entity = new SB02Entity();
            entity.setSeq(1);
            entity.setSeq01(1);

            when(sb02Dao.likeSB02(any(SB02Entity.class))).thenReturn(1);

            // when
            int result = sb02Service.likeSB02(entity);

            // then
            assertEquals(1, result);
            verify(sb02Dao).likeSB02(entity);
        }
    }

    @Nested
    @DisplayName("selectSB02List() 메서드 테스트")
    class SelectSB02ListTest {

        @Test
        @DisplayName("댓글 목록 조회 성공 - 첫 페이지")
        void selectSB02List_firstPage_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SB02Entity entity1 = new SB02Entity();
            entity1.setSeq(1);
            entity1.setText("댓글1");
            entity1.setUserid(TEST_USER);

            SB02Entity entity2 = new SB02Entity();
            entity2.setSeq(2);
            entity2.setText("댓글2");
            entity2.setUserid("otheruser");

            List<SB02Entity> list = Arrays.asList(entity1, entity2);

            when(sb02Dao.selectSF02Count(any(SB02Entity.class))).thenReturn(2);
            when(sb02Dao.selectSB02(any(SB02Entity.class))).thenReturn(list);

            // when
            SB02ListResponse response = sb02Service.selectSB02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb02Dao).selectSF02Count(any(SB02Entity.class));
            verify(sb02Dao).selectSB02(any(SB02Entity.class));
        }

        @Test
        @DisplayName("대댓글 목록 조회 성공")
        void selectSB02List_reply_success() throws Exception {
            // given
            int seq01 = 1;
            int parents = 1; // 부모 댓글 존재
            int nowPage = 1;

            SB02Entity reply = new SB02Entity();
            reply.setSeq(2);
            reply.setParents(1);
            reply.setText("대댓글");
            reply.setUserid(TEST_USER);

            List<SB02Entity> list = Collections.singletonList(reply);

            when(sb02Dao.selectSF02Count(any(SB02Entity.class))).thenReturn(1);
            when(sb02Dao.selectReplySB02(any(SB02Entity.class))).thenReturn(list);

            // when
            SB02ListResponse response = sb02Service.selectSB02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb02Dao).selectReplySB02(any(SB02Entity.class));
        }

        @Test
        @DisplayName("두 번째 페이지 조회시 Count 조회 안함")
        void selectSB02List_secondPage_noCount() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 2;

            when(sb02Dao.selectSB02(any(SB02Entity.class))).thenReturn(Collections.emptyList());

            // when
            SB02ListResponse response = sb02Service.selectSB02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb02Dao, never()).selectSF02Count(any(SB02Entity.class));
        }

        @Test
        @DisplayName("본인 댓글은 deleteYn이 true")
        void selectSB02List_ownComment_deleteYnTrue() throws Exception {
            // given
            int seq01 = 1;
            int parents = 0;
            int nowPage = 1;

            SB02Entity ownComment = new SB02Entity();
            ownComment.setSeq(1);
            ownComment.setUserid(TEST_USER);

            when(sb02Dao.selectSF02Count(any(SB02Entity.class))).thenReturn(1);
            when(sb02Dao.selectSB02(any(SB02Entity.class))).thenReturn(Collections.singletonList(ownComment));

            // when
            sb02Service.selectSB02List(seq01, parents, nowPage, TEST_USER);

            // then
            assertTrue(ownComment.isDeleteYn());
        }
    }

    @Nested
    @DisplayName("updateSB02() 메서드 테스트")
    class UpdateSB02Test {

        @Test
        @DisplayName("댓글 수정 성공")
        void updateSB02_success() throws Exception {
            // given
            SB02Entity entity = new SB02Entity();
            entity.setSeq(1);
            entity.setText("수정된 댓글");

            when(sb02Dao.updateSB02(any(SB02Entity.class))).thenReturn(1);

            // when
            int result = sb02Service.updateSB02(entity);

            // then
            assertEquals(1, result);
            verify(sb02Dao).updateSB02(entity);
        }
    }

    @Nested
    @DisplayName("deleteSB02() 메서드 테스트")
    class DeleteSB02Test {

        @Test
        @DisplayName("댓글 삭제 성공 - 본인 댓글")
        void deleteSB02_ownComment_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SB02Entity entity = new SB02Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sb02Dao.checkUserSB02(any(SB02Entity.class))).thenReturn(entity);
            when(sb02Dao.deleteSB02(any(SB02Entity.class))).thenReturn(1);

            // when
            int result = sb02Service.deleteSB02(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sb02Dao).deleteSB02(any(SB02Entity.class));
        }

        @Test
        @DisplayName("대댓글 삭제 성공 - 자식 댓글도 삭제")
        void deleteSB02_replyWithChildren_success() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 1; // 부모가 있는 대댓글

            SB02Entity entity = new SB02Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sb02Dao.checkUserSB02(any(SB02Entity.class))).thenReturn(entity);
            when(sb02Dao.deleteSB02(any(SB02Entity.class))).thenReturn(1);

            // when
            int result = sb02Service.deleteSB02(seq, seq01, parents, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sb02Dao).deleteChild(any(SB02Entity.class));
            verify(sb02Dao).deleteSB02(any(SB02Entity.class));
        }

        @Test
        @DisplayName("댓글 삭제 실패 - 타인 댓글")
        void deleteSB02_otherComment_throwsException() throws Exception {
            // given
            int seq = 1;
            int seq01 = 1;
            int parents = 0;

            SB02Entity entity = new SB02Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sb02Dao.checkUserSB02(any(SB02Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sb02Service.deleteSB02(seq, seq01, parents, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }
}
