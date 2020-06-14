package com.model2.mvc.service.discount.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.discount.DiscountDAO;
import com.model2.mvc.service.domain.Discount;


@Repository("discountDaoImpl")
public class DiscountDaoImpl implements DiscountDAO{

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
		
	
	public DiscountDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}	

	@Override
	public Discount getDiscount(int discountNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("DiscountMapper.getDiscount", discountNo);
	}
	

	@Override
	public void addDiscount(Discount discount) throws Exception {
	
		sqlSession.insert("DiscountMapper.addDiscount", discount);
		
	}

	@Override
	public void deleteDiscount(int discountNo) throws Exception {
		
		sqlSession.delete("DiscountMapper.deleteDiscount", discountNo);
	}

	@Override
	public List<Discount> getDiscountSimpleList(Map<String, Object> value) throws Exception {
		List<Discount> list = sqlSession.selectList("DiscountMapper.getDiscountSimpleList", value);

		return list;
	}

	@Override
	public Map<String, Object> getDiscountCouponList(Search search, String ownerId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		search.setSearchKeyword(ownerId);
		
		List<Discount> list = 	sqlSession.selectList("DiscountMapper.getDiscountList", search);		
		int totalProductCount = sqlSession.selectOne("DiscountMapper.getCountDiscount", ownerId);
		
		map.put("count", totalProductCount);
		map.put("list", list);		
		
		return map;
	}

	@Override
	public void useDiscount(Map<String, Object> value) throws Exception {
		sqlSession.update("DiscountMapper.useDiscount",value);		
		
	}

	@Override
	public Map<String, Object> getDiscountCouponList(Search search) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<Discount> list = 	sqlSession.selectList("DiscountMapper.getDiscountListHistory", search);
		int totalProductCount = sqlSession.selectOne("DiscountMapper.getCountDiscountHistory", search);
		
		map.put("count", totalProductCount);
		map.put("list", list);
		return map;
	}

}
