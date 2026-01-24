package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR01ListResponse;
import com.singer.application.dto.sr.SR01Request;
import com.singer.application.dto.sr.SR01Response;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sr.SR01Dao;
import com.singer.domain.dao.sr.SR02Dao;
import com.singer.domain.dao.sr.SR03Dao;
import com.singer.domain.entity.sr.SR01Entity;
import com.singer.infrastructure.config.S3Properties;
import com.singer.infrastructure.util.S3Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SR01ServiceTest {

    @Mock
    private SR01Dao sr01Dao;

    @Mock
    private SR02Dao sr02Dao;

    @Mock
    private SR03Dao sr03Dao;

    @Mock
    private S3Util s3Util;

    @Mock
    private S3Properties s3Properties;

    @InjectMocks
    private SR01Service sr01Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSR01() 메서드 테스트")
    class InsertSR01Test {

        @Mock
        private MultipartHttpServletRequest multipartRequest;

        @Mock
        private MultipartFile photoFile;

        @TempDir
        Path tempDir;

        @Test
        @DisplayName("추천글 작성 성공 - 사진 포함")
        void insertSR01_withPhoto_success() throws Exception {
            // given
            SR01Request request = new SR01Request("제목", "내용", null, null, 5, 0.0, 0.0, null);

            when(multipartRequest.getFiles("file")).thenReturn(Collections.singletonList(photoFile));
            when(photoFile.getSize()).thenReturn(1024L);
            when(photoFile.getOriginalFilename()).thenReturn("photo.jpg");
            when(s3Properties.tempPath()).thenReturn(tempDir.toString());
            doNothing().when(photoFile).transferTo(any(File.class));
            doNothing().when(s3Util).putS3File(anyString(), any(File.class));

            // when
            SR01Response response = sr01Service.insertSR01(request, multipartRequest, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr01Dao).insertSR01(any(SR01Entity.class));
            verify(sr02Dao).insertSR02(any());
            verify(sr01Dao).insertImage(anyList());
        }

        @Test
        @DisplayName("추천글 작성 성공 - 사진 없음")
        void insertSR01_withoutPhoto_success() throws Exception {
            // given
            SR01Request request = new SR01Request("제목", "내용", null, null, 5, 0.0, 0.0, null);

            when(multipartRequest.getFiles("file")).thenReturn(Collections.emptyList());

            // when
            SR01Response response = sr01Service.insertSR01(request, multipartRequest, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr01Dao).insertSR01(any(SR01Entity.class));
            verify(sr01Dao, never()).insertImage(anyList());
        }
    }

    @Nested
    @DisplayName("selectSR01List() 메서드 테스트")
    class SelectSR01ListTest {

        @Test
        @DisplayName("목록 조회 성공")
        void selectSR01List_success() throws Exception {
            // given
            int nowPage = 1;
            SR01Entity entity1 = new SR01Entity();
            entity1.setSeq(1);
            entity1.setTitle("추천글1");
            entity1.setTotCnt(2);

            SR01Entity entity2 = new SR01Entity();
            entity2.setSeq(2);
            entity2.setTitle("추천글2");

            List<SR01Entity> list = Arrays.asList(entity1, entity2);

            when(sr01Dao.selectSR01(any(SR01Entity.class))).thenReturn(list);

            // when
            SR01ListResponse response = sr01Service.selectSR01List(nowPage);

            // then
            assertNotNull(response);
            verify(sr01Dao).selectSR01(any(SR01Entity.class));
        }

        @Test
        @DisplayName("빈 목록 조회")
        void selectSR01List_emptyList() throws Exception {
            // given
            int nowPage = 1;

            when(sr01Dao.selectSR01(any(SR01Entity.class))).thenReturn(Collections.emptyList());

            // when
            SR01ListResponse response = sr01Service.selectSR01List(nowPage);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("selectOneSR01() 메서드 테스트")
    class SelectOneSR01Test {

        @Test
        @DisplayName("상세 조회 성공 - 본인 글")
        void selectOneSR01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sr01Dao.selectOneSR01(any(SR01Entity.class))).thenReturn(entity);

            // when
            SR01Response response = sr01Service.selectOneSR01(seq, TEST_USER);

            // then
            assertNotNull(response);
            assertTrue(entity.isDeleteYn());
            verify(sr01Dao).clickSR01(any(SR01Entity.class));
        }

        @Test
        @DisplayName("상세 조회 성공 - 타인 글")
        void selectOneSR01_otherPost_success() throws Exception {
            // given
            int seq = 1;
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sr01Dao.selectOneSR01(any(SR01Entity.class))).thenReturn(entity);

            // when
            SR01Response response = sr01Service.selectOneSR01(seq, TEST_USER);

            // then
            assertNotNull(response);
            assertFalse(entity.isDeleteYn());
        }
    }

    @Nested
    @DisplayName("likeSR01() 메서드 테스트")
    class LikeSR01Test {

        @Test
        @DisplayName("좋아요 성공")
        void likeSR01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SR01Response response = sr01Service.likeSR01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr01Dao).likeSR01(any(SR01Entity.class));
            verify(sr01Dao).likelogSR01(any(SR01Entity.class));
        }
    }

    @Nested
    @DisplayName("hateSR01() 메서드 테스트")
    class HateSR01Test {

        @Test
        @DisplayName("싫어요 성공")
        void hateSR01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SR01Response response = sr01Service.hateSR01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sr01Dao).hateSR01(any(SR01Entity.class));
            verify(sr01Dao).hatelogSR01(any(SR01Entity.class));
        }
    }

    @Nested
    @DisplayName("deleteSR01() 메서드 테스트")
    class DeleteSR01Test {

        @Test
        @DisplayName("삭제 성공 - 본인 글")
        void deleteSR01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sr01Dao.selectOneSR01(any(SR01Entity.class))).thenReturn(entity);
            when(sr01Dao.deleteSR01(any(SR01Entity.class))).thenReturn(1);

            // when
            int result = sr01Service.deleteSR01(seq, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sr03Dao).deleteSeqSR03(any());
            verify(sr01Dao).deleteSR01(any(SR01Entity.class));
        }

        @Test
        @DisplayName("삭제 실패 - 타인 글")
        void deleteSR01_otherPost_throwsException() throws Exception {
            // given
            int seq = 1;
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sr01Dao.selectOneSR01(any(SR01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sr01Service.deleteSR01(seq, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("updateSR01(int, SR01Request, String) 메서드 테스트")
    class UpdateSR01RequestTest {

        @Test
        @DisplayName("수정 성공 - 본인 글")
        void updateSR01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SR01Request request = new SR01Request("수정 제목", "수정 내용", null, null, 4, 0.0, 0.0, null);
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sr01Dao.selectOneSR01(any(SR01Entity.class))).thenReturn(entity);
            when(sr01Dao.updateSR01(any(SR01Entity.class))).thenReturn(1);

            // when
            sr01Service.updateSR01(seq, request, TEST_USER);

            // then
            verify(sr01Dao).updateSR01(any(SR01Entity.class));
        }

        @Test
        @DisplayName("수정 실패 - 타인 글")
        void updateSR01_otherPost_throwsException() throws Exception {
            // given
            int seq = 1;
            SR01Request request = new SR01Request("수정 제목", "수정 내용", null, null, 4, 0.0, 0.0, null);
            SR01Entity entity = new SR01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sr01Dao.selectOneSR01(any(SR01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sr01Service.updateSR01(seq, request, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("selectPhoto() 메서드 테스트")
    class SelectPhotoTest {

        @Test
        @DisplayName("사진 스트리밍 성공")
        void selectPhoto_success() throws Exception {
            // given
            int seq = 1;
            int idx = 0;
            String photoPath = "rphoto/1_20240101_1.jpg";
            InputStream mockStream = new ByteArrayInputStream("photo content".getBytes());

            when(sr01Dao.selectPhoto(any(SR01Entity.class))).thenReturn(photoPath);
            when(s3Util.getS3FileStream(photoPath)).thenReturn(mockStream);

            // when
            InputStream result = sr01Service.selectPhoto(seq, idx);

            // then
            assertNotNull(result);
            verify(sr01Dao).selectPhoto(any(SR01Entity.class));
            verify(s3Util).getS3FileStream(photoPath);
        }
    }
}
