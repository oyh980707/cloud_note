package com.loveoyh.note.service;

import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.ex.PasswordException;
import com.loveoyh.note.service.ex.UserNameException;
import com.loveoyh.note.service.ex.UserNotFoundException;

/**
 * ҵ���ӿ�
 * @author HP
 *
 */
public interface UserService {
	/**
	 * ��½����,��½�ɹ������û���Ϣ,��¼ʧ�����׳��쳣
	 * @param name �û���
	 * @param password ����
	 * @return �����½�ɹ��ͷ����û���Ϣ
	 * @throws UserNotFoundException �û�������
	 * @throws PasswordException �������
	 */
	public User login(String name,String password)throws UserNotFoundException,PasswordException;
	
	/**
	 * UserService �����ע�Ṧ��
	 * @param name �û���
	 * @param nick �ǳ�
	 * @param password ����
	 * @param confirm ȷ������
	 * @return ע��ɹ����û���Ϣ
	 * @throws UserNameException �û����쳣
	 * @throws PasswordException �����쳣
	 */
	public User regist(String name,String nick,String password,String confirm) throws UserNameException,PasswordException;
	
	/**
	 * �޸�����
	 * @param userId
	 * @param lastPassword
	 * @param password
	 * @param confirm
	 * @throws UserNotFoundException
	 * @throws PasswordException
	 */
	public void changePassword(String userId, String lastPassword, String password, String confirm) throws UserNotFoundException,PasswordException;
	
}
