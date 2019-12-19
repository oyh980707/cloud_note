package com.loveoyh.note.test;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.PostDAO;
import com.loveoyh.note.entity.Post;

public class PostDaoTest extends BaseTest{
	
	private PostDAO dao;
	
	@Before
	public void init(){
		dao = ctx.getBean("postDAO",PostDAO.class);
	}
	
	@Test
	public void testFindPostById(){
		Integer id = 1;
		Post post = dao.findPostById(id);
		System.out.println(post);
	}
}
