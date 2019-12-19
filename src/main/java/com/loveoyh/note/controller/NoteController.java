package com.loveoyh.note.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loveoyh.note.entity.Note;
import com.loveoyh.note.service.NoteService;
import com.loveoyh.note.util.JsonResult;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController{
	@Resource
	private NoteService noteService;
	/**
	 * ͨ���ʼǱ���ѯ�ʼǿ�����
	 * @param notebookId
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public Object list(String notebookId){
		List<Map<String, Object>> list = noteService.listNotes(notebookId);
		return new JsonResult(list);
	}
	/**
	 * ���رʼ����ݿ�����
	 * @param noteId
	 * @return
	 */
	@RequestMapping("/load.do")
	@ResponseBody
	public Object load(String noteId){
		Note note = noteService.getNote(noteId);
		return new JsonResult(note);
	}
	/**
	 * ���ӱʼǿ�����
	 * @param userId
	 * @param notebookId
	 * @param title
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public Object add(String userId,String notebookId,String title){
		Note note = noteService.add(userId, notebookId, title);
		return new JsonResult(note);
	}
	/**
	 * ���±ʼǿ�����
	 * @param noteId
	 * @param title
	 * @param body
	 * @return
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	public Object updateNote(String noteId,String title,String body){
		boolean success = noteService.updateNote(noteId, title, body);
		return new JsonResult(success);
	}
	/**
	 * �ƶ��ʼ�
	 * @param noteId
	 * @param notebookId
	 * @return
	 */
	@RequestMapping("/move.do")
	@ResponseBody
	public Object move(String noteId,String notebookId){
		boolean success = noteService.move(noteId, notebookId);
		return new JsonResult(success);
	}
	
	/**
	 * ɾ���ʼ�
	 * @param noteId
	 * @return
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(String noteId){
		boolean success = noteService.delete(noteId);
		return new JsonResult(success);
	}
	
	/**
	 * ����վ�б��ʼ�
	 * @param userId
	 * @return
	 */
	@RequestMapping("/trash.do")
	@ResponseBody
	public Object listTrashBin(String userId){
		List<Map<String, Object>> list = noteService.listTrashBin(userId);
		return new JsonResult(list);
	}
	
	/**
	 * �����ʼ�
	 * @param userId
	 * @return
	 */
	@RequestMapping("/replay.do")
	@ResponseBody
	public Object replayNote(String noteId,String notebookId){
		boolean success = noteService.replayNote(noteId, notebookId);
		return new JsonResult(success);
	}
	
	/**
	 * ɾ���ʼ�
	 * @param noteId
	 * @return
	 */
	@RequestMapping("/remove.do")
	@ResponseBody
	public Object removeNote(String noteId){
		boolean success = noteService.removeNote(noteId);
		return new JsonResult(success);
	}
	
	/**
	 * ʵ�ֱʼǷ�ҳ��ѯ
	 * @param notebookId
	 * @param page
	 * @return
	 */
	@RequestMapping("/pageNote.do")
	@ResponseBody
	public Object pageNote(String notebookId,Integer page){
		List<Map<String, Object>> list = noteService.listNotes(notebookId, page);
		return new JsonResult(list);
	}
	
}