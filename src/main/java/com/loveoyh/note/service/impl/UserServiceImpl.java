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
	 * ��¼����ʵ��
	 */
	public User login(String name, String password) throws UserNotFoundException, PasswordException {
		
//		System.out.println("login");
//		String a = null;
//		a.length();
		
		/*
		 * �˴��޸�bug ����������Ƿ�Ϊ��,��ȥ�ձ��Ƿ�Ϊ�ձ���Ż��������
		 */
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("����Ϊ��");
		}
		if(name==null || name.trim().isEmpty()){
			throw new UserNotFoundException("�û�����");
		}
		/*
		 * ��ѯ�û�
		 */
		User user = userDAO.findUserByName(name.trim());
		
		if(user==null){
			throw new UserNotFoundException("�û���������");
		}
		
		/*
		 * ����md5�㷨������������ܵõ�����ժҪ,���ݿ����汣��ľ���ժҪ,������������
		 * �����������ο�����ǿ����ǿ��,��ֹ�ƽ�
		 */
		String salt = "�����������?";
		String md5Pwd = DigestUtils.md5Hex(salt+password);
		
		if(!md5Pwd.trim().equals(user.getPassword())){
			throw new PasswordException("�������");
		}
		return user;
	}
	
	/**
	 * ע�᷽��ʵ��
	 */
	public User regist(String name, String nick, String password, String confirm)throws UserNameException, PasswordException {
		/*
		 * ���name,�����ظ�
		 */
		if(name == null || name.trim().isEmpty()){
			throw new UserNameException("�û���Ϊ��!");
		}
		//����û����Ƿ����
		User one = userDAO.findUserByName(name);
		if(one!=null){
			throw new UserNameException("�ѱ���ע��!");
		}
		/*
		 * �������
		 */
		if(password == null || password.trim().isEmpty()){
			throw new PasswordException("����Ϊ��");
		}
		//����������������Ƿ�һ��
		if(! password.equals(confirm)){
			throw new PasswordException("���벻һ��");
		}
		/*
		 * ���nick,�����ظ�
		 */
		if(nick == null || nick.trim().isEmpty()){
			// ����ǳ�Ϊ��,��ʹ���û���Ϊ�ǳ�
			nick = name;
		}
		
		String id = UUID.randomUUID().toString();
		String token = "";
		//������ܴ���
		password = DigestUtils.md5Hex(salt+password);
		
		User user = new User(id, name, password, token, nick);
		
		int n = userDAO.addUser(user);
		//��ֹ���ݿ������,���ʧ��
		if(n!=1){
			throw new RuntimeException("���ʧ��!");
		}
		return user;
	}

}
