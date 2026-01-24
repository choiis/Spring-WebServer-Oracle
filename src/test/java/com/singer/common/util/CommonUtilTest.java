package com.singer.common.util;

import com.singer.common.exception.ClientException;
import com.singer.common.util.Constants.BROWSER_CODE;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommonUtilTest {

    @Nested
    @DisplayName("nvl() 메서드 테스트")
    class NvlTest {

        @Test
        @DisplayName("문자열이 null이면 기본값 반환")
        void nvl_withNull_returnsDefaultValue() {
            String result = CommonUtil.nvl(null, "default");
            assertEquals("default", result);
        }

        @Test
        @DisplayName("문자열이 빈 문자열이면 기본값 반환")
        void nvl_withEmptyString_returnsDefaultValue() {
            String result = CommonUtil.nvl("", "default");
            assertEquals("default", result);
        }

        @Test
        @DisplayName("문자열이 유효하면 원래 값 반환")
        void nvl_withValidString_returnsOriginalValue() {
            String result = CommonUtil.nvl("value", "default");
            assertEquals("value", result);
        }

        @Test
        @DisplayName("Object가 null이면 기본값 반환")
        void nvl_withNullObject_returnsDefaultValue() {
            Object result = CommonUtil.nvl((Object) null, "default");
            assertEquals("default", result);
        }

        @Test
        @DisplayName("Object가 유효하면 원래 값 반환")
        void nvl_withValidObject_returnsOriginalObject() {
            Integer original = 123;
            Object result = CommonUtil.nvl(original, "default");
            assertEquals(original, result);
        }
    }

    @Nested
    @DisplayName("getPageCnt() 메서드 테스트")
    class GetPageCntTest {

        @Test
        @DisplayName("1개 항목이면 1페이지")
        void getPageCnt_with1Item_returns1Page() {
            assertEquals(1, CommonUtil.getPageCnt(1));
        }

        @Test
        @DisplayName("10개 항목이면 1페이지 (ROW_PER_PAGE = 10)")
        void getPageCnt_with10Items_returns1Page() {
            assertEquals(1, CommonUtil.getPageCnt(10));
        }

        @Test
        @DisplayName("11개 항목이면 2페이지")
        void getPageCnt_with11Items_returns2Pages() {
            assertEquals(2, CommonUtil.getPageCnt(11));
        }

        @Test
        @DisplayName("25개 항목이면 3페이지")
        void getPageCnt_with25Items_returns3Pages() {
            assertEquals(3, CommonUtil.getPageCnt(25));
        }
    }

    @Nested
    @DisplayName("chkDOCFile() 메서드 테스트")
    class ChkDocFileTest {

        @ParameterizedTest
        @ValueSource(strings = {"test.doc", "test.DOC", "test.docx", "test.DOCX"})
        @DisplayName("Word 문서 파일 확인")
        void chkDOCFile_withWordFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkDOCFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.xls", "test.XLS", "test.xlsx", "test.XLSX"})
        @DisplayName("Excel 파일 확인")
        void chkDOCFile_withExcelFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkDOCFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.pdf", "test.PDF", "test.csv", "test.CSV", "test.hwp", "test.HWP"})
        @DisplayName("기타 문서 파일 확인")
        void chkDOCFile_withOtherDocFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkDOCFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.txt", "test.jpg", "test.png", "test.exe"})
        @DisplayName("문서가 아닌 파일은 false 반환")
        void chkDOCFile_withNonDocFiles_returnsFalse(String fileName) throws Exception {
            assertFalse(CommonUtil.chkDOCFile(fileName));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("null 또는 빈 파일명은 예외 발생")
        void chkDOCFile_withNullOrEmpty_throwsException(String fileName) {
            assertThrows(Exception.class, () -> CommonUtil.chkDOCFile(fileName));
        }
    }

    @Nested
    @DisplayName("chkIMGFile() 메서드 테스트")
    class ChkImgFileTest {

        @ParameterizedTest
        @ValueSource(strings = {"test.png", "test.PNG", "test.jpg", "test.JPG", "test.jpeg", "test.JPEG"})
        @DisplayName("일반 이미지 파일 확인")
        void chkIMGFile_withCommonImageFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkIMGFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.gif", "test.GIF", "test.bmp", "test.BMP", "test.tif", "test.tiff"})
        @DisplayName("기타 이미지 파일 확인")
        void chkIMGFile_withOtherImageFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkIMGFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.mp4", "test.doc", "test.txt", "test.exe"})
        @DisplayName("이미지가 아닌 파일은 false 반환")
        void chkIMGFile_withNonImageFiles_returnsFalse(String fileName) throws Exception {
            assertFalse(CommonUtil.chkIMGFile(fileName));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("null 또는 빈 파일명은 예외 발생")
        void chkIMGFile_withNullOrEmpty_throwsException(String fileName) {
            assertThrows(Exception.class, () -> CommonUtil.chkIMGFile(fileName));
        }
    }

    @Nested
    @DisplayName("chkVideoFile() 메서드 테스트")
    class ChkVideoFileTest {

        @ParameterizedTest
        @ValueSource(strings = {"test.avi", "test.AVI", "test.mp4", "test.MP4", "test.wmv", "test.mov"})
        @DisplayName("동영상 파일 확인")
        void chkVideoFile_withVideoFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkVideoFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.mpeg", "test.flv", "test.asf", "test.rm", "test.dat"})
        @DisplayName("기타 동영상 파일 확인")
        void chkVideoFile_withOtherVideoFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkVideoFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.jpg", "test.doc", "test.mp3", "test.txt"})
        @DisplayName("동영상이 아닌 파일은 false 반환")
        void chkVideoFile_withNonVideoFiles_returnsFalse(String fileName) throws Exception {
            assertFalse(CommonUtil.chkVideoFile(fileName));
        }
    }

    @Nested
    @DisplayName("chkAudioFile() 메서드 테스트")
    class ChkAudioFileTest {

        @ParameterizedTest
        @ValueSource(strings = {"test.mp3", "test.MP3", "test.ogg", "test.wav", "test.wma"})
        @DisplayName("오디오 파일 확인")
        void chkAudioFile_withAudioFiles_returnsTrue(String fileName) throws Exception {
            assertTrue(CommonUtil.chkAudioFile(fileName));
        }

        @ParameterizedTest
        @ValueSource(strings = {"test.jpg", "test.doc", "test.mp4", "test.txt"})
        @DisplayName("오디오가 아닌 파일은 false 반환")
        void chkAudioFile_withNonAudioFiles_returnsFalse(String fileName) throws Exception {
            assertFalse(CommonUtil.chkAudioFile(fileName));
        }
    }

    @Nested
    @DisplayName("getExtensionName() 메서드 테스트")
    class GetExtensionNameTest {

        @ParameterizedTest
        @CsvSource({
            "test.txt, txt",
            "document.pdf, pdf",
            "image.PNG, PNG",
            "file.name.jpg, jpg"
        })
        @DisplayName("파일 확장자 추출")
        void getExtensionName_withValidFileName_returnsExtension(String fileName, String expected) {
            assertEquals(expected, CommonUtil.getExtensionName(fileName));
        }

        @Test
        @DisplayName("확장자 없는 파일명은 null 반환")
        void getExtensionName_withNoExtension_returnsNull() {
            assertNull(CommonUtil.getExtensionName("filename"));
        }
    }

    @Nested
    @DisplayName("getIp() 메서드 테스트")
    class GetIpTest {

        @Test
        @DisplayName("X-Forwarded-For 헤더가 있으면 해당 IP 반환")
        void getIp_withXForwardedFor_returnsForwardedIp() {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("X-Forwarded-For")).thenReturn("192.168.1.100");

            String ip = CommonUtil.getIp(request);

            assertEquals("192.168.1.100", ip);
        }

        @Test
        @DisplayName("X-Forwarded-For가 없으면 Proxy-Client-IP 확인")
        void getIp_withProxyClientIp_returnsProxyIp() {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("X-Forwarded-For")).thenReturn(null);
            when(request.getHeader("Proxy-Client-IP")).thenReturn("192.168.1.101");

            String ip = CommonUtil.getIp(request);

            assertEquals("192.168.1.101", ip);
        }

        @Test
        @DisplayName("헤더가 모두 없으면 RemoteAddr 반환")
        void getIp_withNoHeaders_returnsRemoteAddr() {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("X-Forwarded-For")).thenReturn(null);
            when(request.getHeader("Proxy-Client-IP")).thenReturn(null);
            when(request.getHeader("HTTP_CLIENT_IP")).thenReturn(null);
            when(request.getHeader("HTTP_X_FORWARDED_FOR")).thenReturn(null);
            when(request.getRemoteAddr()).thenReturn("127.0.0.1");

            String ip = CommonUtil.getIp(request);

            assertEquals("127.0.0.1", ip);
        }
    }

    @Nested
    @DisplayName("getBrower() 메서드 테스트")
    class GetBrowserTest {

        @Test
        @DisplayName("Chrome User-Agent면 CHROME 반환")
        void getBrowser_withChrome_returnsChrome() throws ClientException {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 Chrome/120.0.0.0 Safari/537.36");

            assertEquals(BROWSER_CODE.CHROME, CommonUtil.getBrower(request));
        }

        @Test
        @DisplayName("Firefox User-Agent면 FIREFOX 반환")
        void getBrowser_withFirefox_returnsFirefox() throws ClientException {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 Firefox/120.0");

            assertEquals(BROWSER_CODE.FIREFOX, CommonUtil.getBrower(request));
        }

        @Test
        @DisplayName("Safari User-Agent면 SAFARI 반환")
        void getBrowser_withSafari_returnsSafari() throws ClientException {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 Safari/605.1.15");

            assertEquals(BROWSER_CODE.SAFARI, CommonUtil.getBrower(request));
        }

        @Test
        @DisplayName("IE User-Agent면 IE 반환")
        void getBrowser_withIE_returnsIE() throws ClientException {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0 Trident/7.0");

            assertEquals(BROWSER_CODE.IE, CommonUtil.getBrower(request));
        }
    }

    @Nested
    @DisplayName("ajaxCheck() 메서드 테스트")
    class AjaxCheckTest {

        @Test
        @DisplayName("XMLHttpRequest 헤더가 있으면 true 반환")
        void ajaxCheck_withXmlHttpRequest_returnsTrue() {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");

            assertTrue(CommonUtil.ajaxCheck(request));
        }

        @Test
        @DisplayName("XMLHttpRequest 헤더가 없으면 false 반환")
        void ajaxCheck_withoutXmlHttpRequest_returnsFalse() {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("X-Requested-With")).thenReturn(null);

            assertFalse(CommonUtil.ajaxCheck(request));
        }

        @Test
        @DisplayName("다른 헤더 값이면 false 반환")
        void ajaxCheck_withDifferentValue_returnsFalse() {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("X-Requested-With")).thenReturn("OtherValue");

            assertFalse(CommonUtil.ajaxCheck(request));
        }
    }
}
