package com.loveoyh.note.entity;

import java.io.Serializable;

/**
 * 分享笔记实体类
 * @author oyh
 *
 */
public class Share implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String noteId;
	private String title;
	private String body;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
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
