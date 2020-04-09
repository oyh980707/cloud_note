package com.loveoyh.note.service;

import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.ex.PasswordException;
import com.loveoyh.note.service.ex.UserNameException;
import com.loveoyh.note.service.ex.UserNotFoundException;

/**
 * 业务层接口
 * @author HP
 *
 */
public interface UserService {
	/**
	 * 登陆功能,登陆成功返回用户信息,登录失败则抛出异常
	 * @param name 用户名
	 * @param password 密码
	 * @return 如果登陆成功就返回用户信息
	 * @throws UserNotFoundException 用户不存在
	 * @throws PasswordException 密码错误
	 */
	public User login(String name,String password)throws UserNotFoundException,PasswordException;
	
	/**
	 * UserService 中添加注册功能
	 * @param name 用户名
	 * @param nick 昵称
	 * @param password 密码
	 * @param confirm 确认密码
	 * @return 注册成功的用户信息
	 * @throws UserNameException 用户名异常
	 * @throws PasswordException 密码异常
	 */
	public User regist(String name,String nick,String password,String confirm) throws UserNameException,PasswordException;
	
	/**
	 * 修改密码
	 * @param userId
	 * @param lastPassword
	 * @param password
	 * @param confirm
	 * @throws UserNotFoundException
	 * @throws PasswordException
	 */
	public void changePassword(String userId, String lastPassword, String password, String confirm) throws UserNotFoundException,PasswordException;
	
}
