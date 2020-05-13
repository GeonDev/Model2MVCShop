package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;



// 두 레이어간에 커플링 관계를 줄이기 위한 중간 메소드
// 캡술화 =>  절차 은닉
//

public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}

	public void addProduct(Product productVO) throws Exception {
		productDAO.insertProduct(productVO);
	}

	public Product getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		return productDAO.getProductList(search);
	}

	public void updateProduct(Product product) throws Exception {
		productDAO.updateProduct(product);
	}
}