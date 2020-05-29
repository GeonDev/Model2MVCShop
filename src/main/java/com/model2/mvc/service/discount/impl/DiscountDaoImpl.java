package com.model2.mvc.service.discount.impl;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.discount.DiscountDAO;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;


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
	public List<Discount> getDiscountList(String userId) throws Exception {
		List<Discount> list = sqlSession.selectList("DiscountMapper.getDiscountSimpleList", userId);

		return list;
	}

}
