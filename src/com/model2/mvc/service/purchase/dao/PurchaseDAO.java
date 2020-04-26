package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
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
		
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");		
		purchaseVO.setDivyDate(form.format(rs.getDate("DLYY_DATE")));
		
			
		
		con.close();
		return purchaseVO;
	}
	
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
					
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO TRANSACTION VALUES(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,'1',SYSDATE,?)";	
		PreparedStatement pStmt = con.prepareStatement(sql);
			
		System.out.println("���� ������ ��??");
		
		pStmt.setInt(1, (purchaseVO.getPurchaseProd()).getProdNo());
		pStmt.setString(2, (purchaseVO.getBuyer()).getUserId());
		pStmt.setString(3, purchaseVO.getPaymentOption());
		pStmt.setString(4, purchaseVO.getReceiverName());
		pStmt.setString(5,purchaseVO.getReceiverPhone());
		pStmt.setString(6, purchaseVO.getDivyAddr());
		pStmt.setString(7, purchaseVO.getDivyRequest());		
		pStmt.setString(8, purchaseVO.getDivyDate());
		
		pStmt.executeUpdate();
		con.close();
	}
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		Connection con = DBUtil.getConnection();
		int tranNO = purchaseVO.getTranNo();
		
		String sql = "UPDATE TRANSACTION SET PAYMENT_OPTION =?, RECEIVER_NAME=?, RECEIVER_PHONE =?, DLYY_ADDR =? DLYY_REQUSER = ?, DLYY_DATE =? WHERE TRAN_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchaseVO.getPaymentOption());
		pStmt.setString(2, purchaseVO.getReceiverName());
		pStmt.setString(3, purchaseVO.getReceiverPhone());
		pStmt.setString(4, purchaseVO.getDivyAddr());
		pStmt.setString(5, purchaseVO.getDivyRequest());	
		
		Date date = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(purchaseVO.getDivyDate());		
		pStmt.setDate(6, date);
		pStmt.setInt(7, tranNO);		
			
		pStmt.executeUpdate();
		
		con.close();		
	}
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		//tranCode =�Ǹ��� :0  ���ſϷ�: 1/ ����ϱ� :2/ ��۵��� : 3 		
		
		Connection con = DBUtil.getConnection();
		int prodNo = (purchaseVO.getPurchaseProd()).getProdNo();
		
		//���� ������ �޾Ƽ� ó��
		int temp = Integer.parseInt(purchaseVO.getTranCode());
		temp += 1;				
		temp%=3;
		
		String sql = "UPDATE TRANSACTION SET TRAN_STATUS_CODE = ? WHERE PROD_NO = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, String.valueOf(temp));
		pStmt.setInt(2, prodNo);
		
		pStmt.executeUpdate();
		con.close();		
	}
	
	//���� ��� - ������ ������ ��ǰ�� ��ȸ
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId)  throws Exception {
				
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		//��� ��ǰ�� �����Ѵ�.
		String sql = "select * from TRANSACTION, PRODUCT WHERE TRANSACTION.PROD_NO = PRODUCT.PROD_NO AND TRANSACTION.BUYER_ID = ?";		
		
		//searchVO�� ���͸� ������ ���� �ִٸ�
		if (searchVO.getSearchCondition() != null) {
			//��ǰ ��ȣ�� �������� ��ȸ
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " AND PRODUCT.PROD_NO=?";
			//��ǰ �̸��� �������� ��ȸ
			} else if (searchVO.getSearchCondition().equals("1")) {				
				sql += " AND PRODUCT.PROD_NAME=?";
			//��ǰ �������� ��ȸ
			}else if(searchVO.getSearchCondition().equals("2")) {
				sql += " AND PRODUCT.PRICE=?";
			}
		}
		sql += " order by PROD_NO";
		
		//TYPE_SCROLL_INSENSITIVE : Ŀ�� �̵��� ���������� ��ȭ�� ����, CONCUR_UPDATABLE �����͸� �����鼭 ������Ʈ ����
		PreparedStatement pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		
		//������ ������ �̸��� �ִ´�.
		pStmt.setString(1, buyerId);
		
		if(searchVO.getSearchKeyword()!=null) {
			pStmt.setString(2, searchVO.getSearchKeyword());
		}
			
		ResultSet rs = pStmt.executeQuery();
		
		//Ŀ���� ���� �Ʒ��� �̵� => �˻��� Raw ������ ���Ϸ���?
		rs.last();
		//�ִ� Row ������ ������ �д�.
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);
		
		map.put("count", new Integer(total));		
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		//searchVO.getPage() : ������ ������
		//searchVO.getPageUnit() : �������� ǥ�õǴ� ��
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		
		if(total>0) {
			
			for(int i =0; i<searchVO.getPageUnit(); i++) {
				PurchaseVO tempPurchase = new PurchaseVO();
				
				ProductVO tempProd = new ProductVO();
				tempProd.setProdNo(rs.getInt("PROD_NO"));
				tempProd.setProdName(rs.getString("PROD_NAME"));
				tempProd.setProdDetail(rs.getString("PROD_DETAIL"));
				tempProd.setManuDate(rs.getString("MANUFACTURE_DAY"));
				tempProd.setPrice(rs.getInt("PRICE"));		
				tempProd.setFileName(rs.getString("IMAGE_FILE"));
				tempProd.setRegDate(rs.getDate("REG_DATE"));
				
				tempPurchase.setPurchaseProd(tempProd);
				tempPurchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
								
				list.add(tempPurchase);					
				
					if (!rs.next()) {
						break;
					
				}
			}				
		}		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
				
		con.close();
		return map;
	}
	
	//�Ǹ� ��� - ��ü �Ǹ� ��ǰ ��ȸ
	public HashMap<String,Object> getSaleList(SearchVO searchVO) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		//��� ��ǰ�� �����Ѵ�.
		String sql = "select * from TRANSACTION, PRODUCT WHERE TRANSACTION.PROD_NO = PRODUCT.PROD_NO";		
		
		//searchVO�� ���͸� ������ ���� �ִٸ�
		if (searchVO.getSearchCondition() != null) {
			//��ǰ ��ȣ�� �������� ��ȸ
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " AND PRODUCT.PROD_NO=?";
			//��ǰ �̸��� �������� ��ȸ
			} else if (searchVO.getSearchCondition().equals("1")) {				
				sql += " AND PRODUCT.PROD_NAME=?";
			//��ǰ �������� ��ȸ
			}else if(searchVO.getSearchCondition().equals("2")) {
				sql += " AND PRODUCT.PRICE=?";
			}
		}
		sql += " order by PROD_NO";
		
		//TYPE_SCROLL_INSENSITIVE : Ŀ�� �̵��� ���������� ��ȭ�� ����, CONCUR_UPDATABLE �����͸� �����鼭 ������Ʈ ����
		PreparedStatement pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		
		if(searchVO.getSearchKeyword()!=null) {
			pStmt.setString(1, searchVO.getSearchKeyword());
		}
			
		ResultSet rs = pStmt.executeQuery();
		
		//Ŀ���� ���� �Ʒ��� �̵� => �˻��� Raw ������ ���Ϸ���?
		rs.last();
		//�ִ� Row ������ ������ �д�.
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);
		
		map.put("count", new Integer(total));		
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		//searchVO.getPage() : ������ ������
		//searchVO.getPageUnit() : �������� ǥ�õǴ� ��
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		
		if(total>0) {
			
			for(int i =0; i<searchVO.getPageUnit(); i++) {
				PurchaseVO tempPurchase = new PurchaseVO();
				
				ProductVO tempProd = new ProductVO();
				tempProd.setProdNo(rs.getInt("PROD_NO"));
				tempProd.setProdName(rs.getString("PROD_NAME"));
				tempProd.setProdDetail(rs.getString("PROD_DETAIL"));
				tempProd.setManuDate(rs.getString("MANUFACTURE_DAY"));
				tempProd.setPrice(rs.getInt("PRICE"));		
				tempProd.setFileName(rs.getString("IMAGE_FILE"));
				tempProd.setRegDate(rs.getDate("REG_DATE"));
				
				tempPurchase.setPurchaseProd(tempProd);
				tempPurchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
								
				list.add(tempPurchase);					
				
					if (!rs.next()) {
						break;
					
				}
			}				
		}		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
				
		con.close();
		return map;
	}
	
	
	
}
