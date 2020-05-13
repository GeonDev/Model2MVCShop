package com.model2.mvc.service.product;

import java.sql.SQLException;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {

	public Product findProduct(int productNo) throws Exception;	
	
	public Product findProductName(String prodName) throws Exception;	
	
	public Map<String,Object> getProductList(Search search) throws Exception;	
	
	public void updateProduct(Product product) throws Exception;
	
	public void insertProduct(Product product) throws Exception;
	
	
	
}
