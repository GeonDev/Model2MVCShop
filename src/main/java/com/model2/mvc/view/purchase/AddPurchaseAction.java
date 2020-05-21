package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class AddPurchaseAction{

	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Purchase purchase = new Purchase();
		PurchaseServiceImpl service = new PurchaseServiceImpl();		
		ProductServiceImpl serviceProd = new ProductServiceImpl();		
		User buyer = new User();
					
		HttpSession session = request.getSession();		
		buyer = (User)session.getAttribute("user");
			
		purchase.setBuyer(buyer);
		purchase.setPurchaseProd(serviceProd.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));					
		
		
		//데이터 삽입		
		service.addPurchase(purchase);					
		
		//requset에 값 추가
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";		
	}

}
