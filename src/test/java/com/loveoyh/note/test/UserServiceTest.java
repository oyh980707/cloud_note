package com.loveoyh.note.test;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.UserService;
import com.loveoyh.note.service.ex.PasswordException;
import com.loveoyh.note.service.ex.UserNameException;

public class UserServiceTest extends BaseTest {
	
	private UserService service;
	
	@Before
	public void initService(){
		service = ctx.getBean("userService",UserService.class);
	}
	
	
	
	@Test
	public void testLogin(){
		String name = "demo";
		String password = "123321";
		User user = service.login(name, password);
		System.out.println(user); 
	}
	
	@Test
	public void testRegist(){
		User user = null;
		//为什么这里要处理异常?????????????????????
		try {
			user = service.regist("Jack", "", "123456", "123456");
		} catch (PasswordException e) {
			e.printStackTrace();
		} catch (UserNameException e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}
	
}
