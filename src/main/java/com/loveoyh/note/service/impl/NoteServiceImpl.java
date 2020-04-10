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
import com.loveoyh.note.dao.ActivityDAO;
import com.loveoyh.note.dao.StarsDAO;
import com.loveoyh.note.dao.UserDAO;
import com.loveoyh.note.entity.Note;
import com.loveoyh.note.entity.Notebook;
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
	private ActivityDAO shareDAO;
	@Value("#{jdbc.pageSize}")
	private int pageSize;
	
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookNotFoundException{
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("�ʼǱ�ID����");
		}
		//����һ: ͨ������ʵ����,ͨ���ʼǱ�id��ѯ�ʼǱ�,���ʼǱ��Ƿ����,���ֲ�ѯ��ʽ�˷���Դ,��ѯ��������ֻΪ���жϴ�����
//		Notebook notebook = notebookDAO.findNotebookById(notebookId);
//		if(notebook==null){
//			throw new NotebookNoteFoundException("û�бʼǱ�");
//		}
		//������: ͨ����ѯ�ʼǱ������ݿ����Ƿ����,ͨ������ֵ����ȷ��
		int n = notebookDAO.countNotebookById(notebookId);
//		System.out.println("�ʼǱ���"+n+"��");
		if(n!=1){
			throw new NotebookNotFoundException("û�бʼǱ�");
		}
		return noteDAO.findNotesByNotebookId(notebookId);
	}
	
	@Transactional
	public Note getNote(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�иñʼ�");
		}
		return note;
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED)//Ĭ��Ϊ������뷽ʽ
	public Note add(String userId, String notebookId, String title)
			throws UserNotFoundException, NotebookNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("�û�ID����");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("�ʼǱ�ID��");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("û�бʼǱ�");
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
			throw new NoteNotFoundException("����ʧ��!");
		}
		//���ӱʼ���5������,��ǰ����ᴫ����addStar������,����Ϊһ������
		addStar(userId, 5);
		return note;
	}

	public boolean updateNote(String noteId, String title, String body) throws NoteNotFoundException{
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
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
			throw new NoteNotFoundException("����ʧ��!");
		}
		return true;
	}

	public boolean move(String noteId, String notebookId) throws NotebookNotFoundException, NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("�ʼǱ�ID��");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("û�бʼǱ�");
		}
		
		if(notebookId.equals(note.getNotebookId())){
			throw new NotebookNotFoundException("�����ƶ�����ǰ�ʼǱ���");
		}
		
		Note data = new Note();
		data.setId(note.getId());
		data.setNotebookId(notebookId);
		data.setLastModifyTime(System.currentTimeMillis());
		n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("����ʧ��!");
		}
		return true;
	}

	public boolean delete(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		Note data = new Note();
		data.setId(note.getId());
		data.setLastModifyTime(System.currentTimeMillis());
		data.setNoteTypeId("2");
		int n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("ɾ��ʧ��!");
		}
		return true;
	}

	public List<Map<String, Object>> listTrashBin(String userId) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("�û�ID����");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		List<Map<String, Object>> list = noteDAO.findNotes(userId, null, "2");
		return list;
	}

	public boolean replayNote(String noteId, String notebookId)
			throws NoteNotFoundException, NotebookNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("�ʼǱ�ID��");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("û�бʼǱ�");
		}
		
		Note data = new Note();
		data.setId(note.getId());
		data.setNotebookId(notebookId);
		data.setLastModifyTime(System.currentTimeMillis());
		data.setNoteTypeId("5");
		n = noteDAO.updateNote(data);
		if(n!=1){
			throw new NoteNotFoundException("����ʧ��!");
		}
		return true;
	}

	public boolean removeNote(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		int n = noteDAO.removeNote(noteId);
		if(n!=1){
			throw new RemoveException("ɾ��ʧ��!");
		}
		return true;
	}
	
	public int deleteNotes(String... noteIds) throws NoteNotFoundException {
//		for(String id : noteIds){
//			if(id==null || id.trim().isEmpty()){
//				throw new NoteNotFoundException("�ʼ�ID����");
//			}
//			Note note = noteDAO.findNoteById(id);
//			if(note == null){
//				throw new NoteNotFoundException("û�бʼ�");
//			}
//			int n = noteDAO.deleteNoteById(id);
//			if(n!=1){
//				throw new NoteNotFoundException("ɾ��ʧ��");
//			}
//		}
		int n = noteDAO.deleteNotes(noteIds);
		if(n!=noteIds.length){
			throw new NoteNotFoundException("ɾ���ʼ�ʧ��");
		}
		return n;
	}
	
	public boolean addStar(String userId, int stars) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID���ܿ�");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		//����Ƿ�����
		Stars star = starsDAO.findSatrsByUserId(userId);
		if(star==null){//���û������
			String id = UUID.randomUUID().toString();
			star = new Stars(id, userId, stars);
			int n = starsDAO.insertStars(star);
			if(n!=1){
				throw new RuntimeException("ʧ��");
			}
		}else{//���������,�������е���������������
			int n = star.getStars() + stars;
			if(n<0){
				// n=0
				throw new RuntimeException("�۷�̫��");
			}
			star = new Stars();
			star.setStars(n);
			star.setUserId(userId);
			n = starsDAO.updateStars(star);
			if(n!=1){
				throw new RuntimeException("ʧ��");
			}
		}
		return true;
	}

	public List<Map<String, Object>> listNotes(String notebookId,Integer page)
			throws NotebookNotFoundException {
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookNotFoundException("�ʼǱ�ID��");
		}
		int n = notebookDAO.countNotebookById(notebookId);
		if(n!=1){
			throw new NotebookNotFoundException("û�бʼǱ�");
		}
		int start = page * pageSize;
		return noteDAO.findNotesByPage(notebookId, start, pageSize, "cn_note");
	}
	
	public void shareNote(String noteId, String userId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		//��������
		//����Ƿ�����
		Stars star = starsDAO.findSatrsByUserId(userId);
		if(star==null){//���û������
			String id = UUID.randomUUID().toString();
			star = new Stars(id, userId, 10);
			int n = starsDAO.insertStars(star);
			if(n!=1){
				throw new RuntimeException("ʧ��");
			}
		}else{//���������,�������е���������������
			int n = star.getStars() + 10;
			if(n<0){
				// n=0
				throw new RuntimeException("�۷�̫��");
			}
			star = new Stars();
			star.setStars(n);
			star.setUserId(userId);
			n = starsDAO.updateStars(star);
			if(n!=1){
				throw new RuntimeException("ʧ��");
			}
		}
		//���ıʼ�����
		Note type = new Note();
		type.setId(noteId);
		type.setNoteTypeId("4");
		type.setLastModifyTime(System.currentTimeMillis());
		noteDAO.updateNote(type);
	}

	public List<Note> searchNote(String keywords) {
		return noteDAO.findNotesByKeywords(keywords);
	}

	public Notebook findNotebookByNoteId(String noteId) throws NoteNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		Notebook notebook = notebookDAO.findNotebookById(note.getNotebookId());
		notebook.setCreatetime(null);
		notebook.setNotebookDesc(null);
		notebook.setNotebookTypeId(null);
		notebook.setUserId(null);
		return notebook;
	}

	public void collect(String userId, String noteId) throws UserNotFoundException, NoteNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID���ܿ�");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteNotFoundException("�ʼ�ID����");
		}
		Note note = noteDAO.findNoteById(noteId);
		if(note == null){
			throw new NoteNotFoundException("û�бʼ�");
		}
		
		//�ղرʼ�:����ѯ�ıʼ�ʵ�����޸��������û����ʼ�Id�ͱʼ�����
		note.setId(UUID.randomUUID().toString());
		note.setNoteTypeId("1");
		note.setUserId(userId);
		
		noteDAO.addNote(note);
	}

	public List<Map<String,Object>> listCollects(String userId) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID���ܿ�");
		}
		User user = userDAO.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		
		List<Map<String,Object>> notes = noteDAO.findNotes(userId, null, "1");
		return notes;
	}
}
