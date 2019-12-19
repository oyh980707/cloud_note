package com.loveoyh.note.test;

import java.util.Properties;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import com.loveoyh.note.dao.UserDAO;
import com.loveoyh.note.entity.User;

public class UserDaoTest extends BaseTest{
	private UserDAO dao;
	private String salt;
	
	@Before
	public void initDao(){
		dao = ctx.getBean("userDAO",UserDAO.class);
		Properties prop = ctx.getBean("jdbc",Properties.class);
		salt = prop.getProperty("salt");
		System.out.println(salt);
	}
	
	@Test
	public void testFindUserByName(){
		String name = "Tom";
		User user = dao.findUserByName(name);
		System.out.println(user);
	}
	
	@Test
	public void testAddUser(){
		/*
		 * 生成独一无二的字符串方法
		 */
		String id = UUID.randomUUID().toString();
		
		String name = "Tom";
		String password = DigestUtils.md5Hex(salt+"123456");
		String token = "";
		String nick = "";
		User user = new User(id, name, password, token, nick);
		int n = dao.addUser(user);
		System.out.println(n);
	}
}
