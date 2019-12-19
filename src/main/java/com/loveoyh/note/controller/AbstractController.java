package com.loveoyh.note.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loveoyh.note.service.ex.UserNotFoundException;
import com.loveoyh.note.util.JsonResult;

public abstract class AbstractController {
	
	public static final int SUCCESS = 0;

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonResult handlerException(Exception e) {
		e.printStackTrace();
		return new JsonResult(e);
	}

}