package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));		
		
		ProductServiceImpl serviceProd = new ProductServiceImpl();
		Product product = serviceProd.getProduct(prodNo);
		request.setAttribute("product", product);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
		
		
	}

}
