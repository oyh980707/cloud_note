package com.loveoyh.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * �������ҵ��������ܲ���
 * @author HP
 *
 */
@Component
@Aspect
public class TimeAspect {

	@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint jp) throws Throwable{
		long t1 = System.currentTimeMillis();
		Object val = jp.proceed();//Ŀ��ҵ�񷽷�
		long t2 = System.currentTimeMillis();
		long t = t2-t1;
		
		//JoinPoint ������Ի�ȡĿ��ҵ�񷽷�����ϸ��Ϣ: ����ǩ��,���ò�����
		//Singnature: ǩ��,�����Ƿ���ǩ��
		Signature m = jp.getSignature();
		
		System.out.println(m +"��ʱ:"+t);
		return val;
	}
	
}
