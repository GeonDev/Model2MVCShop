package com.model2.mvc.common.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpUtil {
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path){
		try{
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}catch(Exception ex){
			System.out.println("forward ���� : " + ex);
			throw new RuntimeException("forward ���� : " + ex);
		}
	}
	
	public static void redirect(HttpServletResponse response, String path){
		try{
			response.sendRedirect(path);
		}catch(Exception ex){
			System.out.println("redirect ���� : " + ex);
			throw new RuntimeException("redirect ����  : " + ex);
		}
	}
	
	public static String convertKO(String paramValue) {		
		String result = null;
		
		try {
			byte[] b = paramValue.getBytes("8859_1");
			result = new String(b,"EUC_KR");
			
		}catch (Exception e) {
			System.out.println("�ѱۺ�ȯ�� ���� �߻�");
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	
}