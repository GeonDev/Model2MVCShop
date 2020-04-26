package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String prodNo=request.getParameter("prodNo");
		
		ProductService service=new ProductServiceImpl();
		ProductVO productVO=service.getProduct(Integer.parseInt(prodNo));
		
		request.setAttribute("productVO", productVO);		
		
		if(request.getParameter("menu").equals("manage")) {
			return "forward:/product/updateProduct.jsp";				
		}	
		
		return "forward:/product/getProduct.jsp";
	}
}