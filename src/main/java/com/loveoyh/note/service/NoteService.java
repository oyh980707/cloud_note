package com.loveoyh.note.service;

import java.util.List;
import java.util.Map;

import com.loveoyh.note.entity.Note;
import com.loveoyh.note.service.ex.NoteNotFoundException;
import com.loveoyh.note.service.ex.NotebookNotFoundException;
import com.loveoyh.note.service.ex.UserNotFoundException;

public interface NoteService {
	/**
	 * ʵ��ͨ���ʼǱ�id���Ҷ�Ӧ���бʼǵ��б�
	 * @param notebookId
	 * @return
	 */
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException;
	/**
	 * ʵ��ͨ���ʼǱ�id���Ҷ�Ӧ���бʼǵ��б�,ʵ�ַ�ҳЧ��
	 * @param notebookId
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws NotebookNotFoundException
	 */
	public List<Map<String, Object>> listNotes(String notebookId,Integer page) throws NotebookNotFoundException;
	/**
	 * ���ݱʼ�id����ȫ����Ϣ
	 * @param noteId
	 * @return
	 */
	public Note getNote(String noteId) throws NoteNotFoundException;
	/**
	 * ��ӱʼ�
	 * @return
	 */
	public Note add(String userId,String notebookId,String title) throws UserNotFoundException,NotebookNotFoundException;
	/**
	 * ʵ�ֱʼǸ��¹���
	 * @param noteId
	 * @param title
	 * @param body
	 * @return
	 * @throws NotebookNotFoundException
	 */
	public boolean updateNote(String noteId,String title,String body) throws NoteNotFoundException;
	/**
	 * ʵ�ֱʼ��ƶ�����
	 * @param noteId
	 * @param notebookId
	 * @return
	 * @throws NotebookNotFoundException
	 * @throws NoteNotFoundException
	 */
	public boolean move(String noteId,String notebookId) throws NotebookNotFoundException,NoteNotFoundException;
	/**
	 * ʵ�ֱʼ�ɾ������
	 * @param noteId
	 * @return
	 * @throws NoteNotFoundException
	 */
	public boolean delete(String noteId) throws NoteNotFoundException;
	/**
	 * ʵ�ֻ���վ�����бʼ��б��ѯ
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public List<Map<String, Object>> listTrashBin(String userId) throws UserNotFoundException;
	/**
	 * ʵ�ֻ���վ�����ʼ�
	 * @param noteId
	 * @param notebookId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws NotebookNotFoundException
	 */
	public boolean replayNote(String noteId,String notebookId) throws NoteNotFoundException,NotebookNotFoundException;
	/**
	 * ʵ��ɾ���ʼǹ���
	 * @param noteId
	 * @return
	 * @throws NoteNotFoundException
	 */
	public boolean removeNote(String noteId) throws NoteNotFoundException;
	
	/**
	 * ����ɾ���ʼǹ���
	 * @param noteIds
	 * @return
	 * @throws NoteNotFoundException
	 */
	public int deleteNotes(String... noteIds) throws NoteNotFoundException;
	
	/**
	 * �û���������
	 * @param userId
	 * @param stars
	 * @return
	 * @throws UserNotFoundException
	 */
	public boolean addStar(String userId, int stars) throws UserNotFoundException;
}
