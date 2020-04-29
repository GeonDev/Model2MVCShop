package com.model2.mvc.view.purchase;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.*;

public class UpdateTranCodeAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));		
		String tranCode = request.getParameter("tranCode").trim();
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		Purchase purchase = new Purchase();			
		
		purchase = service.getPurchase(tranNo);
		purchase.setTranCode(tranCode);
		
		//TranCode를 업데이트 한다.
		service.updateTranCode(purchase);

		return "redirect:/listPurchase.do";
	}

}
