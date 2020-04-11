package com.loveoyh.note.entity;

import java.io.Serializable;
/**
 * 活动笔记实体类
 * @author oyh
 *
 */
public class ActivityNote implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String activityId;
	private String noteId;
	private Long up;
	private Long down;
	private String title;
	private String body;
	public ActivityNote() {
	}
	public ActivityNote(String id, String activityId, String noteId, Long up, Long down, String title, String body) {
		this.id = id;
		this.activityId = activityId;
		this.noteId = noteId;
		this.up = up;
		this.down = down;
		this.title = title;
		this.body = body;
	}
	@Override
	public String toString() {
		return "ActivityNote [id=" + id + ", activityId=" + activityId + ", noteId=" + noteId + ", up=" + up + ", down="
				+ down + ", title=" + title + ", body=" + body + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public Long getUp() {
		return up;
	}
	public void setUp(Long up) {
		this.up = up;
	}
	public Long getDown() {
		return down;
	}
	public void setDown(Long down) {
		this.down = down;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
