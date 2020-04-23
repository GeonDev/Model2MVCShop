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
		//web.xml <init-param> ���εǾ� �ִ� resources�� �ҷ��´�. => ���⼱ com/model2/mvc/resources/actionmapping.properties�� ���εǾ� �ִ�.
		String resources=getServletConfig().getInitParameter("resources");
	
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = request.getRequestURI();
		
		//request.getContextPath() => ������Ʈ ��θ� �޾ƿ´�. 
		String contextPath = request.getContextPath();
		
		//������Ʈ ��� ���������� ������ ���� => path�� �ʿ��� page ������ ����/ "login.do" �� ���·� �����		
		String path = url.substring(contextPath.length());
		System.out.println(path);
		
		try{
			//���ε� ��ü�� �����Ѵ�.
			Action action = mapper.getAction(path);
			
			//�������ؽ�Ʈ�� �����Ѵ�.
			action.setServletContext(getServletContext());
			
			//�־��� �׼��� ����� �����Ѵ�. excute()�� �̿��Ͽ� ������ �����Ѵ�.
			String resultPage=action.execute(request, response);
			
			// ���޹��� ��� ��Ʈ������ ���� �̵��� ��ġ�� ����
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			//excute�� �����ϰ� �����ų ��������(JSP)�� ���� ��Ų��.
			//���޹��� ��Ʈ���� ���ڸ��� ���� forward/redirect�� ������ �����Ų��.
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}