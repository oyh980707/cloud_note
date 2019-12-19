package com.loveoyh.note.dao;

import com.loveoyh.note.entity.User;

public interface UserDAO {
	
	public User findUserByName(String name);
	
	public int addUser(User user);

	public User findUserById(String userId);
}
