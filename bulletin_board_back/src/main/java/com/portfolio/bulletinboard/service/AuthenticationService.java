package com.portfolio.bulletinboard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portfolio.bulletinboard.dto.auth.LoginReqDto;
import com.portfolio.bulletinboard.dto.auth.SignupDto;
import com.portfolio.bulletinboard.entity.Authority;
import com.portfolio.bulletinboard.entity.User;
import com.portfolio.bulletinboard.exception.CustomException;
import com.portfolio.bulletinboard.exception.ErrorMap;
import com.portfolio.bulletinboard.repository.UserRepository;
import com.portfolio.bulletinboard.security.JwtTokenProvider;
import com.portfolio.bulletinboard.security.PrincipalUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public void checkDuplicatedEmail(String email) {
		if(userRepository.findUserByEmail(email) != null) {
			throw new CustomException("Duplicated Email", 
					ErrorMap.builder().put("email", "사용중인 이메일입니다").build());
		}
	}
	
	public void signup(SignupDto signupDto) {
		
		if(misMatchPassword(signupDto)) {
			throw new CustomException("error", 
					ErrorMap.builder().put("password", "비밀번호가 일치하지 않습니다.").build());
		}
		
		User userEntity = signupDto.toEntity();
		
		userRepository.saveUser(userEntity);
		
		userRepository.saveAuthority(Authority.builder()
									.userId(userEntity.getUserId())
									.roleId(2)
									.build());
	}
	
	public boolean misMatchPassword(SignupDto signupDto) {
		return !signupDto.getPassword().equals(signupDto.getCheckPassword());
	}
	
	public String signin(LoginReqDto loginReqDto) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		return jwtTokenProvider.generateToken(authentication);
	}
	
	public PrincipalUser getUserInfo(String accessToken) {
		try {
			String email = jwtTokenProvider.getClaims(jwtTokenProvider.getToken(accessToken)).get("email").toString();
			User userEntity = userRepository.findUserByEmail(email); 
			
			return userEntity.toPrincipal();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findUserByEmail(username);
		
		return userEntity.toPrincipal();
	}
	
	public Map<String, Object> authenticate(String accessToken) {
		try {
			String email = jwtTokenProvider.getClaims(jwtTokenProvider.getToken(accessToken)).get("email").toString();
			User userEntity = userRepository.findUserByEmail(email);			

			Map<String, Object> map = new HashMap<>();
			map.put("authority", userEntity);
			map.put("getToken", jwtTokenProvider.validateToken(jwtTokenProvider.getToken(accessToken)));

			return map;
		} catch (Exception e) {
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("getToken", jwtTokenProvider.validateToken(jwtTokenProvider.getToken(accessToken)));
			errorMap.put("authority", null);
			return errorMap;
		}
		
		
	}

	public int userDelete(User user) {
		userRepository.userDelete(user);
		return userRepository.userDelete(user);
	}
}
