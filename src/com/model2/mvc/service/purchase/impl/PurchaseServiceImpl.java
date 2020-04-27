package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;


// �� ���̾�� Ŀ�ø� ���踦 ���̱� ���� �߰� �޼ҵ�
// ĸ��ȭ =>  ���� ����

public class PurchaseServiceImpl implements PurchaseService{
	
	private PurchaseDAO purchaseDAO;

	//�⺻ ������ �߰�
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}	
	
	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {		
		purchaseDAO.insertPurchase(purchaseVO);	
	}

	@Override
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public PurchaseVO getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return  purchaseDAO.findPurchase(ProdNo);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(searchVO, buyerId);
	}

	@Override
	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.getSaleList(searchVO);
	}

	@Override
	public void updatePurcahse(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updatePurchase(purchaseVO);
		
	}

	@Override
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updateTranCode(purchaseVO);		
	}
	

}