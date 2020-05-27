package com.model2.mvc.service.coupon;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Coupon;


public interface CouponService {

	public Coupon findCoupon(int couponNo) throws Exception;	
	
	public void insertCoupon(Coupon coupon) throws Exception;
	
	public void updateCoupon(Coupon coupon) throws Exception;
	
	public void removeCoupon(int couponNo) throws  Exception;
	
	public Map<String,Object> getCouponList(Search search)  throws Exception;
	
	public List<Coupon> getSimpleCouponList() throws Exception;
	
}