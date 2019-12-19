package com.loveoyh.note.util;

import java.io.Serializable;

public class JsonResult implements Serializable{
	private static final long serialVersionUID = 1L;
	// 表示成功状态标志
	public static final int SUCCESS = 0;
	// 表示出错状态标志
	public static final int ERROR = 1;
	
	/**
	 * 状态
	 */
	private int state;
	/**
	 * 错误消息
	 */
	private String message;
	/**
	 * 返回正确的时候的数据
	 */
	private Object data;
	
	public JsonResult() {
	}
	public JsonResult(String message) {
		state = ERROR;
		this.message = message;
	}
	public JsonResult(int state) {
		this.state = state;
	}
	public JsonResult(Object data) {
		state = SUCCESS;
		this.data = data;
	}
	public JsonResult(Throwable e){
		this.state = ERROR;
		this.message = e.getMessage();
	}
	public JsonResult(int state,Throwable e){
		this.state = state;
		this.message = e.getMessage();
	}
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
