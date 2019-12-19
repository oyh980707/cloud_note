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
	 * 登录
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
			 * 登录成功的时候,将user信息保存到session,用于在过滤器中检查登录情况
			 */
			session.setAttribute("loginUser", user);
			return new JsonResult(user);
//		} catch(UserNotFoundException e){
//			e.printStackTrace();
//			return new JsonResult(e);
//		} catch (PasswordException e) {
//			e.printStackTrace();
//			/*
//			 * 使用map等方式,充分利用了解析为json传输,spring和Jackson就封装了对字符编码的问题,
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
	 * 登录
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
	 * 心跳检测,检查用户是否在线,防止session过期
	 * @return
	 */
	@RequestMapping("/heart.do")
	@ResponseBody
	public Object heart(){
		return new JsonResult("您是否还在?");
	}
	
	/**
	 * @ResponseBody 注解会自动处理控制返回值
	 * 1. 如果是javaBean(数组,集合) 返回json
	 * 2. 如果是byte数字,则将byte数组直接放进响应消息的body
	 * 利用produces="image/png" 用于设置 content-type属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/image.do",produces="image/png")
	@ResponseBody
	public byte[] image() throws Exception{
		return createPng();
	}
	
	/**
	 * 图片下载 设置Content-Disposition
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
	 * Excel下载 设置Content-Disposition
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
	 * 上载
	 * Spring MVC 中可以利用MultipartFile 接收上载的文件
	 * 文件中的一切数据都可以从MultipartFile 对象中找到
	 * @param userfile1
	 * @param userfile2
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/upload.do")
	@ResponseBody
	public JsonResult upload(MultipartFile userfile1,MultipartFile userfile2) throws IllegalStateException, IOException{
		//获得上载的原始文件名
		String file1 = userfile1.getOriginalFilename();
		String file2 = userfile2.getOriginalFilename();
//		System.out.println(file1);
//		System.out.println(file2);
		/*
		 * 三种保存方法
		 */
		//1. transferTo(目标文件) 将文件爱你直接保存到目标文件,可以处理大文件
		//2. userfile1.getBytes() 获取文件的全部数据  将文件全部读取到内存,适合处理小文件
		//3. userfile1.getInputStream() 获取上载文件的流 适合处理大文件
		
		File dir = new File("C:\\Users\\HP\\Desktop");
		dir.mkdirs();
		File f1 = new File(dir,file1);
		File f2 = new File(dir,file2);
		
		//第一种
//		userfile1.transferTo(f1);
//		userfile2.transferTo(f2);
		
		//第三种
		InputStream in1 = userfile1.getInputStream();
		FileOutputStream fos1 = new FileOutputStream(f1);
		int b;
		while((b=in1.read())!=-1){
			fos1.write(b);
		}
		
		in1.close();
		fos1.close();
//		出现乱码问题
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
	 * 用户名不存在异常捕获
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public JsonResult handlerUserNotFound(UserNotFoundException e){
		return new JsonResult(2,e);
	}
	
	/**
	 * 用户名不存在异常捕获
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public JsonResult handlerUserName(UserNameException e){
		return new JsonResult(4,e);
	}
	
	/**
	 * 密码错误异常捕获
	 * @param e
	 * @return
	 */
	@ExceptionHandler(PasswordException.class)
	@ResponseBody
	public JsonResult handlerPassword(PasswordException e){
		return new JsonResult(3,e);
	}
	
//	/**
//	 * 在其他控制器方法执行出现异常的时候,执行异常处理方法handlerException
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
	 * 创建一张图片,并且编码为png格式,返回编码以后的数据
	 * @return
	 * @throws IOException 
	 */
	private byte[] createPng() throws IOException{
		BufferedImage img = new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		//在图片上绘制内容
		img.setRGB(100, 40, 0xffffff);
		//将图片编码为png
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		byte[] png = out.toByteArray();
		out.close();
		return png;
	}
	
	/**
	 * 创建Excel
	 * @return
	 * @throws IOException
	 */
	private byte[] createExcel() throws IOException{
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("Demo");
		//在工作表中创建数据行
		HSSFRow row = sheet.createRow(0);
		//创建行中的格子
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue("Hello Word!");
		//将Excel文件保存byte数组
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		out.close();
		return out.toByteArray();
	}
}
