package com.loveoyh.note.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loveoyh.note.entity.Note;
import com.loveoyh.note.entity.Notebook;
import com.sun.org.glassfish.gmbal.ParameterNames;

public interface NoteDAO {
	
	public List<Map<String, Object>> findNotesByNotebookId(String notebookId);
	
	public Note findNoteById(String noteId);
	
	public int addNote(Note note);
	
	public int updateNote(Note note);

	public int countNoteById(String noteId);
	
	public List<Map<String, Object>> findDeleteNotesByUserId(String userId);
	
	public int removeNote(String noteId);
	
	public int deleteNoteById(String noteId);
	
	public int deleteNotes(@Param("ids")String... ids);
	
	public List<Map<String, Object>> findNotes(@Param("userId")String userId,@Param("notebookId")String notebookId,@Param("noteType")String noteType);
	
	public List<Map<String, Object>> findNotesByPage(@Param("notebookId")String notebookId,@Param("start")int start,@Param("pageSize")int pageSize,@Param("table")String table);
	
	public Notebook addNotebook(Notebook notebook);
}
