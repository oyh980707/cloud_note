package com.loveoyh.note.test;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.StarsDAO;
import com.loveoyh.note.entity.Stars;

public class StarsDaoTest extends BaseTest{
	private StarsDAO starsDAO;
	
	@Before
	public void init(){
		starsDAO = ctx.getBean("starsDAO",StarsDAO.class);
	}
	
	@Test
	public void testFindSatrsByUserId(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		Stars stars = starsDAO.findSatrsByUserId(userId);
		System.out.println(stars);
	}
	
	@Test
	public void tesItnsertStars(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		Stars stars = new Stars();
		stars.setId(UUID.randomUUID().toString());
		stars.setStars(0);
		stars.setUserId(userId);
		int n = starsDAO.insertStars(stars);
		System.out.println(n);
	}
	
	@Test
	public void testUpdateStars(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		String id = "";
		Stars stars = new Stars();
		stars.setId(id);
		stars.setStars(10);
		stars.setUserId(userId);
		int n = starsDAO.updateStars(stars);
		System.out.println(n);
	}
}






















