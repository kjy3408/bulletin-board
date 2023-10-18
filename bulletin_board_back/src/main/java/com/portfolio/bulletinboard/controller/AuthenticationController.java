package com.portfolio.bulletinboard.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.bulletinboard.aop.annotation.ValidAspect;
import com.portfolio.bulletinboard.dto.auth.LoginReqDto;
import com.portfolio.bulletinboard.dto.auth.SignupDto;
import com.portfolio.bulletinboard.entity.User;
import com.portfolio.bulletinboard.security.JwtTokenProvider;
import com.portfolio.bulletinboard.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController{
	private final AuthenticationService authenticationService;
	private final JwtTokenProvider jwtTokenProvider;
	
	@ValidAspect
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupDto signupDto, BindingResult bindingResult) {
		authenticationService.checkDuplicatedEmail(signupDto.getEmail());
		authenticationService.signup(signupDto);
		return ResponseEntity.ok().body(true);
	}
	
	@ValidAspect
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto, BindingResult bindingResult) {
		return ResponseEntity.ok(authenticationService.signin(loginReqDto));
	}
	
	@GetMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestHeader(value="Authorization") String accessToken) {
		return ResponseEntity.ok().body(authenticationService.authenticate(accessToken));
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUserInfo(@RequestHeader(value="Authorization") String accessToken) {
		return ResponseEntity.ok().body(authenticationService.getUserInfo(accessToken));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> userDelete(@RequestBody User user){
		return ResponseEntity.ok().body(authenticationService.userDelete(user));
	}
}
