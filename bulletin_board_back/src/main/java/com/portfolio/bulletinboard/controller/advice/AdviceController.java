package com.portfolio.bulletinboard.controller.advice;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portfolio.bulletinboard.dto.common.ErrorResponseDto;
import com.portfolio.bulletinboard.exception.CustomException;
import com.portfolio.bulletinboard.exception.ErrorMap;

@RestControllerAdvice
public class AdviceController {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> customException(CustomException e) {
		return ResponseEntity.badRequest().body(new ErrorResponseDto<>(e.getMessage(), e.getErrorMap()));
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> customException(UsernameNotFoundException e) {
		return ResponseEntity.badRequest().body(new ErrorResponseDto<>(e.getMessage(), null));
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> duplicatedPhone(SQLIntegrityConstraintViolationException e) {
		return ResponseEntity.badRequest().body(new ErrorResponseDto<>("Duplicated Phone", ErrorMap.builder().put("phone", "이미 등록된 전화번호 입니다.").build()));
	}
}
