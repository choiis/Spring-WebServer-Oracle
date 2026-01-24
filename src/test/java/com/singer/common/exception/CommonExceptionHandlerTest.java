package com.singer.common.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommonExceptionHandlerTest {

    private CommonExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new CommonExceptionHandler();
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/test");
    }

    @Nested
    @DisplayName("responseTypes 맵 테스트")
    class ResponseTypesMapTest {

        @Test
        @DisplayName("responseTypes 맵이 null이 아님")
        void responseTypes_isNotNull() {
            assertNotNull(CommonExceptionHandler.responseTypes);
        }

        @Test
        @DisplayName("responseTypes 맵에 모든 ResponseType이 포함됨")
        void responseTypes_containsAllResponseTypes() {
            for (CommonExceptionHandler.ResponseType type : CommonExceptionHandler.ResponseType.values()) {
                assertTrue(CommonExceptionHandler.responseTypes.containsKey(type.getTargetClass()));
            }
        }

        @Test
        @DisplayName("AsyncRequestTimeoutException이 맵에 등록됨")
        void responseTypes_containsAsyncRequestTimeout() {
            assertTrue(CommonExceptionHandler.responseTypes.containsKey(AsyncRequestTimeoutException.class));
        }

        @Test
        @DisplayName("NoHandlerFoundException이 맵에 등록됨")
        void responseTypes_containsNoHandlerFound() {
            assertTrue(CommonExceptionHandler.responseTypes.containsKey(NoHandlerFoundException.class));
        }
    }

    @Nested
    @DisplayName("handle 메서드 - 4xx 클라이언트 에러 테스트")
    class Handle4xxErrorsTest {

        @Test
        @DisplayName("HttpRequestMethodNotSupportedException 처리 - 405 반환")
        void handle_methodNotSupported_returns405() {
            HttpRequestMethodNotSupportedException exception =
                    new HttpRequestMethodNotSupportedException("POST");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().header().isSuccessful());
            assertEquals(-99990, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("HttpMediaTypeNotSupportedException 처리 - 415 반환")
        void handle_mediaTypeNotSupported_returns415() {
            HttpMediaTypeNotSupportedException exception =
                    new HttpMediaTypeNotSupportedException("application/xml");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
            assertEquals(-99991, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("HttpMediaTypeNotAcceptableException 처리 - 406 반환")
        void handle_mediaTypeNotAcceptable_returns406() {
            HttpMediaTypeNotAcceptableException exception =
                    new HttpMediaTypeNotAcceptableException("Not acceptable");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
            assertEquals(-99992, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("MissingServletRequestParameterException 처리 - 400 반환")
        void handle_missingRequestParam_returns400() {
            MissingServletRequestParameterException exception =
                    new MissingServletRequestParameterException("id", "String");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99994, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("MissingServletRequestPartException 처리 - 400 반환")
        void handle_missingRequestPart_returns400() {
            MissingServletRequestPartException exception =
                    new MissingServletRequestPartException("file");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99995, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("ServletRequestBindingException 처리 - 400 반환")
        void handle_servletRequestBinding_returns400() {
            ServletRequestBindingException exception =
                    new ServletRequestBindingException("Binding error");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99996, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("NoHandlerFoundException 처리 - 404 반환")
        void handle_noHandlerFound_returns404() {
            NoHandlerFoundException exception =
                    new NoHandlerFoundException("GET", "/notfound", null);

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(-99997, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("HttpMessageNotReadableException 처리 - 400 반환")
        void handle_messageNotReadable_returns400() {
            HttpMessageNotReadableException exception =
                    new HttpMessageNotReadableException("Message not readable", (org.springframework.http.HttpInputMessage) null);

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99987, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("TypeMismatchException 처리 - 400 반환")
        void handle_typeMismatch_returns400() {
            TypeMismatchException exception =
                    new TypeMismatchException("value", Integer.class);

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99986, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("BindException 처리 - 400 반환")
        void handle_bindException_returns400() {
            BindException exception = new BindException("Bind error");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99989, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("AccessDeniedException 처리 - 403 반환")
        void handle_accessDenied_returns403() {
            AccessDeniedException exception = new AccessDeniedException("Access denied");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            assertEquals(-99982, response.getBody().header().resultCode());
            assertEquals("forbidden", response.getBody().header().resultMessage());
        }
    }

    @Nested
    @DisplayName("handle 메서드 - 5xx 서버 에러 테스트")
    class Handle5xxErrorsTest {

        @Test
        @DisplayName("MissingPathVariableException 처리 - 500 반환")
        void handle_missingPathVariable_returns500() throws NoSuchMethodException {
            MissingPathVariableException exception =
                    new MissingPathVariableException("id",
                            new org.springframework.core.MethodParameter(
                                    String.class.getMethod("toString"), -1));

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(-99993, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("HttpMessageNotWritableException 처리 - 500 반환")
        void handle_messageNotWritable_returns500() {
            HttpMessageNotWritableException exception =
                    new HttpMessageNotWritableException("Message not writable");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(-99988, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("ConversionNotSupportedException 처리 - 500 반환")
        void handle_conversionNotSupported_returns500() {
            ConversionNotSupportedException exception =
                    new ConversionNotSupportedException("value", Integer.class, null);

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(-99985, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("AsyncRequestTimeoutException 처리 - 503 반환")
        void handle_asyncTimeout_returns503() {
            AsyncRequestTimeoutException exception = new AsyncRequestTimeoutException();

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
            assertEquals(-99998, response.getBody().header().resultCode());
        }

        @Test
        @DisplayName("정의되지 않은 예외 처리 - 500 반환")
        void handle_undefinedException_returns500() {
            RuntimeException exception = new RuntimeException("Unknown error");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(-99999, response.getBody().header().resultCode());
            assertEquals("something nasty happened", response.getBody().header().resultMessage());
        }

        @Test
        @DisplayName("NullPointerException은 정의되지 않은 예외로 처리됨")
        void handle_nullPointerException_returns500() {
            NullPointerException exception = new NullPointerException("null value");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(-99999, response.getBody().header().resultCode());
        }
    }

    @Nested
    @DisplayName("ResponseType enum 테스트")
    class ResponseTypeEnumTest {

        @Test
        @DisplayName("TYPE_NOT_DEFINED는 메시지를 포함하지 않음")
        void typeNotDefined_doesNotIncludeExceptionMessage() {
            CommonExceptionHandler.ResponseType type = CommonExceptionHandler.ResponseType.TYPE_NOT_DEFINED;

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, type.getStatus());
            assertEquals(-99999, type.getCode());
            assertEquals(RuntimeException.class, type.getTargetClass());
        }

        @Test
        @DisplayName("METHOD_ARGUMENT_TYPE_MISMATCH는 고정 메시지 사용")
        void methodArgumentTypeMismatch_usesFixedMessage() {
            MethodArgumentTypeMismatchException exception =
                    new MethodArgumentTypeMismatchException("value", Integer.class, "param", null, null);

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(-99984, response.getBody().header().resultCode());
            assertTrue(response.getBody().header().resultMessage().startsWith("argument type mismatch:"));
        }

        @Test
        @DisplayName("FORBIDDEN_403은 예외 메시지를 포함하지 않음")
        void forbidden_doesNotIncludeExceptionMessage() {
            AccessDeniedException exception = new AccessDeniedException("Custom access denied message");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertEquals("forbidden", response.getBody().header().resultMessage());
            assertFalse(response.getBody().header().resultMessage().contains("Custom access denied message"));
        }

        @Test
        @DisplayName("ASYNC_REQ_TIMEOUT은 예외 메시지를 직접 사용")
        void asyncReqTimeout_usesExceptionMessage() {
            CommonExceptionHandler.ResponseType type = CommonExceptionHandler.ResponseType.ASYNC_REQ_TIMEOUT;

            assertEquals(HttpStatus.SERVICE_UNAVAILABLE, type.getStatus());
            assertEquals(-99998, type.getCode());
        }

        @Test
        @DisplayName("모든 ResponseType은 고유한 code를 가짐")
        void allResponseTypes_haveUniqueCodes() {
            CommonExceptionHandler.ResponseType[] types = CommonExceptionHandler.ResponseType.values();
            long distinctCodeCount = java.util.Arrays.stream(types)
                    .map(CommonExceptionHandler.ResponseType::getCode)
                    .distinct()
                    .count();

            assertEquals(types.length, distinctCodeCount);
        }

        @Test
        @DisplayName("모든 ResponseType은 고유한 targetClass를 가짐")
        void allResponseTypes_haveUniqueTargetClasses() {
            CommonExceptionHandler.ResponseType[] types = CommonExceptionHandler.ResponseType.values();
            long distinctClassCount = java.util.Arrays.stream(types)
                    .map(CommonExceptionHandler.ResponseType::getTargetClass)
                    .distinct()
                    .count();

            assertEquals(types.length, distinctClassCount);
        }
    }

    @Nested
    @DisplayName("CommonResponse 반환값 검증")
    class CommonResponseValidationTest {

        @Test
        @DisplayName("에러 응답의 header가 null이 아님")
        void errorResponse_hasNonNullHeader() {
            RuntimeException exception = new RuntimeException("test");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertNotNull(response.getBody());
            assertNotNull(response.getBody().header());
        }

        @Test
        @DisplayName("에러 응답의 data는 null임")
        void errorResponse_hasNullData() {
            RuntimeException exception = new RuntimeException("test");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertNull(response.getBody().data());
        }

        @Test
        @DisplayName("에러 응답의 isSuccessful은 false임")
        void errorResponse_isNotSuccessful() {
            RuntimeException exception = new RuntimeException("test");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertFalse(response.getBody().header().isSuccessful());
        }

        @Test
        @DisplayName("응답에 HttpHeaders가 포함됨")
        void response_hasHttpHeaders() {
            RuntimeException exception = new RuntimeException("test");

            ResponseEntity<CommonResponse<Void>> response = handler.handle(webRequest, exception);

            assertNotNull(response.getHeaders());
        }
    }
}
