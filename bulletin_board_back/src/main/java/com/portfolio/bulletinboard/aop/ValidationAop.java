package com.portfolio.bulletinboard.aop;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.portfolio.bulletinboard.exception.CustomException;

@Component
@Aspect
public class ValidationAop {

	@Pointcut("@annotation(com.portfolio.bulletinboard.aop.annotation.ValidAspect)")
	private void pointCut() {}
	
	@Around("pointCut()")
	private Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		Object[] args = joinPoint.getArgs();
		BindingResult bindingResult = null;
		
		for(Object arg : args) {
			if(arg.getClass() == BeanPropertyBindingResult.class) {
				bindingResult = (BindingResult) arg;
			}
		}
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(error -> {
				errorMap.put(error.getField(), error.getDefaultMessage());
			});
			
			throw new CustomException("ValidDation check failed", errorMap);
		}
		
		return joinPoint.proceed();
	}
}
