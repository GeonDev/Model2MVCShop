package com.model2.mvc.service.discount.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.discount.DiscountDAO;
import com.model2.mvc.service.discount.DiscountService;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;


@Service("discountServiceImpl")
public class DiscountServiceImpl implements DiscountService{
	
	@Autowired
	@Qualifier("discountDaoImpl")
	private DiscountDAO discountDAO;
	

	public DiscountServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Discount findDiscount(int discountNo) throws Exception {
		// TODO Auto-generated method stub
		return discountDAO.getDiscount(discountNo);
	}

	@Override
	public void addDiscount(Discount discount) throws Exception {
		discountDAO.addDiscount(discount);
		
	}

	@Override
	public void deleteDiscount(int discountNo) throws Exception {
		discountDAO.deleteDiscount(discountNo);

	}

	@Override
	public List<Discount> getDiscountList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return discountDAO.getDiscountList(userId);
	}

	@Override
	public Map<String, Object> getDiscountCouponList(Search search, String ownerId) throws Exception {
		// TODO Auto-generated method stub	
		
		return discountDAO.getDiscountCouponList(search, ownerId);
	}

}
