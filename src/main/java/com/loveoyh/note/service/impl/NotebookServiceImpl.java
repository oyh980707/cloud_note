package com.loveoyh.note.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.validator.ValidateWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loveoyh.note.dao.NotebookDAO;
import com.loveoyh.note.dao.UserDAO;
import com.loveoyh.note.entity.Notebook;
import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.NotebookService;
import com.loveoyh.note.service.ex.NotebookNotFoundException;
import com.loveoyh.note.service.ex.RemoveException;
import com.loveoyh.note.service.ex.UpdateException;
import com.loveoyh.note.service.ex.UserNotFoundException;

@Service("notebookService")
@Transactional
public class NotebookServiceImpl implements NotebookService {
	
	@Resource
	private NotebookDAO notebookDAO;
	@Resource
	private UserDAO userDao;
	@Value("#{jdbc.pageSize}")
	private int pageSize;
	
	public List<Map<String, Object>> listNotebooks(String userId,String type) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID���ܿ�");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		return notebookDAO.findNotebooksByUserId(userId,type);
	}

	public List<Map<String, Object>> listNotebooks(String userId, Integer page) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID���ܿ�");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		if(page==null){
			page = 0;
		}
		int start = page * pageSize;
		return notebookDAO.findNotebooksByPage(userId, start, pageSize, "cn_notebook");
	}

	public Notebook addNotebook(String userId, String name) throws UserNotFoundException,NotebookNotFoundException{
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("�û�ID����");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		
		Notebook notebook = new Notebook();
		notebook.setId(UUID.randomUUID().toString());
		notebook.setUserId(userId);
		notebook.setName(name);
		notebook.setNotebookDesc(null);
		notebook.setNotebookTypeId("5");
		Date time = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createtime = formatter.format(time);
		notebook.setCreatetime(createtime);
		
		int rows = notebookDAO.addNotebook(notebook);
		if(rows != 1) {
			throw new NotebookNotFoundException("��ӱʼǱ�ʧ�ܣ�");
		}
		return notebook;
	}

	public void deleteNotebook(String notebookId) throws NotebookNotFoundException {
		Notebook notebook = notebookDAO.findNotebookById(notebookId);
		if(notebook == null) {
			throw new NotebookNotFoundException("�ʼǱ�������!");
		}
		
		Notebook newNotebook = new Notebook();
		newNotebook.setId(notebookId);
		newNotebook.setNotebookTypeId("2");
		
		int rows = notebookDAO.updateNotebook(newNotebook);
		if(rows != 1) {
			throw new UpdateException("ɾ���ʼǱ�ʧ��!");
		}
	}
	
	public void removeNotebook(String notebookId) throws NotebookNotFoundException {
		Notebook notebook = notebookDAO.findNotebookById(notebookId);
		if(notebook == null) {
			throw new NotebookNotFoundException("�ʼǱ�������!");
		}
		
		int rows = notebookDAO.removeNotebook(notebookId);
		if(rows != 1) {
			throw new RemoveException("ɾ��ʧ�ܣ�");
		}
	}

	public void replayNotebook(String notebookId) throws NotebookNotFoundException {
		Notebook notebook = notebookDAO.findNotebookById(notebookId);
		if(notebook == null) {
			throw new NotebookNotFoundException("�ʼǱ�������!");
		}
		
		Notebook newNotebook = new Notebook();
		newNotebook.setId(notebookId);
		newNotebook.setNotebookTypeId("5");
		
		int rows = notebookDAO.updateNotebook(newNotebook);
		if(rows != 1) {
			throw new UpdateException("�����ʼǱ�ʧ��!");
		}
	}



	

}
