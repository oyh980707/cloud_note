package com.loveoyh.note.entity;

import java.io.Serializable;

public class Stars implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userId;
	private Integer stars;
	public Stars() {
		super();
	}
	public Stars(String id, String userId, Integer stars) {
		super();
		this.id = id;
		this.userId = userId;
		this.stars = stars;
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
		Stars other = (Stars) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Stars [id=" + id + ", userId=" + userId + ", stars=" + stars + "]";
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
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
}
