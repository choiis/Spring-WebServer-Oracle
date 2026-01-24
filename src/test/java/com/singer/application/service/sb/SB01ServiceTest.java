package com.singer.application.service.sb;

import com.singer.application.dto.sb.SB01ListResponse;
import com.singer.application.dto.sb.SB01Request;
import com.singer.application.dto.sb.SB01Response;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.util.Constants.YES_NO;
import com.singer.domain.dao.sb.SB01Dao;
import com.singer.domain.dao.sb.SB02Dao;
import com.singer.domain.entity.sb.SB01Entity;
import com.singer.infrastructure.config.S3Properties;
import com.singer.infrastructure.util.S3Util;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SB01ServiceTest {

    @Mock
    private SB01Dao sb01Dao;

    @Mock
    private SB02Dao sb02Dao;

    @Mock
    private S3Util s3Util;

    @Mock
    private S3Properties s3Properties;

    @InjectMocks
    private SB01Service sb01Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSB01() 메서드 테스트")
    class InsertSB01Test {

        @Mock
        private MultipartHttpServletRequest multipartRequest;

        @Mock
        private MultipartFile videoFile;

        @TempDir
        Path tempDir;

        @Test
        @DisplayName("비디오 파일 업로드 성공")
        void insertSB01_withVideoFile_success() throws Exception {
            // given
            SB01Request request = new SB01Request("제목", "내용", TEST_USER, null, null, null, null);
            Iterator<String> fileNames = Collections.singletonList("video").iterator();

            when(multipartRequest.getFileNames()).thenReturn(fileNames);
            when(multipartRequest.getFile("video")).thenReturn(videoFile);
            when(videoFile.getOriginalFilename()).thenReturn("test.mp4");
            when(s3Properties.tempPath()).thenReturn(tempDir.toString());
            doNothing().when(videoFile).transferTo(any(File.class));
            doNothing().when(s3Util).putS3File(anyString(), any(File.class));
            when(sb01Dao.insertVideo(any(SB01Entity.class))).thenReturn(1);

            // when
            SB01Response response = sb01Service.insertSB01(request, multipartRequest, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb01Dao).insertSB01(any(SB01Entity.class));
            verify(sb01Dao).insertVideo(any(SB01Entity.class));
            verify(s3Util).putS3File(anyString(), any(File.class));
        }

        @Test
        @DisplayName("오디오 파일 업로드 성공")
        void insertSB01_withAudioFile_success() throws Exception {
            // given
            SB01Request request = new SB01Request("제목", "내용", TEST_USER, null, null, null, null);
            Iterator<String> fileNames = Collections.singletonList("audio").iterator();

            when(multipartRequest.getFileNames()).thenReturn(fileNames);
            when(multipartRequest.getFile("audio")).thenReturn(videoFile);
            when(videoFile.getOriginalFilename()).thenReturn("test.mp3");
            when(s3Properties.tempPath()).thenReturn(tempDir.toString());
            doNothing().when(videoFile).transferTo(any(File.class));
            doNothing().when(s3Util).putS3File(anyString(), any(File.class));
            when(sb01Dao.insertVideo(any(SB01Entity.class))).thenReturn(1);

            // when
            SB01Response response = sb01Service.insertSB01(request, multipartRequest, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb01Dao).insertSB01(any(SB01Entity.class));
        }

        @Test
        @DisplayName("지원하지 않는 파일 형식이면 AppException 발생")
        void insertSB01_withUnsupportedFile_throwsException() throws Exception {
            // given
            SB01Request request = new SB01Request("제목", "내용", TEST_USER, null, null, null, null);
            Iterator<String> fileNames = Collections.singletonList("file").iterator();

            when(multipartRequest.getFileNames()).thenReturn(fileNames);
            when(multipartRequest.getFile("file")).thenReturn(videoFile);
            when(videoFile.getOriginalFilename()).thenReturn("test.txt");

            // when & then
            assertThrows(AppException.class, () ->
                    sb01Service.insertSB01(request, multipartRequest, TEST_USER));
        }

        @Test
        @DisplayName("파일이 없으면 예외 발생")
        void insertSB01_withNoFile_throwsException() {
            // given
            SB01Request request = new SB01Request("제목", "내용", TEST_USER, null, null, null, null);
            Iterator<String> emptyIterator = Collections.emptyIterator();

            when(multipartRequest.getFileNames()).thenReturn(emptyIterator);

            // when & then - 파일이 없으면 NullPointerException 발생 (video가 null인 상태로 getOriginalFilename 호출)
            assertThrows(Exception.class, () ->
                    sb01Service.insertSB01(request, multipartRequest, TEST_USER));
        }
    }

    @Nested
    @DisplayName("selectSB01List() 메서드 테스트")
    class SelectSB01ListTest {

        @Test
        @DisplayName("목록 조회 성공")
        void selectSB01List_success() throws Exception {
            // given
            int nowPage = 1;
            SB01Entity entity1 = new SB01Entity();
            entity1.setSeq(1);
            entity1.setTitle("제목1");

            SB01Entity entity2 = new SB01Entity();
            entity2.setSeq(2);
            entity2.setTitle("제목2");

            List<SB01Entity> list = Arrays.asList(entity1, entity2);
            SB01Entity countEntity = new SB01Entity();
            countEntity.setTotCnt(2);

            when(sb01Dao.selectSB01(any(SB01Entity.class))).thenReturn(list);
            when(sb01Dao.selectSB01Count()).thenReturn(countEntity);

            // when
            SB01ListResponse response = sb01Service.selectSB01List(nowPage);

            // then
            assertNotNull(response);
            verify(sb01Dao).selectSB01(any(SB01Entity.class));
            verify(sb01Dao).selectSB01Count();
        }

        @Test
        @DisplayName("빈 목록 조회")
        void selectSB01List_emptyList() throws Exception {
            // given
            int nowPage = 1;

            when(sb01Dao.selectSB01(any(SB01Entity.class))).thenReturn(Collections.emptyList());
            when(sb01Dao.selectSB01Count()).thenReturn(null);

            // when
            SB01ListResponse response = sb01Service.selectSB01List(nowPage);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("selectOneSB01() 메서드 테스트")
    class SelectOneSB01Test {

        @Test
        @DisplayName("상세 조회 성공 - 본인 글")
        void selectOneSB01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SB01Entity entity = new SB01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);
            entity.setRegdate("20240101120000");

            when(sb01Dao.selectOneSB01(any(SB01Entity.class))).thenReturn(entity);

            // when
            SB01Response response = sb01Service.selectOneSB01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb01Dao).clickSB01(any(SB01Entity.class));
            verify(sb01Dao).selectOneSB01(any(SB01Entity.class));
        }

        @Test
        @DisplayName("상세 조회 성공 - 타인 글")
        void selectOneSB01_otherPost_success() throws Exception {
            // given
            int seq = 1;
            SB01Entity entity = new SB01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");
            entity.setRegdate("20240101120000");

            when(sb01Dao.selectOneSB01(any(SB01Entity.class))).thenReturn(entity);

            // when
            SB01Response response = sb01Service.selectOneSB01(seq, TEST_USER);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("likeSB01() 메서드 테스트")
    class LikeSB01Test {

        @Test
        @DisplayName("좋아요 성공")
        void likeSB01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SB01Response response = sb01Service.likeSB01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb01Dao).likeSB01(any(SB01Entity.class));
            verify(sb01Dao).likelogSB01(any(SB01Entity.class));
        }
    }

    @Nested
    @DisplayName("hateSB01V() 메서드 테스트")
    class HateSB01Test {

        @Test
        @DisplayName("싫어요 성공")
        void hateSB01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SB01Response response = sb01Service.hateSB01V(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sb01Dao).hateSB01(any(SB01Entity.class));
            verify(sb01Dao).hatelogSB01(any(SB01Entity.class));
        }
    }

    @Nested
    @DisplayName("deleteSB01() 메서드 테스트")
    class DeleteSB01Test {

        @Test
        @DisplayName("삭제 성공 - 본인 글")
        void deleteSB01_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SB01Entity entity = new SB01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sb01Dao.selectOneSB01(any(SB01Entity.class))).thenReturn(entity);
            when(sb01Dao.deleteSB01(any(SB01Entity.class))).thenReturn(1);

            // when
            int result = sb01Service.deleteSB01(seq, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sb02Dao).deleteSeqSB02(any());
            verify(sb01Dao).deleteSB01(any(SB01Entity.class));
        }

        @Test
        @DisplayName("삭제 실패 - 타인 글")
        void deleteSB01_otherPost_throwsException() throws Exception {
            // given
            int seq = 1;
            SB01Entity entity = new SB01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sb01Dao.selectOneSB01(any(SB01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sb01Service.deleteSB01(seq, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("updateSB01Simple() 메서드 테스트")
    class UpdateSB01SimpleTest {

        @Test
        @DisplayName("수정 성공 - 본인 글")
        void updateSB01Simple_ownPost_success() throws Exception {
            // given
            int seq = 1;
            SB01Request request = new SB01Request("수정 제목", "수정 내용", TEST_USER, null, null, null, null);
            SB01Entity entity = new SB01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sb01Dao.selectOneSB01(any(SB01Entity.class))).thenReturn(entity);

            // when
            sb01Service.updateSB01Simple(seq, request, TEST_USER);

            // then
            verify(sb01Dao).updateSB01(any(SB01Entity.class));
        }

        @Test
        @DisplayName("수정 실패 - 타인 글")
        void updateSB01Simple_otherPost_throwsException() throws Exception {
            // given
            int seq = 1;
            SB01Request request = new SB01Request("수정 제목", "수정 내용", TEST_USER, null, null, null, null);
            SB01Entity entity = new SB01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sb01Dao.selectOneSB01(any(SB01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sb01Service.updateSB01Simple(seq, request, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("selectVideo() 메서드 테스트")
    class SelectVideoTest {

        @Test
        @DisplayName("비디오 스트리밍 성공")
        void selectVideo_success() throws Exception {
            // given
            int seq = 1;
            String videoPath = "video/test.mp4";
            InputStream mockStream = new ByteArrayInputStream("video content".getBytes());

            when(sb01Dao.selectVideo(any(SB01Entity.class))).thenReturn(videoPath);
            when(s3Util.getS3FileStream(videoPath)).thenReturn(mockStream);

            // when
            InputStream result = sb01Service.selectVideo(seq, null);

            // then
            assertNotNull(result);
            verify(sb01Dao).selectVideo(any(SB01Entity.class));
            verify(s3Util).getS3FileStream(videoPath);
        }
    }
}
