package com.loveoyh.note.entity;

import java.io.Serializable;

/**
 * 笔记实体类
 * @author HP
 *
 */
public class Note implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String notebookId;
	private String userId;
	private String noteStatusId;
	private String noteTypeId;
	private String title;
	private String body;
	private Long createtime;
	private Long lastModifyTime;
	
	public Note() {
	}

	public Note(String id, String notebookId, String userId, String noteStatusId, String noteTypeId, String title,
			String body, Long createtime, Long lastModifyTime) {
		super();
		this.id = id;
		this.notebookId = notebookId;
		this.userId = userId;
		this.noteStatusId = noteStatusId;
		this.noteTypeId = noteTypeId;
		this.title = title;
		this.body = body;
		this.createtime = createtime;
		this.lastModifyTime = lastModifyTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", notebookId=" + notebookId + ", userId=" + userId + ", noteStatusId=" + noteStatusId
				+ ", noteTypeId=" + noteTypeId + ", title=" + title + ", body=" + body + ", createtime=" + createtime
				+ ", lastModifyTime=" + lastModifyTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotebookId() {
		return notebookId;
	}

	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNoteStatusId() {
		return noteStatusId;
	}

	public void setNoteStatusId(String noteStatusId) {
		this.noteStatusId = noteStatusId;
	}

	public String getNoteTypeId() {
		return noteTypeId;
	}

	public void setNoteTypeId(String noteTypeId) {
		this.noteTypeId = noteTypeId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	public Long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
