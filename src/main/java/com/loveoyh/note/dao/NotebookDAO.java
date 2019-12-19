package com.loveoyh.note.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loveoyh.note.entity.Note;
import com.loveoyh.note.entity.Notebook;

public interface NotebookDAO {

	public List<Map<String, Object>> findNotebooksByUserId(@Param("userId") String userId,@Param("type") String type);
	
	public Notebook findNotebookById(String notebookId);

	public int countNotebookById(String notebookId);
	
	public List<Map<String, Object>> findNotebooksByPage(@Param("userId")String userId,@Param("start")int start,@Param("pageSize")int pageSize,@Param("table")String table);
	
	public int addNotebook(Notebook notebook);
	
	public int updateNotebook(Notebook notebook);
	
	public int removeNotebook(String notebookId);
}
