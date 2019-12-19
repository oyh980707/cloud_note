package com.loveoyh.note.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.dao.NotebookDAO;
import com.loveoyh.note.entity.Note;
import com.loveoyh.note.entity.Notebook;

public class NotebookDaoTest extends BaseTest{
	NotebookDAO dao;
	
	@Before
	public void initDao(){
		dao = ctx.getBean("notebookDAO",NotebookDAO.class);
	}
	
	@Test
	public void testFindNotebooksByUserId(){
		String userId="52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		List<Map<String, Object>> list = dao.findNotebooksByUserId(userId,null);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testFindNotebookById(){
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		Notebook notebook = dao.findNotebookById(notebookId);
		System.out.println(notebook);
	}
	
	@Test
	public void testRemoveNotebook(){
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		int rows = dao.removeNotebook(notebookId);
		System.out.println(rows);
	}
	
	@Test
	public void testUpdateNotebook(){
		Notebook notebook = new Notebook();
		notebook.setId("01e24d89-15ab-4b6a-bf6f-2e5ad10b2041");
		notebook.setNotebookTypeId("1");
		int rows = dao.updateNotebook(notebook);
		System.out.println(rows);
	}
	
	@Test
	public void testFindNotebooksByPage(){
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		int pageSize = 4;
		int page = 0;
		int start = page * pageSize;
		String table = "cn_notebook";
		List<Map<String, Object>> list = dao.findNotebooksByPage(userId,start, pageSize, table);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testAddNotebook(){
		String id = UUID.randomUUID().toString();
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		String notebookTypeId = "5";
		String name = "addNewNotebook";
		String notebookDesc = null;
		Date time = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createtime = formatter.format(time);
		Notebook notebook = new Notebook(id, userId, notebookTypeId, name, notebookDesc, createtime);
		int n = dao.addNotebook(notebook);
		System.out.println(n);
	}
	
//	@Test
//	public void a(){
//		Date time = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String createtime = formatter.format(time);
//		System.out.println(createtime);
//	}
	
}
