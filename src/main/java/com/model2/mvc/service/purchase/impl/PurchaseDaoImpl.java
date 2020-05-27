package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDAO;


@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;
	
	//�⺻ ������ ���	
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
	
	//���� ��� - Ư�� ������ ������ ��ǰ�� ��ȸ
	public Map<String,Object> getPurchaseList(Search search, String buyerId)  throws Exception {		
		
		Map<String,Object> map = new HashMap<String,Object>();
		search.setSearchKeyword(buyerId);		
		List<Purchase> list = 	sqlSession.selectList("PurchaseMapper.getPurchaseList", search);
		
		int totalProductCount = sqlSession.selectOne("PurchaseMapper.getCountPurchase", buyerId);	
	
		
		System.out.println("��ü �ֹ���:" + totalProductCount);				
		/********************************************************/
		
		map.put("count", new Integer(totalProductCount));
		map.put("list", list);

		
		return map;
	}
	
	//�Ǹ� ��� - ��ü �Ǹ� ��ǰ ��ȸ (������)
	public Map<String,Object> getSaleList(Search search) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
			
		List<Purchase> list = 	sqlSession.selectList("PurchaseMapper.getAllPurchaseList");
		int totalProductCount = sqlSession.selectOne("PurchaseMapper.getAllCountPurchase");	
	
		
		System.out.println("��ü �ֹ���:" + totalProductCount);				
		/********************************************************/
		
		map.put("count", totalProductCount);
		map.put("list", list);

		
		return map;
		
	}		
}
	

