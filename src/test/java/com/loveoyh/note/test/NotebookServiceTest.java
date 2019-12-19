package com.loveoyh.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.loveoyh.note.service.NotebookService;
import com.loveoyh.note.service.ex.ServiceException;

public class NotebookServiceTest extends BaseTest {
	private NotebookService service;
	
	@Before
	public void initService(){
		service = ctx.getBean("notebookService",NotebookService.class);
	}
	
	@Test
	public void testListNotebooks1(){
		List<Map<String, Object>> list = service.listNotebooks("52f9b276-38ee-447f-a3aa-0d54e7a736e4","5");
		for(Map<String,Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testListNotebooks2(){
		List<Map<String, Object>> list = service.listNotebooks("52f9b276-38ee-447f-a3aa-0d54e7a736e4",0);
		for(Map<String,Object> map : list){
			System.out.println(map);
		}
	}
	
	@Test
	public void testAddNotebook() {
		String userId = "52f9b276-38ee-447f-a3aa-0d54e7a736e4";
		String title = "serviceNotebook";
		service.addNotebook(userId, title);
		System.err.println("testAddNotebook is OK.");
	}
	
	@Test
	public void testDeleteNotebook() {
		try {
			String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
			service.deleteNotebook(notebookId);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	@Test
	public void testRemoveNotebook() {
		try {
			String notebookId = "0314be50-5402-4417-97eb-c6b340e97ffc";
			service.removeNotebook(notebookId);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		
	}
}
