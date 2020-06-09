package com.model2.mvc.service.discount;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;

public interface DiscountService {

	public Discount	findDiscount(int discountNo) throws Exception;	
	
	public void addDiscount(Discount discount) throws Exception;
	
	public void deleteDiscount(int discountNo) throws Exception;
	
	public List<Discount> getDiscountList(String userId) throws Exception;	
	
	public Map<String, Object> getDiscountCouponList(Search search,String ownerId) throws Exception;
}
