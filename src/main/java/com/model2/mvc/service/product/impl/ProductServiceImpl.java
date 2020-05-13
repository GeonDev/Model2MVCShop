package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;



// �� ���̾�� Ŀ�ø� ���踦 ���̱� ���� �߰� �޼ҵ�
// ĸ��ȭ =>  ���� ����
//

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDAO;
	
	public ProductServiceImpl() {
		//productDAO = new ProductDaoImpl();
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
	
	//----------------------------------------------------------------------------//
	public Product getProductName(String prodName) throws Exception {
		return productDAO.findProductName(prodName);
	}
}