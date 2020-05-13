package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.*;

public class GetPuchaseAction extends Action{

	public GetPuchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));		
		Purchase purchase = new Purchase();
		
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		
		purchase = service.getPurchase(tranNo);		
		request.setAttribute("purchase", purchase);	

		
		return "forward:/purchase/getPurchase.jsp";
	}

}
