package com.loveoyh.note.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loveoyh.note.entity.User;
import com.loveoyh.note.util.JsonResult;

@Component
public class AccessInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		String path = request.getRequestURI();
//		System.out.println("Interceptor:"+path);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		System.out.println(user);
		/*
		 * ���û�е�¼�ͷ��ش����json��Ϣ
		 * �����½�˾ͷŹ�����
		 */
		if(user==null){
//			JsonResult result = new JsonResult("��Ҫ���µ�¼");
//			//����response ���󷵻ؽ��
//			response.setContentType("application/json;charset=UTF-8");
//			response.setCharacterEncoding("utf-8");
//			ObjectMapper mapper = new ObjectMapper();
//			String json = mapper.writeValueAsString(result);
//			
//			response.getWriter().println(json);
//			response.flushBuffer();
			//ֱ���ض��򵽵�¼ҳ��
			response.sendRedirect("/cloud_note/log_in.html");
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
