package com.loveoyh.note.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loveoyh.note.entity.Notebook;
import com.loveoyh.note.service.NotebookService;
import com.loveoyh.note.util.JsonResult;

@RequestMapping("/notebook")
@Controller
public class NotebookController extends AbstractController {
	
	@Resource
	private NotebookService notebookService;
	
	/**
	 * 通过cookie ajax 传过来的用户id 查找笔记本列表
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String userId,String type){
		List<Map<String, Object>> list = notebookService.listNotebooks(userId,type);
		return new JsonResult(list);
	}
	
	/**
	 * 通过id和页数 查找笔记本
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping("/page.do")
	@ResponseBody
	public JsonResult page(String userId,Integer page){
		List<Map<String, Object>> list = notebookService.listNotebooks(userId,page);
		return new JsonResult(list);
	}
	
	/**
	 * 通过cookie ajax 传过来的用户id 查找笔记本来添加笔记
	 * @param userId 用户id
	 * @param title 笔记本标题
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult add(String userId,String name){
		Notebook notebook = notebookService.addNotebook(userId, name);
		return new JsonResult(notebook);
	}
	
	/**
	 * 删除笔记本到回收站
	 * @param notebookId
	 * @return
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String notebookId){
		notebookService.deleteNotebook(notebookId);
		return new JsonResult(SUCCESS);
	}
	
	/**
	 * 彻底删除笔记本
	 * @param notebookId
	 * @return
	 */
	@RequestMapping("/remove.do")
	@ResponseBody
	public JsonResult remove(String notebookId){
		notebookService.removeNotebook(notebookId);
		return new JsonResult(SUCCESS);
	}
	
	/**
	 * 撤销笔记本
	 * @param userId
	 * @return
	 */
	@RequestMapping("/replay.do")
	@ResponseBody
	public Object replayNote(String notebookId){
		notebookService.replayNotebook(notebookId);
		return new JsonResult(SUCCESS);
	}
}











