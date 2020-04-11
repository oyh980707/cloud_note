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
	 * 获取所有的活动
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public Object getAll() {
		List<Activity> activitys = activityService.listActivities();
		return new JsonResult(activitys);
	}
	
	/**
	 * 获取所有的活动
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
	 * 赞笔记
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
	 * 踩笔记
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
	 * 收藏笔记
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
			throw new RuntimeException("操作用户与登录用户不匹配!");
		}
		activityService.collect(userId,activityId,noteId);
		return new JsonResult();
	}
	
	
	/**
	 * 处理活动未找到异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ActivityNotsFountException.class)
	@ResponseBody
	public Object handleActivityNotsFount(ActivityNotsFountException e) {
		return new JsonResult(1,e);
	}
}
