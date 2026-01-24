package com.singer.application.service.sf;

import com.singer.application.dto.sf.SF01ListResponse;
import com.singer.application.dto.sf.SF01Request;
import com.singer.application.dto.sf.SF01Response;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.domain.dao.sf.SF01Dao;
import com.singer.domain.dao.sf.SF02Dao;
import com.singer.domain.entity.sf.SF01Entity;
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
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SF01ServiceTest {

    @Mock
    private SF01Dao sf01Dao;

    @Mock
    private SF02Dao sf02Dao;

    @Mock
    private S3Properties s3Properties;

    @Mock
    private S3Util s3Util;

    @InjectMocks
    private SF01Service sf01Service;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("insertSF01() 메서드 테스트")
    class InsertSF01Test {

        @Mock
        private MultipartHttpServletRequest multipartRequest;

        @Mock
        private MultipartFile multipartFile;

        @TempDir
        Path tempDir;

        @Test
        @DisplayName("파일 업로드 성공")
        void insertSF01_success() throws Exception {
            // given
            SF01Request request = new SF01Request("제목", "내용", null, null, null);
            Iterator<String> fileNames = Collections.singletonList("file").iterator();

            when(multipartRequest.getFileNames()).thenReturn(fileNames);
            when(multipartRequest.getFile("file")).thenReturn(multipartFile);
            when(multipartFile.getOriginalFilename()).thenReturn("test.pdf");
            when(s3Properties.tempPath()).thenReturn(tempDir.toString());
            doNothing().when(multipartFile).transferTo(any(File.class));
            doNothing().when(s3Util).putS3File(anyString(), any(File.class));
            when(sf01Dao.insertSF01(any(SF01Entity.class))).thenReturn(1);

            // when
            SF01Response response = sf01Service.insertSF01(request, multipartRequest, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf01Dao).insertSF01(any(SF01Entity.class));
            verify(s3Util).putS3File(anyString(), any(File.class));
        }

        @Test
        @DisplayName("파일이 없으면 예외 발생")
        void insertSF01_noFile_throwsException() {
            // given
            SF01Request request = new SF01Request("제목", "내용", null, null, null);
            Iterator<String> emptyIterator = Collections.emptyIterator();

            when(multipartRequest.getFileNames()).thenReturn(emptyIterator);

            // when & then - 파일이 없으면 NullPointerException 발생
            assertThrows(Exception.class, () ->
                    sf01Service.insertSF01(request, multipartRequest, TEST_USER));
        }
    }

    @Nested
    @DisplayName("selectSF01List() 메서드 테스트")
    class SelectSF01ListTest {

        @Test
        @DisplayName("목록 조회 성공")
        void selectSF01List_success() throws Exception {
            // given
            int nowPage = 1;
            SF01Entity entity1 = new SF01Entity();
            entity1.setSeq(1);
            entity1.setTitle("파일1");

            SF01Entity entity2 = new SF01Entity();
            entity2.setSeq(2);
            entity2.setTitle("파일2");

            List<SF01Entity> list = Arrays.asList(entity1, entity2);
            SF01Entity countEntity = new SF01Entity();
            countEntity.setTotCnt(2);

            when(sf01Dao.selectSF01(any(SF01Entity.class))).thenReturn(list);
            when(sf01Dao.selectSF01Count()).thenReturn(countEntity);

            // when
            SF01ListResponse response = sf01Service.selectSF01List(nowPage);

            // then
            assertNotNull(response);
            verify(sf01Dao).selectSF01(any(SF01Entity.class));
            verify(sf01Dao).selectSF01Count();
        }

        @Test
        @DisplayName("빈 목록 조회")
        void selectSF01List_emptyList() throws Exception {
            // given
            int nowPage = 1;

            when(sf01Dao.selectSF01(any(SF01Entity.class))).thenReturn(Collections.emptyList());
            when(sf01Dao.selectSF01Count()).thenReturn(null);

            // when
            SF01ListResponse response = sf01Service.selectSF01List(nowPage);

            // then
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("selectOneSF01() 메서드 테스트")
    class SelectOneSF01Test {

        @Test
        @DisplayName("상세 조회 성공 - 본인 파일")
        void selectOneSF01_ownFile_success() throws Exception {
            // given
            int seq = 1;
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);
            entity.setRegdate("20240101120000");

            when(sf01Dao.selectOneSF01(any(SF01Entity.class))).thenReturn(entity);

            // when
            SF01Response response = sf01Service.selectOneSF01(seq, TEST_USER);

            // then
            assertNotNull(response);
            assertTrue(entity.isDeleteYn());
            verify(sf01Dao).clickSF01(any(SF01Entity.class));
        }

        @Test
        @DisplayName("상세 조회 성공 - 타인 파일")
        void selectOneSF01_otherFile_success() throws Exception {
            // given
            int seq = 1;
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");
            entity.setRegdate("20240101120000");

            when(sf01Dao.selectOneSF01(any(SF01Entity.class))).thenReturn(entity);

            // when
            SF01Response response = sf01Service.selectOneSF01(seq, TEST_USER);

            // then
            assertNotNull(response);
            assertFalse(entity.isDeleteYn());
        }
    }

    @Nested
    @DisplayName("likeSF01() 메서드 테스트")
    class LikeSF01Test {

        @Test
        @DisplayName("좋아요 성공")
        void likeSF01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SF01Response response = sf01Service.likeSF01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf01Dao).likeSF01(any(SF01Entity.class));
            verify(sf01Dao).likelogSF01(any(SF01Entity.class));
        }
    }

    @Nested
    @DisplayName("hateSF01() 메서드 테스트")
    class HateSF01Test {

        @Test
        @DisplayName("싫어요 성공")
        void hateSF01_success() throws Exception {
            // given
            int seq = 1;

            // when
            SF01Response response = sf01Service.hateSF01(seq, TEST_USER);

            // then
            assertNotNull(response);
            verify(sf01Dao).hateSF01(any(SF01Entity.class));
            verify(sf01Dao).hatelogSF01(any(SF01Entity.class));
        }
    }

    @Nested
    @DisplayName("deleteSF01() 메서드 테스트")
    class DeleteSF01Test {

        @Test
        @DisplayName("삭제 성공 - 본인 파일")
        void deleteSF01_ownFile_success() throws Exception {
            // given
            int seq = 1;
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);
            entity.setFtpfilename("test.pdf");

            when(sf01Dao.selectOneSF01(any(SF01Entity.class))).thenReturn(entity);
            when(sf01Dao.selectFile(any(SF01Entity.class))).thenReturn(entity);
            when(sf01Dao.deleteSF01(any(SF01Entity.class))).thenReturn(1);

            // when
            int result = sf01Service.deleteSF01(seq, TEST_USER);

            // then
            assertEquals(1, result);
            verify(sf02Dao).deleteSeqSF02(any());
            verify(s3Util).deleteS3File(anyString());
            verify(sf01Dao).deleteSF01(any(SF01Entity.class));
        }

        @Test
        @DisplayName("삭제 실패 - 타인 파일")
        void deleteSF01_otherFile_throwsException() throws Exception {
            // given
            int seq = 1;
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sf01Dao.selectOneSF01(any(SF01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sf01Service.deleteSF01(seq, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("updateSF01(int, SF01Request, String) 메서드 테스트")
    class UpdateSF01RequestTest {

        @Test
        @DisplayName("수정 성공 - 본인 파일")
        void updateSF01_ownFile_success() throws Exception {
            // given
            int seq = 1;
            SF01Request request = new SF01Request("수정 제목", "수정 내용", null, null, null);
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setUserid(TEST_USER);

            when(sf01Dao.selectOneSF01(any(SF01Entity.class))).thenReturn(entity);
            when(sf01Dao.updateSF01(any(SF01Entity.class))).thenReturn(1);

            // when
            sf01Service.updateSF01(seq, request, TEST_USER);

            // then
            verify(sf01Dao).updateSF01(any(SF01Entity.class));
        }

        @Test
        @DisplayName("수정 실패 - 타인 파일")
        void updateSF01_otherFile_throwsException() throws Exception {
            // given
            int seq = 1;
            SF01Request request = new SF01Request("수정 제목", "수정 내용", null, null, null);
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setUserid("otheruser");

            when(sf01Dao.selectOneSF01(any(SF01Entity.class))).thenReturn(entity);

            // when & then
            ClientException exception = assertThrows(ClientException.class, () ->
                    sf01Service.updateSF01(seq, request, TEST_USER));
            assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatusCode());
        }
    }

    @Nested
    @DisplayName("selectFile() 메서드 테스트")
    class SelectFileTest {

        @TempDir
        Path tempDir;

        @Test
        @DisplayName("파일 다운로드 성공")
        void selectFile_success() throws Exception {
            // given
            int seq = 1;
            SF01Entity entity = new SF01Entity();
            entity.setSeq(seq);
            entity.setFtpfilename("test.pdf");
            entity.setFilename("원본파일.pdf");

            InputStream mockStream = new ByteArrayInputStream("file content".getBytes());

            when(sf01Dao.selectFile(any(SF01Entity.class))).thenReturn(entity);
            when(s3Util.getS3FileStream(anyString())).thenReturn(mockStream);
            when(s3Properties.tempPath()).thenReturn(tempDir.toString());

            // when
            File result = sf01Service.selectFile(seq, TEST_USER);

            // then
            assertNotNull(result);
            verify(sf01Dao).mergeSFD1(any(SF01Entity.class));
        }
    }
}
