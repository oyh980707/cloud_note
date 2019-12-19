package com.loveoyh.note.entity;

import java.io.Serializable;

/**
 * 笔记本实体类
 * @author HP
 *
 */
public class Notebook implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userId;
	private String notebookTypeId;
	private String name;
	private String notebookDesc;
	private String createtime;
	
	public Notebook() {
	}

	public Notebook(String id, String userId, String notebookTypeId, String name, String notebookDesc,
			String createtime) {
		super();
		this.id = id;
		this.userId = userId;
		this.notebookTypeId = notebookTypeId;
		this.name = name;
		this.notebookDesc = notebookDesc;
		this.createtime = createtime;
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
		Notebook other = (Notebook) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notebook [id=" + id + ", userId=" + userId + ", notebookTypeId=" + notebookTypeId + ", name=" + name
				+ ", notebookDesc=" + notebookDesc + ", createtime=" + createtime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNotebookTypeId() {
		return notebookTypeId;
	}

	public void setNotebookTypeId(String notebookTypeId) {
		this.notebookTypeId = notebookTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotebookDesc() {
		return notebookDesc;
	}

	public void setNotebookDesc(String notebookDesc) {
		this.notebookDesc = notebookDesc;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
}
