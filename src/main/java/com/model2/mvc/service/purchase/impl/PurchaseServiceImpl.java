package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;



// �� ���̾�� Ŀ�ø� ���踦 ���̱� ���� �߰� �޼ҵ�
// ĸ��ȭ =>  ���� ����

public class PurchaseServiceImpl implements PurchaseService{
	
	private PurchaseDaoImpl purchaseDAO;

	//�⺻ ������ �߰�
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDaoImpl();
	}	
	
	@Override
	public void addPurchase(Purchase purchase) throws Exception {		
		purchaseDAO.insertPurchase(purchase);	
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return  purchaseDAO.findPurchase(ProdNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(search, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.getSaleList(search);
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
		
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);		
	}
	

}