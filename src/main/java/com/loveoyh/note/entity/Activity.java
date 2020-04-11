package com.loveoyh.note.entity;

import java.io.Serializable;
/**
 * 活动实体类
 * @author oyh
 *
 */
public class Activity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String activityStatusId;
	private String title;
	private String body;
	private Long deadline;
	
	public Activity() {
	}
	public Activity(String id, String activityStatusIdj, String title, String body, Long deadline) {
		this.id = id;
		this.activityStatusId = activityStatusIdj;
		this.title = title;
		this.body = body;
		this.deadline = deadline;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", activityStatusId=" + activityStatusId + ", title=" + title + ", body=" + body
				+ ", deadline=" + deadline + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityStatusIdj() {
		return activityStatusId;
	}
	public void setActivityStatusIdj(String activityStatusIdj) {
		this.activityStatusId = activityStatusIdj;
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
	public Long getDeadline() {
		return deadline;
	}
	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}
}
