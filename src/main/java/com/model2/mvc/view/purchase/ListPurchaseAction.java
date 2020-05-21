package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.model2.mvc.common.*;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.*;

public class ListPurchaseAction{

	public ListPurchaseAction() {
		// TODO Auto-generated constructor stub
	}


	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search=new Search();
		
		User buyer = new User();
		int currentPage=1;
		
		HttpSession session = request.getSession();		
		buyer = (User)session.getAttribute("user");		
	
		
		//선택한 페이지가 있으면 해당 페이지로 세팅
		if(request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}	
		
		search.setCurrentPage(currentPage);		
		
		// web.xml  meta-data 로 부터 상수 추출 
		//int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		//int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		int pageSize = 3;
		int pageUnit = 5;
		search.setPageSize(pageSize);
		search.setPageSize(pageUnit);
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		String buyerId = buyer.getUserId();
		
		Map<String,Object> map=service.getPurchaseList(search, buyerId);
		Page resultPage	= new Page( currentPage, ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
				
		return "forward:/purchase/listPurchase.jsp";
	}
	
	
	
	
	

}
