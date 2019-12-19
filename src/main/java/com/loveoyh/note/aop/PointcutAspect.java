package com.loveoyh.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointcutAspect {
	
//	@Before("bean(*Service)")
//	@Before("within(com.loveoyh.note.service.*ServiceImpl)")
//	@Before("execution(* com.loveoyh.note.service.UserService.login(..))")
	public void test(){
		System.out.println("«–»Îµ„≤‚ ‘");
	}
	
}
