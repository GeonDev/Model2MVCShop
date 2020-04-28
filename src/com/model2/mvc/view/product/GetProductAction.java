package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String prodNo=request.getParameter("prodNo");
		
		ProductService service=new ProductServiceImpl();
		ProductVO productVO=service.getProduct(Integer.parseInt(prodNo));
		
		request.setAttribute("productVO", productVO);		
		
		
		//���� ��Ű�� ������ �޾ƿ´�.
		String history = null;
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {				
					history = cookie.getValue();				
				}
			}
		}		
		if(history == null || history.equals("")) {
			history += prodNo;
		}else {
			history += ","+prodNo;
		}
		
		history = history.replace("null", "");	
		history = history.trim();
		
		System.out.println("��Ű ���� : " + history);
		
		Cookie cookie = new Cookie("history", history);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		
		if(request.getParameter("menu").equals("manage")) {
			return "forward:/product/updateProduct.jsp";				
		}
		
		return "forward:/product/getProduct.jsp";
	}
}