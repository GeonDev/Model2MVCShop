package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.common.*;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.*;

public class ListPurchaseAction extends Action{

	public ListPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search=new Search();
		
		User buyer = new User();
		
		HttpSession session = request.getSession();		
		buyer = (User)session.getAttribute("user");
		
		int page=1;
		
		//선택한 페이지가 있으면 해당 페이지로 세팅
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}	
		
		search.setCurrentPage(page);

		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		search.setPageSize(Integer.parseInt(pageUnit));
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		String buyerId = buyer.getUserId();
		
		HashMap<String,Object> map=service.getPurchaseList(search, buyerId);

		request.setAttribute("map", map);
		request.setAttribute("search", search);
				
		return "forward:/purchase/listPurchase.jsp";
	}
	
	
	
	
	

}
