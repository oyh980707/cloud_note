package com.loveoyh.note.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.loveoyh.note.dao.NoteDAO;
import com.loveoyh.note.dao.NotebookDAO;
import com.loveoyh.note.dao.ShareDAO;
import com.loveoyh.note.dao.StarsDAO;
import com.loveoyh.note.dao.UserDAO;
import com.loveoyh.note.entity.Note;
import com.loveoyh.note.entity.Share;
import com.loveoyh.note.entity.Stars;
import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.NoteService;
import com.loveoyh.note.service.ex.NoteNotFoundException;
import com.loveoyh.note.service.ex.NotebookNotFoundException;
import com.loveoyh.note.service.ex.RemoveException;
import com.loveoyh.note.service.ex.UserNotFoundException;

import sun.security.provider.SHA;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteDAO noteDAO;
	@Resource
	private NotebookDAO notebookDAO;
	@Resource	
	private UserDAO userDAO;
	@Resource
	private StarsDAO starsDAO;
	@Resource
	private ShareDAO shareDAO;
	@Value("#{jdbc.pageSize}")
	private int pageSize;
	
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException{
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("笔记本ID错误");
		}
		//方法一: 通过创建实体类,通过笔记本id查询笔记本,看笔记本是否存在,这种查询方式浪费资源,查询大量数据只为了判断存在性
//		Notebook notebook = notebookDAO.findNotebookById(notebookId);
//		if(notebook==null){
//			throw new NotebookNoteFoundException("没有笔记本");
//		}
		//方法二: 通过查询笔记本在数据库中是否存在,通过返回值可以确定
		int n = notebookDAO.countNotebookById(notebookId);
//		System.out.println("笔记本有"+n+"个");
		if(n!=1){
			throw new NotebookNotFoundException("没有笔记本");
		}
		return noteDAO.findNotes(null, notebookId, "5");
	}
	
//	@Transactional
	public Note getNote(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有该笔记");
		}
		return note;
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED)//默认为这个隔离方式
	public Note add(String userId, String notebookId, String title)
			throws UserNotFoundException, NotebookNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("用户ID错误");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("笔记本ID误");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("没有笔记本");
		}
		
		Note note = new Note();
		String id = UUID.randomUUID().toString();
		note.setId(id);
		note.setUserId(userId);
		note.setNotebookId(notebookId);
		note.setNoteStatusId("1");
		note.setNoteTypeId("5");
		note.setTitle(title);
		note.setBody("");
		note.setCreatetime(System.currentTimeMillis());
		note.setLastModifyTime(System.currentTimeMillis());
		
		n = noteDAO.addNote(note);
		if(n!=1){
			throw new NoteNotFoundException("保存失败!");
		}
		//增加笔记送5个星星,当前事务会传播到addStar方法中,整合为一个事务
		addStar(userId, 5);
		return note;
	}

	public boolean updateNote(String noteId, String title, String body) throws NoteNotFoundException{
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有笔记");
		}
		Note data = new Note();
		if(title!=null && !title.equals(note.getTitle())){
	        data.setTitle(title);
	    }
	    if(body!=null && !body.equals(note.getBody())){
	        data.setBody(body);
	    }
	    data.setId(noteId);
	    data.setLastModifyTime(System.currentTimeMillis());
		int n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("保存失败!");
		}
		return true;
	}

	public boolean move(String noteId, String notebookId) throws NotebookNotFoundException, NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有笔记");
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("笔记本ID误");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("没有笔记本");
		}
		
		if(notebookId.equals(note.getNotebookId())){
			throw new NotebookNotFoundException("不能移动到当前笔记本下");
		}
		
		Note data = new Note();
		data.setId(note.getId());
		data.setNotebookId(notebookId);
		data.setLastModifyTime(System.currentTimeMillis());
		n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("保存失败!");
		}
		return true;
	}

	public boolean delete(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有笔记");
		}
		Note data = new Note();
		data.setId(note.getId());
		data.setLastModifyTime(System.currentTimeMillis());
		data.setNoteTypeId("2");
		int n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("删除失败!");
		}
		return true;
	}

	public List<Map<String, Object>> listTrashBin(String userId) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("用户ID错误");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		List<Map<String, Object>> list = noteDAO.findNotes(userId, null, "2");
		return list;
	}

	public boolean replayNote(String noteId, String notebookId)
			throws NoteNotFoundException, NotebookNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有笔记");
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("笔记本ID误");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("没有笔记本");
		}
		
		Note data = new Note();
		data.setId(note.getId());
		data.setNotebookId(notebookId);
		data.setLastModifyTime(System.currentTimeMillis());
		data.setNoteTypeId("5");
		n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("撤销失败!");
		}
		return true;
	}

	public boolean removeNote(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有笔记");
		}
		int n = noteDAO.removeNote(noteId);
		if(n!=1){
			throw new RemoveException("删除失败!");
		}
		return true;
	}
	
	public int deleteNotes(String... noteIds) throws NoteNotFoundException {
//		for(String id : noteIds){
//			if(id==null || id.trim().isEmpty()){
//				throw new NoteNotFoundException("笔记ID错误");
//			}
//			Note note = noteDAO.findNoteById(id);
//			if(note == null){
//				throw new NoteNotFoundException("没有笔记");
//			}
//			int n = noteDAO.deleteNoteById(id);
//			if(n!=1){
//				throw new NoteNotFoundException("删除失败");
//			}
//		}
		int n = noteDAO.deleteNotes(noteIds);
		if(n!=noteIds.length){
			throw new NoteNotFoundException("删除笔记失败");
		}
		return n;
	}
	
	public boolean addStar(String userId, int stars) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID不能空");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		//检查是否有星
		Stars star = starsDAO.findSatrsByUserId(userId);
		if(star==null){//如果没有星星
			String id = UUID.randomUUID().toString();
			star = new Stars(id, userId, stars);
			int n = starsDAO.insertStars(star);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}else{//如果有星星,就在现有的星星中增加星星
			int n = star.getStars() + stars;
			if(n<0){
				// n=0
				throw new RuntimeException("扣分太多");
			}
			star = new Stars();
			star.setStars(n);
			star.setUserId(userId);
			n = starsDAO.updateStars(star);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}
		return true;
	}

	public List<Map<String, Object>> listNotes(String notebookId,Integer page)
			throws NotebookNotFoundException {
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("笔记本ID误");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("没有笔记本");
		}
		int start = page * pageSize;
		return noteDAO.findNotesByPage(notebookId, start, pageSize, "cn_note");
	}
	
	public void shareNote(String noteId, String userId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("笔记ID错误");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("没有笔记");
		}
		Share share = new Share();
		share.setId(UUID.randomUUID().toString());
		share.setNoteId(note.getId());
		share.setTitle(note.getTitle());
		share.setBody(note.getBody());
		shareDAO.save(share);
		//增加星星
		//检查是否有星
		Stars star = starsDAO.findSatrsByUserId(userId);
		if(star==null){//如果没有星星
			String id = UUID.randomUUID().toString();
			star = new Stars(id, userId, 10);
			int n = starsDAO.insertStars(star);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}else{//如果有星星,就在现有的星星中增加星星
			int n = star.getStars() + 10;
			if(n<0){
				// n=0
				throw new RuntimeException("扣分太多");
			}
			star = new Stars();
			star.setStars(n);
			star.setUserId(userId);
			n = starsDAO.updateStars(star);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}
		//更改笔记类型
		Note type = new Note();
		type.setNoteTypeId("3");
		noteDAO.updateNote(type);
	}
}
