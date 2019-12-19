package com.loveoyh.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.loveoyh.note.service.ex.UserNotFoundException;

/**
 * ����һ���������,����һ����ͨ��JavaBean
 * @author HP
 *
 */
@Component
@Aspect
public class DemoAspect {
	
	//����test��������userService��ȫ������֮ǰ����
//	@Before("bean(userService)")
	public void test(){
		System.out.println("Before");
	}
	
	//����test2��������userService��ȫ������֮������
//	@After("bean(userService)")
	public void test2(){
		System.out.println("After");
	}
	
	//����test2��������userService��ȫ������֮������
//	@AfterReturning("bean(userService)")
	public void test3(){
		System.out.println("Returning");
	}

	//����test2��������userService��ȫ������֮������
//	@AfterThrowing("bean(userService)")
	public void test4(){
		System.out.println("Throwing");
	}
	
	/**
	 * ����֪ͨ
	 * 1. �����з���ֵ
	 * 2. �����в���
	 * 3. �����׳��쳣
	 * 4. ��Ҫ�ڷ����е��� jp.proceed()
	 * 5. ����ҵ�񷽷��ķ���ֵ
	 * @param jp
	 * @return
	 */
//	@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint jp) throws Throwable{
		Object val = jp.proceed();
		System.out.println("ҵ����:"+val);
		throw new UserNotFoundException("���ǲ������¼");
	}
	
}
