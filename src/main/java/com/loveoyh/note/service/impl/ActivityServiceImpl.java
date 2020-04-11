package com.loveoyh.note.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loveoyh.note.dao.ActivityDAO;
import com.loveoyh.note.dao.NoteDAO;
import com.loveoyh.note.dao.UserDAO;
import com.loveoyh.note.entity.Activity;
import com.loveoyh.note.entity.ActivityNote;
import com.loveoyh.note.entity.Note;
import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.ActivityService;
import com.loveoyh.note.service.ex.ActivityNotsFountException;
import com.loveoyh.note.service.ex.NoteNotFoundException;
import com.loveoyh.note.service.ex.UserNameException;
import com.loveoyh.note.service.ex.UserNotFoundException;

@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Resource
	private ActivityDAO activityDAO;
	
	@Resource
	private UserDAO userDAO;
	
	@Resource
	private NoteDAO noteDAO;

	public List<Activity> listActivities() {
		List<Activity> activities = activityDAO.listActivities();
		return activities;
	}

	public List<ActivityNote> findActivityNotesByActivityId(String activityId) throws ActivityNotsFountException {
		if(activityId == null || activityId.trim().isEmpty()){
			throw new ActivityNotsFountException("活动未找到!");
		}
		Activity activity = activityDAO.findActivityById(activityId);
		if(activity==null){
			throw new ActivityNotsFountException("活动未找到!");
		}
		
		List<ActivityNote> notes = activityDAO.findActivityNotesByActivityId(activityId);
		return notes;
	}
	
	public ActivityNote noteUp(String activityId, String noteId) throws NoteNotFoundException {
		ActivityNote note = activityDAO.findActivityNoteByIdAndNoteId(activityId,noteId);
		if(note==null) {
			throw new NoteNotFoundException("未找到笔记!");
		}
		//更新笔记
		ActivityNote temp = new ActivityNote();
		temp.setUp(note.getUp()+1);
		temp.setId(note.getId());
		int n = activityDAO.updateActivityNote(temp);
		if(n!=1) {
			throw new RuntimeException("赞失败!");
		}
		note.setUp(temp.getUp());
		return note;
	}

	public ActivityNote noteDown(String activityId, String noteId) throws NoteNotFoundException {
		ActivityNote note = activityDAO.findActivityNoteByIdAndNoteId(activityId,noteId);
		if(note==null) {
			throw new NoteNotFoundException("未找到笔记!");
		}
		//更新笔记
		ActivityNote temp = new ActivityNote();
		temp.setDown(note.getDown()+1);
		temp.setId(note.getId());
		int n = activityDAO.updateActivityNote(temp);
		if(n!=1) {
			throw new RuntimeException("赞失败!");
		}
		note.setDown(temp.getDown());
		return note;
	}

	public void collect(String userId, String activityId, String noteId)
			throws UserNotFoundException, ActivityNotsFountException {
		/* 查询用户 */
		User user = userDAO.findUserById(userId);
		
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		
		if(activityId == null || activityId.trim().isEmpty()){
			throw new ActivityNotsFountException("活动未找到!");
		}
		Activity activity = activityDAO.findActivityById(activityId);
		if(activity==null){
			throw new ActivityNotsFountException("活动未找到!");
		}
		
		ActivityNote activityNote = activityDAO.findActivityNoteByIdAndNoteId(activityId,noteId);
		//根据noteid差notebookid
		String notebookId = noteDAO.findNoteById(noteId).getNotebookId();
		
		Note note = new Note();
		long time = System.currentTimeMillis();
		note.setId(UUID.randomUUID().toString());
		note.setNotebookId(notebookId);
		note.setNoteStatusId("1");
		note.setNoteTypeId("1");
		note.setUserId(userId);
		note.setTitle(activityNote.getTitle());
		note.setBody(activityNote.getBody());
		note.setCreatetime(time);
		note.setLastModifyTime(time);
		
		int n = noteDAO.addNote(note);
		if(n!=1) {
			throw new RuntimeException("收藏失败!");
		}
	}

}
