package com.loveoyh.note.dao;

import org.apache.ibatis.annotations.Param;

import com.loveoyh.note.entity.User;

public interface UserDAO {
	
	public User findUserByName(String name);
	
	public int addUser(User user);

	public User findUserById(String userId);

	public int updatePassword(@Param("userId") String userId, @Param("password") String password);
}
