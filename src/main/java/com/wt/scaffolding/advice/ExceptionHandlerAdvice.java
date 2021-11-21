package com.wt.scaffolding.advice;

import com.wt.scaffolding.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * springmvc异常处理
 * 
 * @author 吴涛
 *
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo badRequestException(IllegalArgumentException exception) {
		return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
	}

//	@ExceptionHandler({ AccessDeniedException.class })
//	@ResponseStatus(HttpStatus.FORBIDDEN)
//	public ResponseInfo badRequestException(AccessDeniedException exception) {
//		return new ResponseInfo(HttpStatus.FORBIDDEN.value() + "", exception.getMessage());
//	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
			UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo badRequestException(Exception exception) {
		return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseInfo exception(Throwable throwable) {
		log.error("系统异常", throwable);
		return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", throwable.getMessage());

	}

}
