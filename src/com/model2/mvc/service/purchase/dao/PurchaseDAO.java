package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;


public class PurchaseDAO {

	
	public PurchaseVO findPurchase(int Tran_No) throws Exception {
		
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductDAO productDAO = new ProductDAO();
		UserDAO userDAO = new UserDAO();
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM TRANSACTION WHERE TRAN_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, Tran_No);
		
		ResultSet rs = pStmt.executeQuery();
		
		purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
		purchaseVO.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
		purchaseVO.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
		purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
		purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
		purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
		purchaseVO.setDivyAddr(rs.getString("DLYY_ADDR"));
		purchaseVO.setDivyRequest(rs.getString("DLYY_REQUEST"));
		purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
		purchaseVO.setOrderDate(rs.getDate("ORDER_DATE"));
		
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		purchaseVO.setDivyDate(form.format(rs.getDate("DLYY_DATE")));
		
			
		
		con.close();
		return purchaseVO;
	}
	
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		String sql = "INSERT into TRANSACTION VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";				
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
			
		
		pStmt.setInt(1, (purchaseVO.getPurchaseProd()).getProdNo());
		pStmt.setString(2, (purchaseVO.getBuyer()).getUserId());
		pStmt.setString(3, purchaseVO.getPaymentOption());
		pStmt.setString(4, purchaseVO.getReceiverName());
		pStmt.setString(5,purchaseVO.getReceiverPhone());
		pStmt.setString(6, purchaseVO.getDivyAddr());
		pStmt.setString(7, purchaseVO.getDivyRequest());
		pStmt.setString(8, purchaseVO.getTranCode());
		pStmt.setString(9, purchaseVO.getDivyDate());
		
		pStmt.executeUpdate();
		con.close();
	}
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		Connection con = DBUtil.getConnection();
		String sql = "UPDATE TRANSACTION SET   WHERE TRAN_NO = ?";
		
		
		
		
		con.close();
		
	}
	
	public void updateTranCode(PurchaseVO purchaseVO) {
		//tranCode =판매중 :0  구매완료: 1/ 배송하기 :2/ 배송도착 : 3
 		
		int temp = Integer.parseInt(purchaseVO.getTranCode());
		temp += 1;				
		temp%=3;
		purchaseVO.setTranCode(String.valueOf(temp));		
		
	}
	
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO){
		
		
		return null;
	}
	
	public HashMap<String,Object> getSaleList(SearchVO searchVO){
		
			
		return null;
	}
	
	
	
}
