package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;



public class PurchaseDAO {
	
	//기본 생성자 명시	
	public PurchaseDAO () {	}

	
	public Purchase findPurchase(int Tran_No) throws Exception {
		
		Purchase purchase = new Purchase();
		ProductDAO productDAO = new ProductDAO();
		UserDao userDAO = new UserDao();
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM TRANSACTION WHERE TRAN_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, Tran_No);
		
		ResultSet rs = pStmt.executeQuery();
		
		if(rs.next()) {
		
		purchase.setTranNo(rs.getInt("TRAN_NO"));
		purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
		purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
		purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
		purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
		purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
		purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
		purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
		purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
		purchase.setOrderDate(rs.getDate("ORDER_DATE"));		
		
		String date = rs.getDate("DLVY_DATE").toString();
		purchase.setDivyDate(date);
		}			
		
		con.close();
		return purchase;
	}
	
	
	public Purchase findPurchase2(int ProdNo) throws Exception {
		
		Purchase purchase = new Purchase();
		ProductDAO productDAO = new ProductDAO();
		UserDao userDAO = new UserDao();
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM TRANSACTION WHERE PROD_NO = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, ProdNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		if(rs.next()) {
		
		purchase.setTranNo(rs.getInt("TRAN_NO"));
		purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
		purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
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
	public HashMap<String,Object> getPurchaseList(Search search, String buyerId)  throws Exception {
				
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		//특정 유저가 구매한 모든 상품을 선택한다.
		String sql = "select TRAN_NO, BUYER_ID, RECEIVER_NAME, RECEIVER_PHONE, TRAN_STATUS_CODE from TRANSACTION WHERE BUYER_ID = ?"
		+ " order by TRAN_NO";
		
		//TYPE_SCROLL_INSENSITIVE : 커서 이동을 가능하지만 변화는 없음, CONCUR_UPDATABLE 데이터를 읽으면서 업데이트 가능
		PreparedStatement pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		
		//구매한 유저의 이름을 넣는다.
		pStmt.setString(1, buyerId);		

			
		ResultSet rs = pStmt.executeQuery();
		
		//커서를 가장 아래로 이동 => 검색된 Raw 개수를 구하려고?
		rs.last();
		//최대 Row 개수를 저장해 둔다.
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		map.put("count", new Integer(total));		
		
		ArrayList<Purchase> list = new ArrayList<Purchase>();		

		rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize()+1);
		
		
		if(total>0) {
			
			for(int i =0; i<search.getPageSize(); i++) {
				Purchase tempPurchase = new Purchase();				
				User buyer = new User();
				buyer.setUserId(rs.getString("BUYER_ID"));				
				tempPurchase.setBuyer(buyer);
				
				tempPurchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				tempPurchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				tempPurchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				tempPurchase.setTranNo(rs.getInt("TRAN_NO"));
								
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
	
	//판매 목록 - 전체 판매 상품 조회
	public HashMap<String,Object> getSaleList(Search search) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		//모든 상품을 선택한다.
		String sql = "select * from TRANSACTION, PRODUCT WHERE TRANSACTION.PROD_NO = PRODUCT.PROD_NO";		
		
		//search로 필터를 지정한 값이 있다면
		if (search.getSearchCondition() != null) {
			//상품 번호를 기준으로 조회
			if (search.getSearchCondition().equals("0")) {
				sql += " AND PRODUCT.PROD_NO=?";
			//상품 이름을 기준으로 조회
			} else if (search.getSearchCondition().equals("1")) {				
				sql += " AND PRODUCT.PROD_NAME=?";
			//상품 가격으로 조회
			}else if(search.getSearchCondition().equals("2")) {
				sql += " AND PRODUCT.PRICE=?";
			}
		}
		sql += " order by PROD_NO";
		
		//TYPE_SCROLL_INSENSITIVE : 커서 이동을 가능하지만 변화는 없음, CONCUR_UPDATABLE 데이터를 읽으면서 업데이트 가능
		PreparedStatement pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		
		if(search.getSearchKeyword()!=null) {
			pStmt.setString(1, search.getSearchKeyword());
		}
			
		ResultSet rs = pStmt.executeQuery();
		
		//커서를 가장 아래로 이동 => 검색된 Raw 개수를 구하려고?
		rs.last();
		//최대 Row 개수를 저장해 둔다.
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		map.put("count", new Integer(total));		
		
		ArrayList<Purchase> list = new ArrayList<Purchase>();
		
		//search.getPage() : 선택한 페이지
		//search.getPageUnit() : 페이지당 표시되는 수
		rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize()+1);
		
		
		if(total>0) {
			
			for(int i =0; i<search.getPageSize(); i++) {
				Purchase tempPurchase = new Purchase();
				
				Product tempProd = new Product();
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
