package com.loveoyh.note.service;

import java.util.List;
import java.util.Map;

import com.loveoyh.note.entity.Note;
import com.loveoyh.note.service.ex.NoteNotFoundException;
import com.loveoyh.note.service.ex.NotebookNotFoundException;
import com.loveoyh.note.service.ex.UserNotFoundException;

public interface NoteService {
	/**
	 * 实现通过笔记本id查找对应所有笔记的列表
	 * @param notebookId
	 * @return
	 */
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException;
	/**
	 * 实现通过笔记本id查找对应所有笔记的列表,实现分页效果
	 * @param notebookId
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws NotebookNotFoundException
	 */
	public List<Map<String, Object>> listNotes(String notebookId,Integer page) throws NotebookNotFoundException;
	/**
	 * 根据笔记id查找全部信息
	 * @param noteId
	 * @return
	 */
	public Note getNote(String noteId) throws NoteNotFoundException;
	/**
	 * 添加笔记
	 * @return
	 */
	public Note add(String userId,String notebookId,String title) throws UserNotFoundException,NotebookNotFoundException;
	/**
	 * 实现笔记更新功能
	 * @param noteId
	 * @param title
	 * @param body
	 * @return
	 * @throws NotebookNotFoundException
	 */
	public boolean updateNote(String noteId,String title,String body) throws NoteNotFoundException;
	/**
	 * 实现笔记移动功能
	 * @param noteId
	 * @param notebookId
	 * @return
	 * @throws NotebookNotFoundException
	 * @throws NoteNotFoundException
	 */
	public boolean move(String noteId,String notebookId) throws NotebookNotFoundException,NoteNotFoundException;
	/**
	 * 实现笔记删除功能
	 * @param noteId
	 * @return
	 * @throws NoteNotFoundException
	 */
	public boolean delete(String noteId) throws NoteNotFoundException;
	/**
	 * 实现回收站的所有笔记列表查询
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public List<Map<String, Object>> listTrashBin(String userId) throws UserNotFoundException;
	/**
	 * 实现回收站撤销笔记
	 * @param noteId
	 * @param notebookId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws NotebookNotFoundException
	 */
	public boolean replayNote(String noteId,String notebookId) throws NoteNotFoundException,NotebookNotFoundException;
	/**
	 * 实现删除笔记功能
	 * @param noteId
	 * @return
	 * @throws NoteNotFoundException
	 */
	public boolean removeNote(String noteId) throws NoteNotFoundException;
	
	/**
	 * 批量删除笔记功能
	 * @param noteIds
	 * @return
	 * @throws NoteNotFoundException
	 */
	public int deleteNotes(String... noteIds) throws NoteNotFoundException;
	
	/**
	 * 用户增加星星
	 * @param userId
	 * @param stars
	 * @return
	 * @throws UserNotFoundException
	 */
	public boolean addStar(String userId, int stars) throws UserNotFoundException;
}
