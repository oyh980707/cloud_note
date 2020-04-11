package com.loveoyh.note.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loveoyh.note.entity.Activity;
import com.loveoyh.note.entity.ActivityNote;

public interface ActivityDAO {
	
	int add(Activity activity);
	
	int addNoteToActivity(ActivityNote activityNote);

	List<Activity> listActivities();

	Activity findActivityById(String activityId);

	List<ActivityNote> findActivityNotesByActivityId(String activityId);

	ActivityNote findActivityNoteByIdAndNoteId(@Param("activityId") String activityId, @Param("noteId") String noteId);

	int updateActivityNote(ActivityNote note);
	
}
