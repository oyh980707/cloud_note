package com.loveoyh.note.service;

import java.util.List;

import com.loveoyh.note.entity.Activity;
import com.loveoyh.note.entity.ActivityNote;
import com.loveoyh.note.service.ex.ActivityNotsFountException;
import com.loveoyh.note.service.ex.NoteNotFoundException;
import com.loveoyh.note.service.ex.UserNotFoundException;

public interface ActivityService {
	
	/**
	 * 查询所有的活动
	 * @return
	 */
	List<Activity> listActivities();
	
	/**
	 * 通过活动id查询所有的笔记
	 * @param activityId
	 * @return
	 * @throws ActivityNotsFountException
	 */
	List<ActivityNote> findActivityNotesByActivityId(String activityId) throws ActivityNotsFountException;

	/**
	 * 赞笔记
	 * @param noteId
	 * @param activityId
	 * @return
	 * @throws NoteNotFoundException
	 */
	ActivityNote noteUp(String activityId, String noteId) throws NoteNotFoundException;
	
	/**
	 * 踩笔记
	 * @param noteId
	 * @param activityId
	 * @return
	 * @throws NoteNotFoundException
	 */
	ActivityNote noteDown(String activityId, String noteId) throws NoteNotFoundException;
	
	/**
	 * 收藏笔记
	 * @param userId
	 * @param activityId
	 * @param noteId
	 */
	void collect(String userId, String activityId, String noteId) throws UserNotFoundException,ActivityNotsFountException;


	
}
