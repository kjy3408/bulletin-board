package com.portfolio.bulletinboard.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.bulletinboard.entity.Authority;
import com.portfolio.bulletinboard.entity.User;

@Mapper
public interface UserRepository {
	public User findUserByEmail(String email);
	public User findUserByName(Map<String, Object> map);
	public int saveUser(User user);
	public int saveAuthority(Authority authority);
	public int updatePassword(User user);
	public int userDelete(User user);
}
