package com.loveoyh.note.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loveoyh.note.dao.UserDAO;
import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.UserService;
import com.loveoyh.note.service.ex.PasswordException;
import com.loveoyh.note.service.ex.UserNameException;
import com.loveoyh.note.service.ex.UserNotFoundException;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	@Value("#{jdbc.salt}")
	private String salt;
	
	@Resource
	private UserDAO userDAO;
	
	/**
	 * 登录方法实现
	 */
	public User login(String name, String password) throws UserNotFoundException, PasswordException {
		
//		System.out.println("login");
//		String a = null;
//		a.length();
		
		/*
		 * 此处修复bug 对密码检验是否为空,出去空表是否为空必须放或运算后面
		 */
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("密码为空");
		}
		if(name==null || name.trim().isEmpty()){
			throw new UserNotFoundException("用户名空");
		}
		/*
		 * 查询用户
		 */
		User user = userDAO.findUserByName(name.trim());
		
		if(user==null){
			throw new UserNotFoundException("用户名不存在");
		}
		
		/*
		 * 利用md5算法对明文密码加密得到的是摘要,数据库里面保存的就是摘要,而非明文密码
		 * 在密码后面加盐可以增强加密强度,防止破解
		 */
		String salt = "今天你吃了吗?";
		String md5Pwd = DigestUtils.md5Hex(salt+password);
		
		if(!md5Pwd.trim().equals(user.getPassword())){
			throw new PasswordException("密码错误");
		}
		return user;
	}
	
	/**
	 * 注册方法实现
	 */
	public User regist(String name, String nick, String password, String confirm)throws UserNameException, PasswordException {
		/*
		 * 检查name,不能重复
		 */
		if(name == null || name.trim().isEmpty()){
			throw new UserNameException("用户名为空!");
		}
		//检查用户名是否存在
		User one = userDAO.findUserByName(name);
		if(one!=null){
			throw new UserNameException("已被经注册!");
		}
		/*
		 * 检查密码
		 */
		if(password == null || password.trim().isEmpty()){
			throw new PasswordException("密码为空");
		}
		//检查两次输入密码是否一致
		if(! password.equals(confirm)){
			throw new PasswordException("密码不一致");
		}
		/*
		 * 检查nick,不能重复
		 */
		if(nick == null || nick.trim().isEmpty()){
			// 如果昵称为空,则使用用户名为昵称
			nick = name;
		}
		
		String id = UUID.randomUUID().toString();
		String token = "";
		//密码加密处理
		password = DigestUtils.md5Hex(salt+password);
		
		User user = new User(id, name, password, token, nick);
		
		int n = userDAO.addUser(user);
		//防止数据库出问题,添加失败
		if(n!=1){
			throw new RuntimeException("添加失败!");
		}
		return user;
	}

}
