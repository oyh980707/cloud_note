package com.loveoyh.note.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.loveoyh.note.entity.User;

public class AccessFilter implements Filter {

	private String login = "/log_in.html";
	
    public AccessFilter() {
    }

	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		//�Ź�log_in.html
		String path = req.getRequestURI();
//		System.out.println("access:"+path);
		if(path.endsWith(login)){
			chain.doFilter(req, res);
			return;//��ֹ������Ӧ,web��Ŀ�����������Ӧ
		}
		if(path.endsWith("alert_error.html")){
			chain.doFilter(req, res);
			return;
		}
		if(path.endsWith("demo.html")){
			chain.doFilter(req, res);
			return;
		}
		if(path.endsWith("ajaxupload.html")){
			chain.doFilter(req, res);
			return;
		}
		/*
		 * ����û���¼
		 * �����¼�ͷŹ�
		 * ���û�е�¼���ض��򵽵�¼ҳ
		 */
		User user = (User) session.getAttribute("loginUser");
		if(user==null){//û�е�¼
			//�ض��򵽵�¼ҳ
			res.sendRedirect(req.getContextPath()+login);
			return;
		}
		
		chain.doFilter(req,res);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
