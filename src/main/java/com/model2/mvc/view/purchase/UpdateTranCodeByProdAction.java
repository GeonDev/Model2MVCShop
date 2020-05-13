package com.model2.mvc.view.purchase;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.*;

public class UpdateTranCodeByProdAction extends Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));		
		String tranCode = request.getParameter("tranCode").trim();
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		Purchase purchase = new Purchase();			
		
		purchase = service.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		
		//TransCode 업데이트
		service.updateTranCode(purchase);
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
