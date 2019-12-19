package com.loveoyh.note.test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.NoteDAO;
import com.loveoyh.note.entity.Note;
import com.sun.jmx.snmp.Timestamp;

public class NoteDaoTest extends BaseTest {
	
	private NoteDAO dao;
	
	@Before
	public void initDao(){
		dao = ctx.getBean("noteDAO",NoteDAO.class);
	}
	
	@Test
	public void testFindNotesByNotebookId(){
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		List<Map<String, Object>> list = dao.findNotesByNotebookId(notebookId);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testFindNoteById(){
		String noteId = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		Note note = dao.findNoteById(noteId);
		System.out.println(note);
	}
	
	@Test
	public void testAddNote(){
		String id = UUID.randomUUID().toString();
		String notebookId = UUID.randomUUID().toString();
		String userId = UUID.randomUUID().toString();
		
		Note note = new Note(id,notebookId,userId,"1",null,"oyhµÄ±Ê¼Ç","ÒúÏ¼°®Äã",System.currentTimeMillis(),System.currentTimeMillis());
		int n = dao.addNote(note);
		System.out.println(n);
	}
	
	@Test
	public void testUpdateNote(){
		String id = "ee95f783-275a-406e-8e70-754704345dba";
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		Note note = new Note(id,notebookId,userId,"1",null,"oyhµÄ±Ê¼Ç","ÒúÏ¼³¬¼¶³¬¼¶°®Äã",null,System.currentTimeMillis());
		int n = dao.updateNote(note);
		System.out.println(n);
	}
	
	@Test
	public void testCountNoteById(){
		String noteId = "ee95f783-275a-406e-8e70-754704345dba";
		int n = dao.countNoteById(noteId);
		System.out.println(n);
	}
	
	@Test
	public void testFindDeleteNotesByUserId(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		List<Map<String, Object>> list = dao.findDeleteNotesByUserId(userId);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testRemoveNote(){
		String noteId = "126bd020-7285-4c8a-9f2f-cc87d9f58c95";
		int n = dao.removeNote(noteId);
		System.out.println(n);
	}
	
	@Test
	public void testDeleteNoteById(){
		String noteId = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		int n = dao.deleteNoteById(noteId);
		System.out.println(n);
	}
	
	@Test
	public void testFindNotes(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		String statusId = "1";
		List<Map<String, Object>> list = dao.findNotes(null, notebookId, statusId);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testDeleteNotes(){
		String noteId1 = "bd6db9bb-7667-4b6e-aa81-8460f874fa21";
		String noteId2 = "c035fb3e-09d1-47f7-b602-e7721188154b";
		String noteId3 = "c1eb311b-fb7f-4d8c-b6a3-472da22021b5";
		int n = dao.deleteNotes(noteId1,noteId2,noteId3);
		System.out.println(n);
	}
	
	@Test
	public void testFindNotesByPage(){
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		int start = 0;
		int pageSize = 4;
		String table = "cn_note";
		List<Map<String, Object>> list = dao.findNotesByPage(notebookId, start, pageSize, table);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
}
