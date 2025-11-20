package com.singer.common.exception;

import java.net.BindException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	public static final Map<Class<?>, ResponseType> responseTypes = Arrays.stream(ResponseType.values())
			.collect(Collectors.toUnmodifiableMap(ResponseType::getTargetClass, Function.identity()));

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<CommonResponse<Void>> handle(WebRequest request, Exception e) {
		var responseType = responseTypes.getOrDefault(e.getClass(), ResponseType.TYPE_NOT_DEFINED);
		if (responseType.getStatus().is5xxServerError()) {
			log.error("handling error {}", request.getDescription(false), e);
		} else {
			log.warn("handling exception {}", request.getDescription(false), e);
		}
		return new ResponseEntity<>(CommonResponse.error(responseType.code, responseType.getMessage(e)),
				new HttpHeaders(), responseType.status);
	}

	@Getter
	public enum ResponseType {
		TYPE_NOT_DEFINED(INTERNAL_SERVER_ERROR, -99999, "something nasty happened", RuntimeException.class, false),
		ASYNC_REQ_TIMEOUT(SERVICE_UNAVAILABLE, -99998, AsyncRequestTimeoutException.class),
		NO_HANDLER_FOUND(NOT_FOUND, -99997, NoHandlerFoundException.class),
		SERVLET_REQ_BINDING(BAD_REQUEST, -99996, ServletRequestBindingException.class),
		MISSING_SERVLET_REQ_PART(BAD_REQUEST, -99995, MissingServletRequestPartException.class),
		MISSING_SERVLET_REQ_PARAM(BAD_REQUEST, -99994, MissingServletRequestParameterException.class),
		MISSING_PATH_VAR(INTERNAL_SERVER_ERROR, -99993, MissingPathVariableException.class),
		MEDIA_TYPE_NOT_ACCEPTABLE(NOT_ACCEPTABLE, -99992, HttpMediaTypeNotAcceptableException.class),
		MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, -99991, HttpMediaTypeNotSupportedException.class),
		METHOD_NOT_SUPPORTED(METHOD_NOT_ALLOWED, -99990, HttpRequestMethodNotSupportedException.class),
		BIND_EXCEPTION(BAD_REQUEST, -99989, BindException.class),
		HTTP_MSG_NOT_WRITABLE(INTERNAL_SERVER_ERROR, -99988, HttpMessageNotWritableException.class),
		HTTP_MSG_NOT_READABLE(BAD_REQUEST, -99987, HttpMessageNotReadableException.class),
		TYPE_MISMATCH(BAD_REQUEST, -99986, TypeMismatchException.class),
		CONVERSION_NOT_SUPPORTED(INTERNAL_SERVER_ERROR, -99985, ConversionNotSupportedException.class),

		METHOD_ARGUMENT_TYPE_MISMATCH(BAD_REQUEST, -99984, "argument type mismatch",
				MethodArgumentTypeMismatchException.class),
		METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, -99983, "argument not valid", MethodArgumentNotValidException.class,
				false),
		FORBIDDEN_403(FORBIDDEN, -99982, "forbidden", org.springframework.security.access.AccessDeniedException.class,
				false);

		private final HttpStatus status;
		private final int code;
		private final Class<? extends Throwable> targetClass;

		private final Function<Throwable, String> messageGenerator;

		ResponseType(HttpStatus status, int code, Class<? extends Throwable> targetClass) {
			this.status = status;
			this.code = code;
			this.targetClass = targetClass;
			this.messageGenerator = Throwable::getMessage;
		}

		ResponseType(HttpStatus status, int code, String message, Class<? extends Throwable> targetClass) {
			this(status, code, message, targetClass, true);
		}

		ResponseType(HttpStatus status, int code, String message, Class<? extends Throwable> targetClass,
				boolean includeExceptionMessage) {
			this.status = status;
			this.code = code;
			this.targetClass = targetClass;
			this.messageGenerator = e -> includeExceptionMessage ? String.format("%s: %s", message, e.getMessage())
					: message;
		}

		private String getMessage(Throwable e) {
			return messageGenerator.apply(e);
		}
	}
}
