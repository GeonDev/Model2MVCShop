package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.UserDao;
import com.model2.mvc.service.user.impl.UserDaoImpl;




public class PurchaseDAO {
	
	//기본 생성자 명시	
	public PurchaseDAO () {	}

	
	public Purchase findPurchase(int Tran_No) throws Exception {
		
		Purchase purchase = new Purchase();
		ProductDAO productDAO = new ProductDAO();
		UserDao userDAO = new UserDaoImpl();
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM TRANSACTION WHERE TRAN_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, Tran_No);
		
		ResultSet rs = pStmt.executeQuery();
		
		if(rs.next()) {
		
		purchase.setTranNo(rs.getInt("TRAN_NO"));
		purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
		purchase.setBuyer(userDAO.getUser(rs.getString("BUYER_ID")));
		purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
		purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
		purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
		purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
		purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
		purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
		purchase.setOrderDate(rs.getDate("ORDER_DATE"));		
		
		if(rs.getDate("DLVY_DATE") !=null) {
			String date = rs.getDate("DLVY_DATE").toString();
			purchase.setDivyDate(date);
		}else {
			purchase.setDivyDate(null);
		}
		
		}			
		
		con.close();
		return purchase;
	}
	
	
	public Purchase findPurchase2(int ProdNo) throws Exception {
		
		Purchase purchase = new Purchase();
		ProductDAO productDAO = new ProductDAO();
		UserDao userDAO = new UserDaoImpl();
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM TRANSACTION WHERE PROD_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, ProdNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		if(rs.next()) {
		
		purchase.setTranNo(rs.getInt("TRAN_NO"));
		purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
		purchase.setBuyer(userDAO.getUser(rs.getString("BUYER_ID")));
		purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
		purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
		purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
		purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
		purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
		purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
		purchase.setOrderDate(rs.getDate("ORDER_DATE"));
		
		SimpleDateFormat form = new SimpleDateFormat("YYYY-MM-DD");		
		purchase.setDivyDate(form.format(rs.getDate("DLVY_DATE")));
		}			
		
		con.close();
		return purchase;
	}
	
	
	
	
	public void insertPurchase(Purchase purchase) throws Exception {
					
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO TRANSACTION VALUES(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";	
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		
		pStmt.setInt(1, (purchase.getPurchaseProd()).getProdNo());
		pStmt.setString(2, (purchase.getBuyer()).getUserId());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5,purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, "1");
		pStmt.setString(9, purchase.getDivyDate());
		
		pStmt.executeUpdate();
		con.close();
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();
		int tranNO = purchase.getTranNo();
		
	
		String datetime = (purchase.getDivyDate()).trim();
		
		Date date = new Date((new SimpleDateFormat("YYYY-MM-DD").parse(datetime)).getTime());
		
		System.out.println("날짜형식보기 " + date);
		
		String sql = "UPDATE TRANSACTION SET PAYMENT_OPTION =?, RECEIVER_NAME=?, RECEIVER_PHONE =?, DLVY_ADDR =?, DLVY_REQUEST =?, DLVY_DATE =?  WHERE TRAN_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getPaymentOption());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getReceiverPhone());
		pStmt.setString(4, purchase.getDivyAddr());
		pStmt.setString(5, purchase.getDivyRequest());				
		pStmt.setDate(6, date);
		pStmt.setInt(7, tranNO);		
			
		pStmt.executeUpdate();
		
		con.close();		
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		//tranCode =판매중 :0  구매완료: 1/ 배송하기 :2/ 배송도착 : 3 		
		
		Connection con = DBUtil.getConnection();
		int prodNo = (purchase.getPurchaseProd()).getProdNo();
		
		String tranCode = purchase.getTranCode().trim();
		
		String sql = "UPDATE TRANSACTION SET TRAN_STATUS_CODE = ? WHERE PROD_NO = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, tranCode);		
		pStmt.setInt(2, prodNo);
		
		System.out.println(prodNo + " : " + tranCode);
		
		pStmt.executeUpdate();
		con.close();		
	}
	
	//구매 목록 - 특정 유저가 구매한 상품만 조회
	public Map<String,Object> getPurchaseList(Search search, String buyerId)  throws Exception {
		
		
		ProductDAO productDAO = new ProductDAO();
		UserDao userDAO = new UserDaoImpl();		
		
		Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		//특정 유저가 구매한 모든 상품을 선택한다.
		String sql ="SELECT COUNT(tran_no) FROM  transaction WHERE buyer_id = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, buyerId);		

		ResultSet rs = pStmt.executeQuery();
		
		int totalProductCount = 0;
		
		if(rs.next()) {
			totalProductCount = rs.getInt(1);
		}
		
		System.out.println("전체 주문수:" + totalProductCount);		
		map.put("count", new Integer(totalProductCount));
		
		System.out.println("현재 페이지: "+search.getCurrentPage());
		System.out.println("페이지 사이즈: "+search.getPageSize());
		
		
		/********************************************************/
		
		sql = "SELECT *  FROM (SELECT ROW_NUMBER() OVER(ORDER BY tran_no) num, transaction.* "
				+ "FROM transaction WHERE buyer_id = ?)";
		
		sql += "WHERE num BETWEEN ? AND ?";
		
		pStmt = con.prepareStatement(sql);	
		
		//구매한 유저의 이름을 넣는다.
		pStmt.setString(1, buyerId);		
		
		pStmt.setInt(2, ((search.getCurrentPage()-1)*search.getPageSize())+1);
		
		pStmt.setInt(3, (search.getCurrentPage()*search.getPageSize()));	
		
		rs = pStmt.executeQuery();
		
				

		
		while(rs.next()) {		
			Purchase purchase = new Purchase();
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userDAO.getUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATE"));
			
			if(rs.getDate("DLVY_DATE") !=null) {
			String date = CommonUtil.toDateStr(rs.getDate("DLVY_DATE"));
			purchase.setDivyDate(date);
			}else {
				purchase.setDivyDate(null);
			}	

		
			list.add(purchase);	
		}
		
	
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
		pStmt.close();		
		rs.close();
		con.close();
		return map;
	}
	
	//판매 목록 - 전체 판매 상품 조회
	public Map<String,Object> getSaleList(Search search) throws Exception{
		
		Purchase purchase = new Purchase();
		ProductDAO productDAO = new ProductDAO();
		UserDao userDAO = new UserDaoImpl();		
		
		Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		//유저들이 구매한 모든 상품을 선택한다.
		String sql ="SELECT COUNT(tran_no) FROM  transaction";
		PreparedStatement pStmt = con.prepareStatement(sql);		

		ResultSet rs = pStmt.executeQuery();
		
		int total = 0;
		
		if(rs.next()) {
			total = rs.getInt(1);
		}
		
		System.out.println("구매 횟수 :" + total);
		
		map.put("count", new Integer(total));
		
		
		/********************************************************/
		
		sql = "SELECT *  FROM (SELECT ROW_NUMBER() OVER(ORDER BY transaction.tran_no) num, transaction.* "
				+ "FROM transaction)";
		
		sql += "WHERE num BETWEEN ? AND ?";
		
		pStmt = con.prepareStatement(sql);	
		
		//구매한 유저의 이름을 넣는다.		
		pStmt.setInt(1, (search.getCurrentPage()-1)*search.getPageSize()+1);
		
		pStmt.setInt(2, (search.getCurrentPage()*search.getPageSize()));	
		
		rs = pStmt.executeQuery();
		
		ArrayList<Purchase> list = new ArrayList<Purchase>();		

		
		if(rs.next()) {			
		purchase.setTranNo(rs.getInt("TRAN_NO"));
		purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
		purchase.setBuyer(userDAO.getUser(rs.getString("BUYER_ID")));
		purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
		purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
		purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
		purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
		purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
		purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
		purchase.setOrderDate(rs.getDate("ORDER_DATE"));
		
			if(rs.getDate("DLVY_DATE") !=null) {
			String date = CommonUtil.toDateStr(rs.getDate("DLVY_DATE"));
			purchase.setDivyDate(date);
			}else {
				purchase.setDivyDate(null);
			}
		}
	
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
				
		pStmt.close();		
		rs.close();
		con.close();	
		
		return map;
	}		
}
	

