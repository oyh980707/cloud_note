package com.loveoyh.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.entity.Note;
import com.loveoyh.note.service.NoteService;

public class NoteServiceTest extends BaseTest {
	private NoteService service;
	
	@Before
	public void initService(){
		service = ctx.getBean("noteService",NoteService.class);
	}
	
	@Test
	public void testNoteService(){
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		List<Map<String, Object>> list = service.listNotes(notebookId);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testGetNote(){
		String noteId = "09f60aeb-a573-4fcf-b39f-903e1536e762";
		Note note = service.getNote(noteId);
		System.out.println(note);
	}
	
	@Test
	public void testAdd(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		String title = "oyhr";
		Note note = service.add(userId, notebookId, title);
		System.out.println(note);
	}
	
	@Test
	public void testUpdateNote(){
		String noteId = "ee95f783-275a-406e-8e70-754704345dba";
		boolean success = service.updateNote(noteId, null, "@#$@#$@#@%#$%$%^$%$#$");
		System.out.println(success);
	}
	
	@Test
	public void testMove(){
		String noteId = "ee95f783-275a-406e-8e70-754704345dba";
		String notebookId = "1fdb7adb-5da0-41f7-8f4e-5a512dbed9fc";
		boolean success = service.move(noteId, notebookId);
		System.out.println(success);
	}
	
	@Test
	public void testDelete(){
		String noteId = "051538a6-0f8e-472c-8765-251a795bc88f";
		boolean success = service.delete(noteId);
		System.out.println(success);
	}
	
	@Test
	public void testListTrashBin(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		List<Map<String, Object>> list = service.listTrashBin(userId);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testReplayNote(){
		String noteId = "0e366f39-cf5b-4989-b07c-66ae72f0b2d2";
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		boolean success = service.replayNote(noteId, notebookId);
		System.out.println(success);
	}
	
	@Test
	public void testRemoveNote(){
		String noteId = "a9ebb56a-ee20-4fea-bc76-1c75317843b7";
		boolean success = service.removeNote(noteId);
		System.out.println(success);
	}
	
	@Test
	public void testDeleteNotes(){
		String id1 = "07305c91-d9fa-420d-af09-c3ff209608ff";
		String id2 = "09f60aeb-a573-4fcf-b39f-903e1536e762";
		String id3 = "0a652205-c8af-41e0-986a-80d0cdecc996";
		String id4 = "0ed5aed5-baf0-4f00-9dbf-8da105b16bb2";
		int n = service.deleteNotes(id1,id2,id3,id4);
		System.out.println(n);
	}
	
	@Test
	public void testAddStar(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		int stars = 5;
		boolean success = service.addStar(userId, stars);
		System.out.println(success);
	}
	
	@Test
	public void testListNotes(){
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		int page = 0;
		List<Map<String, Object>> list = service.listNotes(notebookId, page);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
}














