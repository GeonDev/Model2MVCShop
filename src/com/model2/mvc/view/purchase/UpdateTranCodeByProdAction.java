package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.view.product.ListProductAction;



public class UpdateTranCodeByProdAction extends Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));		
		String tranCode = request.getParameter("tranCode").trim();
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		ProductService prodService = new ProductServiceImpl();
		PurchaseVO purchaseVO = new PurchaseVO();			
		
		purchaseVO = service.getPurchase2(prodNo);
		purchaseVO.setTranCode(tranCode);
		
		//TransCode 업데이트
		service.updateTranCode(purchaseVO);
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
