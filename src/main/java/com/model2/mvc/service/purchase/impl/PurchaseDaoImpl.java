package com.model2.mvc.service.purchase.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.impl.ProductDaoImpl;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.user.UserDao;
import com.model2.mvc.service.user.impl.UserDaoImpl;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;
	
	//기본 생성자 명시	
	public PurchaseDaoImpl () {	}

	
	public Purchase findPurchase(int Tran_No) throws Exception {		
		
		return sqlSession.selectOne("PurchaseMapper.getPurchaseTransNo", Tran_No);
	}
	
	
	public Purchase findPurchase2(int ProdNo) throws Exception {
		
		return sqlSession.selectOne("PurchaseMapper.getPurchaseProdNo", ProdNo);
	}
	
	
	
	
	public void insertPurchase(Purchase purchase) throws Exception {
					
		sqlSession.insert("PurchaseMapper.insertPurchase", purchase);
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTransCode", purchase);
	}
	
	//구매 목록 - 특정 유저가 구매한 상품만 조회
	public Map<String,Object> getPurchaseList(Search search, String buyerId)  throws Exception {		
		
		Map<String,Object> map = new HashMap<String,Object>();
		search.setSearchKeyword(buyerId);		
		List<Purchase> list = 	sqlSession.selectList("PurchaseMapper.getCountPurchase", search);
		int totalProductCount = sqlSession.selectOne("PurchaseMapper.getCountPurchase", buyerId);	
	
		
		System.out.println("전체 주문수:" + totalProductCount);				
		/********************************************************/
		
		map.put("count", new Integer(totalProductCount));
		map.put("list", list);

		
		return map;
	}
	
	//판매 목록 - 전체 판매 상품 조회 (사용안함)
	public Map<String,Object> getSaleList(Search search) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
			
		List<Purchase> list = 	sqlSession.selectList("PurchaseMapper.getAllPurchaseList", search);
		int totalProductCount = sqlSession.selectOne("PurchaseMapper.getAllCountPurchase");	
	
		
		System.out.println("전체 주문수:" + totalProductCount);				
		/********************************************************/
		
		map.put("count", totalProductCount);
		map.put("list", list);

		
		return map;
		
	}		
}
	

