package com.loveoyh.note.test;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.PostDAO;
import com.loveoyh.note.dao.ActivityDAO;
import com.loveoyh.note.entity.Post;

public class ShareDaoTest extends BaseTest{
	private ActivityDAO dao;
	
	@Before
	public void init(){
		dao = ctx.getBean("shareDAO",ActivityDAO.class);
	}
	
//	@Test
//	public void testFindPostById(){
//		String noteId = "a0aeece7-ad79-4a57-90d2-d78966182a8b";
//		Share share = new Share();
//		share.setId(UUID.randomUUID().toString());
//		share.setNoteId(noteId);
//		share.setTitle("oyh");
//		share.setBody("oyh");
//		dao.save(share);
//	}
}
