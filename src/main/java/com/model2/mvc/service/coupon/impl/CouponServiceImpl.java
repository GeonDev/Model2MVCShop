package com.model2.mvc.service.coupon.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.coupon.CouponDAO;
import com.model2.mvc.service.coupon.CouponService;
import com.model2.mvc.service.domain.Coupon;


// �� ���̾�� Ŀ�ø� ���踦 ���̱� ���� �߰� �޼ҵ�
// ĸ��ȭ =>  ���� ����
@Service("couponServiceImpl")
public class CouponServiceImpl implements CouponService{
	
	@Autowired
	@Qualifier("couponDaoImpl")
	private CouponDAO couponDAO;
	
	public CouponServiceImpl() {
		//productDAO = new ProductDaoImpl();
	}

	@Override
	public Coupon findCoupon(int couponNo) throws Exception {
		// TODO Auto-generated method stub
		return couponDAO.getCoupon(couponNo);
	}

	@Override
	public void insertCoupon(Coupon coupon) throws Exception {
		couponDAO.insertCoupon(coupon);
		
	}

	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		couponDAO.updateCoupon(coupon);
		
	}

	@Override
	public void removeCoupon(int couponNo) throws Exception {
		couponDAO.DeleteCoupon(couponNo);
		
	}

	@Override
	public Map<String, Object> getCouponList(Search search) throws Exception {
		
		return couponDAO.getCouponList(search);
	}
}