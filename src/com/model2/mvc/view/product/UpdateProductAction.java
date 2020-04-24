package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;



public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,	HttpServletResponse response) throws Exception {
		int prodNO =Integer.parseInt(request.getParameter("PROD_NO"));
		
		ProductVO productVO=new ProductVO();

		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		
		HttpSession session=request.getSession();
		int sessionId=((ProductVO)session.getAttribute("PROD_NO")).getProdNo();
	
		if(sessionId == prodNO){
			session.setAttribute("product", productVO);
		}
		
		return "redirect:/getProduct.do?PROD_NO="+prodNO;
	}
}