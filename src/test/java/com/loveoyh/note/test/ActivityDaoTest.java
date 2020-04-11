package com.loveoyh.note.test;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.ActivityDAO;
import com.loveoyh.note.dao.NoteDAO;
import com.loveoyh.note.entity.Activity;
import com.loveoyh.note.entity.ActivityNote;
import com.loveoyh.note.entity.Note;

public class ActivityDaoTest extends BaseTest{
	private ActivityDAO dao;
	
	@Before
	public void initDao(){
		dao = ctx.getBean("activityDAO",ActivityDAO.class);
	}
	
	@Test
	public void testAdd(){
		String id = UUID.randomUUID().toString();
		/** 这里表示的时间为2020-6-12 */
		Date d1 = new Date(2020-1900, 6-1, 12);
		Long time = d1.getTime();
		Activity activity = new Activity(id, "1", "武理笔记", "数据结构", time);
		int n = dao.add(activity);
		System.out.println(n);
	}
	
	
	@Test
	public void testListActivities(){
		List<Activity> list = dao.listActivities();
		for(Activity a : list) {
			System.out.println(a);
		}
	}
	
	@Test
	public void testFindActivityById(){
		String id = "0f4ce06f-7dd4-4ef6-bebb-8c6b978d3c22";
		Activity one = dao.findActivityById(id);
		System.out.println(one);
	}
	
	@Test
	public void testFindActivityNotesByActivityId(){
		String id = "bbbbc20e-b8cd-4aee-8ead-3fef8c5bca1e";
		List<ActivityNote> list = dao.findActivityNotesByActivityId(id);
		for(ActivityNote note : list) {
			System.out.println(note);
		}
	}
	
	@Test
	public void testAddNoteToActivity(){
		String id = UUID.randomUUID().toString();
		String activityId = "bbbbc20e-b8cd-4aee-8ead-3fef8c5bca1e";
		String noteId = "a0aeece7-ad79-4a57-90d2-d78966182a8b";
		ActivityNote activityNote = new ActivityNote(id, activityId, noteId, 0l, 0l, "oyh", "oyh");
		int n = dao.addNoteToActivity(activityNote);
		System.out.println(n);
	}
	
	@Test
	public void testFindActivityNoteByIdAndNoteId() {
		String activityId = "bbbbc20e-b8cd-4aee-8ead-3fef8c5bca1e";
		String noteId = "a0aeece7-ad79-4a57-90d2-d78966182a8b";
		ActivityNote activityNote = dao.findActivityNoteByIdAndNoteId(activityId, noteId);
		System.out.println(activityNote);
	}
	
	@Test
	public void testNoteUp() {
		String id = "71d5ac74-585b-463f-96ab-7e2b7d085472";
		ActivityNote temp = new ActivityNote();
		temp.setId(id);
		temp.setUp(0l);
		int n = dao.updateActivityNote(temp);
		System.out.println(n);
		
	}
	
}
