package com.loveoyh.note.test;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.PersonDAO;
import com.loveoyh.note.entity.Person;

public class PersonDaoTest extends BaseTest {
	
	private PersonDAO dao;
	
	@Before
	public void init(){
		dao = ctx.getBean("personDAO",PersonDAO.class);
	}
	
	@Test
	public void testAddPerson(){
		String name = "уехЩ";
		Person person = new Person(null,name);
		System.out.println(person);
		int n = dao.addPerson(person);
		System.out.println(n);
		System.out.println(person);
	}
	
}
