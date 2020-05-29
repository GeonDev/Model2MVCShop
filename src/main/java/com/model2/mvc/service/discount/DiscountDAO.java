package com.model2.mvc.service.discount;

import java.util.List;

import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public interface DiscountDAO {

	public Discount	getDiscount(int discountNo) throws Exception;	
	
	public void addDiscount(Discount discount) throws Exception;
	
	public void deleteDiscount(int discountNo) throws Exception;
	
	public List<Discount> getDiscountList(String userId) throws Exception;
	
}
