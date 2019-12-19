package com.loveoyh.note.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTest {

	protected ClassPathXmlApplicationContext ctx;

	@Before
	public void initCtx() {
		ctx = new ClassPathXmlApplicationContext(
				"conf/spring-mvc.xml",
				"conf/spring-mybatis.xml",
				"conf/spring-service.xml");
	}

	@After
	public void destroy() {
		ctx.close();
	}

}