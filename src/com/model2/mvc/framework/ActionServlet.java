package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = request.getRequestURI();
		//request.getContextPath() => 프로젝트 경로만 받아온다. 
		String contextPath = request.getContextPath();
		//프로젝트 경로 다음값부터 끝까지 저장
		String path = url.substring(contextPath.length());
		System.out.println(path);
		
		try{
			//매핑된 객체를 저장한다.
			Action action = mapper.getAction(path);
			//서블릿컨텍스트를 세팅한다.
			action.setServletContext(getServletContext());
			
			//주어진 액션의 명령을 수행한다. excute()를 이용하여 동작을 수행한다.
			String resultPage=action.execute(request, response);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			//excute를 실행하고 실행시킬 웹페이지(JSP)를 전송 시킨다.
			//전달받은 스트링의 앞자리에 따라 forward/redirect로 나누어 실행시킨다.
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}