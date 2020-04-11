package com.loveoyh.note.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loveoyh.note.entity.Activity;
import com.loveoyh.note.entity.ActivityNote;
import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.ActivityService;
import com.loveoyh.note.service.ex.ActivityNotsFountException;
import com.loveoyh.note.util.JsonResult;

@Controller
@RequestMapping("activity")
public class ActivityController extends AbstractController{
	
	@Resource
	private ActivityService activityService;
	
	/**
	 * ��ȡ���еĻ
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public Object getAll() {
		List<Activity> activitys = activityService.listActivities();
		return new JsonResult(activitys);
	}
	
	/**
	 * ��ȡ���еĻ
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/detail.do")
	@ResponseBody
	public Object getDetail(String activityId) {
		List<ActivityNote> notes = activityService.findActivityNotesByActivityId(activityId);
		return new JsonResult(notes);
	}
	
	/**
	 * �ޱʼ�
	 * @param noteId
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/noteUp.do")
	@ResponseBody
	public Object noteUp(String activityId,String noteId) {
		ActivityNote note = activityService.noteUp(activityId,noteId);
		return new JsonResult(note);
	}
	
	/**
	 * �ȱʼ�
	 * @param noteId
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/noteDown.do")
	@ResponseBody
	public Object noteDown(String activityId,String noteId) {
		ActivityNote note = activityService.noteDown(activityId,noteId);
		return new JsonResult(note);
	}
	
	/**
	 * �ղرʼ�
	 * @param userId
	 * @param noteId
	 * @param session
	 * @return
	 */
	@RequestMapping("/collect.do")
	@ResponseBody
	public Object collect(String activityId, String userId, String noteId, HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
		if(!user.getId().equals(userId)) {
			throw new RuntimeException("�����û����¼�û���ƥ��!");
		}
		activityService.collect(userId,activityId,noteId);
		return new JsonResult();
	}
	
	
	/**
	 * ����δ�ҵ��쳣
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ActivityNotsFountException.class)
	@ResponseBody
	public Object handleActivityNotsFount(ActivityNotsFountException e) {
		return new JsonResult(1,e);
	}
}
