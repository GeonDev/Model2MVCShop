package com.model2.mvc.service.coupon.impl;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.coupon.CouponDAO;
import com.model2.mvc.service.domain.Coupon;


@Repository("couponDaoImpl")
public class CouponDaoImpl implements CouponDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	

	
	public CouponDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}	
	
	@Override
	public Coupon getCoupon(int couponNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("CouponMapper.getCoupon", couponNo);
	}

	@Override
	public void insertCoupon(Coupon coupon) throws Exception {
		
		sqlSession.insert("CouponMapper.addCoupon", coupon);
	}

	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		sqlSession.update("CouponMapper.updateCoupon", coupon);
		
	}

	@Override
	public Map<String, Object> getCouponList(Search search) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Coupon> list = sqlSession.selectList("CouponMapper.getCouponList", search);
		int totalProductCount = sqlSession.selectOne("CouponMapper.getCouponCount", search);					

		System.out.println("현재 페이지: "+search.getCurrentPage());
		System.out.println("페이지 사이즈: "+search.getPageSize());		
		System.out.println("전체 물건수:" + totalProductCount);		
		
		map.put("count", totalProductCount);		
		map.put("list", list);
		
		return map;		
	}

	@Override
	public void DeleteCoupon(int couponNo) throws Exception {
		sqlSession.delete("CouponMapper.deleteCoupon", couponNo);
		
	}
	

}
