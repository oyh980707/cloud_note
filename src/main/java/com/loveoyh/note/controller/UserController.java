package com.loveoyh.note.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.loveoyh.note.entity.User;
import com.loveoyh.note.service.UserService;
import com.loveoyh.note.service.ex.PasswordException;
import com.loveoyh.note.service.ex.UserNameException;
import com.loveoyh.note.service.ex.UserNotFoundException;
import com.loveoyh.note.util.JsonResult;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{
	@Resource
	private UserService userService;
	
	/**
	 * ��¼
	 * @param name
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name,String password,HttpSession session){
//		response.setContentType("text/html;charset=utf8");
//		response.setCharacterEncoding("utf8");
//		try {
			
			User user = userService.login(name, password);
			/*
			 * ��¼�ɹ���ʱ��,��user��Ϣ���浽session,�����ڹ������м���¼���
			 */
			session.setAttribute("loginUser", user);
			return new JsonResult(user);
//		} catch(UserNotFoundException e){
//			e.printStackTrace();
//			return new JsonResult(e);
//		} catch (PasswordException e) {
//			e.printStackTrace();
//			/*
//			 * ʹ��map�ȷ�ʽ,��������˽���Ϊjson����,spring��Jackson�ͷ�װ�˶��ַ����������,
//			 */
////			Map<String,Object> map = new HashMap<String,Object>();
////			map.put("state", "1");
////			map.put("massage", e.getMessage());
//			
//			return new JsonResult(e);
//		} catch(Exception e){
//			e.printStackTrace();
//			return new JsonResult(e);
//		}
	}
	
	/**
	 * ��¼
	 * @param name
	 * @param nick
	 * @param password
	 * @param comfirm
	 * @return
	 */
	@RequestMapping("/regist.do")
	@ResponseBody
	public Object regist(String name,String nick,String password,String confirm){
		User user = null;
		user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
	}
	
	/**
	 * �������,����û��Ƿ�����,��ֹsession����
	 * @return
	 */
	@RequestMapping("/heart.do")
	@ResponseBody
	public Object heart(){
		return new JsonResult("���Ƿ���?");
	}
	
	/**
	 * @ResponseBody ע����Զ�������Ʒ���ֵ
	 * 1. �����javaBean(����,����) ����json
	 * 2. �����byte����,��byte����ֱ�ӷŽ���Ӧ��Ϣ��body
	 * ����produces="image/png" �������� content-type����
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/image.do",produces="image/png")
	@ResponseBody
	public byte[] image() throws Exception{
		return createPng();
	}
	
	/**
	 * ͼƬ���� ����Content-Disposition
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/downloadimg.do",produces="application/octet-stream")
	@ResponseBody
	public byte[] downloadimg(HttpServletResponse res) throws IOException{
		res.setHeader("Content-Disposition", "atachment;filename=\"demo.png\"");
		return createPng();
	}
	
	/**
	 * Excel���� ����Content-Disposition
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/downloadExcel.do",produces="application/octet-stream")
	@ResponseBody
	public byte[] downloadExcel(HttpServletResponse res) throws IOException{
		res.setHeader("Content-Disposition", "atachment;filename=\"demo.xls\"");
		return createExcel();
	}
	
	/**
	 * ����
	 * Spring MVC �п�������MultipartFile �������ص��ļ�
	 * �ļ��е�һ�����ݶ����Դ�MultipartFile �������ҵ�
	 * @param userfile1
	 * @param userfile2
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/upload.do")
	@ResponseBody
	public JsonResult upload(MultipartFile userfile1,MultipartFile userfile2) throws IllegalStateException, IOException{
		//������ص�ԭʼ�ļ���
		String file1 = userfile1.getOriginalFilename();
		String file2 = userfile2.getOriginalFilename();
//		System.out.println(file1);
//		System.out.println(file2);
		/*
		 * ���ֱ��淽��
		 */
		//1. transferTo(Ŀ���ļ�) ���ļ�����ֱ�ӱ��浽Ŀ���ļ�,���Դ�����ļ�
		//2. userfile1.getBytes() ��ȡ�ļ���ȫ������  ���ļ�ȫ����ȡ���ڴ�,�ʺϴ���С�ļ�
		//3. userfile1.getInputStream() ��ȡ�����ļ����� �ʺϴ�����ļ�
		
		File dir = new File("C:\\Users\\HP\\Desktop");
		dir.mkdirs();
		File f1 = new File(dir,file1);
		File f2 = new File(dir,file2);
		
		//��һ��
//		userfile1.transferTo(f1);
//		userfile2.transferTo(f2);
		
		//������
		InputStream in1 = userfile1.getInputStream();
		FileOutputStream fos1 = new FileOutputStream(f1);
		int b;
		while((b=in1.read())!=-1){
			fos1.write(b);
		}
		
		in1.close();
		fos1.close();
//		������������
//		BufferedReader br2 = new BufferedReader(new InputStreamReader(userfile1.getInputStream(), "UTF8"));
//		PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f2)));
//		
//		String line = null;
//		while((line=br2.readLine())!=null){
//			pw2.println(line);
//		}
//		br2.close();
//		pw2.close();
		
		InputStream in2 = userfile2.getInputStream();
		FileOutputStream fos2 = new FileOutputStream(f2);
		byte[] data = new byte[8*1024];
		int len;
		while((len=in2.read(data))!=-1){
			fos2.write(data, 0, len);
		}
		in2.close();
		fos2.close();
		
		return new JsonResult(true);
	}
	
	/**
	 * �û����������쳣����
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public JsonResult handlerUserNotFound(UserNotFoundException e){
		return new JsonResult(2,e);
	}
	
	/**
	 * �û����������쳣����
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public JsonResult handlerUserName(UserNameException e){
		return new JsonResult(4,e);
	}
	
	/**
	 * ��������쳣����
	 * @param e
	 * @return
	 */
	@ExceptionHandler(PasswordException.class)
	@ResponseBody
	public JsonResult handlerPassword(PasswordException e){
		return new JsonResult(3,e);
	}
	
//	/**
//	 * ����������������ִ�г����쳣��ʱ��,ִ���쳣������handlerException
//	 * @param e
//	 * @return
//	 */
//	@ExceptionHandler(Exception.class)
//	@ResponseBody
//	public Object handlerException(Exception e){
//		e.printStackTrace();
//		return new JsonResult(1,e);
//	}
	
	
	/**
	 * ����һ��ͼƬ,���ұ���Ϊpng��ʽ,���ر����Ժ������
	 * @return
	 * @throws IOException 
	 */
	private byte[] createPng() throws IOException{
		BufferedImage img = new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		//��ͼƬ�ϻ�������
		img.setRGB(100, 40, 0xffffff);
		//��ͼƬ����Ϊpng
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		byte[] png = out.toByteArray();
		out.close();
		return png;
	}
	
	/**
	 * ����Excel
	 * @return
	 * @throws IOException
	 */
	private byte[] createExcel() throws IOException{
		//����������
		HSSFWorkbook workbook = new HSSFWorkbook();
		//����������
		HSSFSheet sheet = workbook.createSheet("Demo");
		//�ڹ������д���������
		HSSFRow row = sheet.createRow(0);
		//�������еĸ���
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue("Hello Word!");
		//��Excel�ļ�����byte����
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		out.close();
		return out.toByteArray();
	}
}
