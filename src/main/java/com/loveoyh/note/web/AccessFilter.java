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
		//放过log_in.html
		String path = req.getRequestURI();
//		System.out.println("access:"+path);
		if(path.endsWith(login)){
			chain.doFilter(req, res);
			return;//防止二次响应,web项目不允许二次响应
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
		 * 检查用户登录
		 * 如果登录就放过
		 * 如果没有登录就重定向到登录页
		 */
		User user = (User) session.getAttribute("loginUser");
		if(user==null){//没有登录
			//重定向到登录页
			res.sendRedirect(req.getContextPath()+login);
			return;
		}
		
		chain.doFilter(req,res);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
