package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PurchaseVO purchaseVO = new PurchaseVO();
		PurchaseServiceImpl service = new PurchaseServiceImpl();		
		ProductServiceImpl serviceProd = new ProductServiceImpl();		
		UserVO buyer = new UserVO();
					
		HttpSession session = request.getSession();		
		buyer = (UserVO)session.getAttribute("user");
			
		purchaseVO.setBuyer(buyer);
		purchaseVO.setPurchaseProd(serviceProd.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));		
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));					
		
		
		//데이터 삽입		
		service.addPurchase(purchaseVO);					
		
		//requset에 값 추가
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchase.jsp";		
	}

}
