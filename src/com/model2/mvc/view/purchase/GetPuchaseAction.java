package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class GetPuchaseAction extends Action{

	public GetPuchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));		
		PurchaseVO purchaseVO = new PurchaseVO();
		
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		
		purchaseVO = service.getPurchase(tranNo);		
		request.setAttribute("purchaseVO", purchaseVO);	

		
		return "forward:/purchase/getPurchase.jsp";
	}

}
