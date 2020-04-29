package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;




public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,	HttpServletResponse response) throws Exception {
		int prodNO =Integer.parseInt(request.getParameter("PROD_NO"));
		
		Product product=new Product();

		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(product);
		
		HttpSession session=request.getSession();
		int sessionId=((Product)session.getAttribute("PROD_NO")).getProdNo();
	
		if(sessionId == prodNO){
			session.setAttribute("product", product);
		}
		
		return "redirect:/getProduct.do?PROD_NO="+prodNO;
	}
}