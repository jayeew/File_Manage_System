package com.fuck.springmvc.views;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
public class HelloView implements View{
	@Override
	public String getContentType() {
		// TODO 自动生成的方法存根
		return "/html";
	}
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自动生成的方法存根
		response.getWriter().println("hello view, time " + 
				new Date());
	}
}
