package com.loveoyh.note.service;

import java.util.List;
import java.util.Map;

import com.loveoyh.note.entity.Notebook;
import com.loveoyh.note.service.ex.NotebookNotFoundException;
import com.loveoyh.note.service.ex.UserNotFoundException;

public interface NotebookService {
	
	public List<Map<String,Object>> listNotebooks(String userId,String type) throws UserNotFoundException;
	
	public List<Map<String,Object>> listNotebooks(String userId,Integer page) throws UserNotFoundException;
	
	public Notebook addNotebook(String userId, String name) throws UserNotFoundException,NotebookNotFoundException;
	
	public void deleteNotebook(String notebookId) throws NotebookNotFoundException;
	
	public void removeNotebook(String notebookId) throws NotebookNotFoundException;
	
	public void replayNotebook(String notebookId) throws NotebookNotFoundException;
}
