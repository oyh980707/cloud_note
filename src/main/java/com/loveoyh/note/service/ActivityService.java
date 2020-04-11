package com.loveoyh.note.service;

import java.util.List;

import com.loveoyh.note.entity.Activity;
import com.loveoyh.note.entity.ActivityNote;
import com.loveoyh.note.service.ex.ActivityNotsFountException;
import com.loveoyh.note.service.ex.NoteNotFoundException;
import com.loveoyh.note.service.ex.UserNotFoundException;

public interface ActivityService {
	
	/**
	 * ��ѯ���еĻ
	 * @return
	 */
	List<Activity> listActivities();
	
	/**
	 * ͨ���id��ѯ���еıʼ�
	 * @param activityId
	 * @return
	 * @throws ActivityNotsFountException
	 */
	List<ActivityNote> findActivityNotesByActivityId(String activityId) throws ActivityNotsFountException;

	/**
	 * �ޱʼ�
	 * @param noteId
	 * @param activityId
	 * @return
	 * @throws NoteNotFoundException
	 */
	ActivityNote noteUp(String activityId, String noteId) throws NoteNotFoundException;
	
	/**
	 * �ȱʼ�
	 * @param noteId
	 * @param activityId
	 * @return
	 * @throws NoteNotFoundException
	 */
	ActivityNote noteDown(String activityId, String noteId) throws NoteNotFoundException;
	
	/**
	 * �ղرʼ�
	 * @param userId
	 * @param activityId
	 * @param noteId
	 */
	void collect(String userId, String activityId, String noteId) throws UserNotFoundException,ActivityNotsFountException;


	
}
