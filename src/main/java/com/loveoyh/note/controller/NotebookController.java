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
	 * ͨ��cookie ajax ���������û�id ���ұʼǱ��б�
	 * @param userId �û�id
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String userId,String type){
		List<Map<String, Object>> list = notebookService.listNotebooks(userId,type);
		return new JsonResult(list);
	}
	
	/**
	 * ͨ��id��ҳ�� ���ұʼǱ�
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
	 * ͨ��cookie ajax ���������û�id ���ұʼǱ�����ӱʼ�
	 * @param userId �û�id
	 * @param title �ʼǱ�����
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult add(String userId,String name){
		Notebook notebook = notebookService.addNotebook(userId, name);
		return new JsonResult(notebook);
	}
	
	/**
	 * ɾ���ʼǱ�������վ
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
	 * ����ɾ���ʼǱ�
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
	 * �����ʼǱ�
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











