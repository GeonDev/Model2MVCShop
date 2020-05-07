package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.Product;

import sun.nio.ch.SelChImpl;



public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	// 특정상품을 찾아 반환 한다.
	public Product findProduct(int productNo) throws SQLException {		
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT product.*, "  
		+ "NVL((SELECT transaction.tran_status_code FROM transaction WHERE transaction.prod_no = product.prod_no) ,'0') TRAN_CODE "
		+ "FROM product WHERE product.prod_no = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, productNo);
		
		ResultSet rs = pStmt.executeQuery();	
		
		Product product = new Product();
		
		if(rs.next()) {
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			product.setProTranCode(rs.getString("TRAN_CODE"));
		}		

		
		con.close();		
		
		return product;
	} 	
	
	
	//상품 목록을 반환한다.
	public Map<String,Object> getProductList(Search search) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		List<Product> list = new ArrayList<Product>();
		
		String sql = "SELECT COUNT(PROD_NO) FROM PRODUCT";		
	
		PreparedStatement pStmt = con.prepareStatement(sql);		
		ResultSet rs = pStmt.executeQuery();
		int totalProductCount = 0;
		
		if(rs.next()) {
		totalProductCount = rs.getInt(1);
		}
		
		System.out.println("전체 물건수:" + totalProductCount);		
		map.put("count", new Integer(totalProductCount));				
		
		/************************************************************************/
		
		System.out.println("검색 조건 : "+search.getSearchCondition());
		System.out.println("검색 키워드 : "+search.getSearchKeyword());
		System.out.println("검색 정렬 : "+search.getSearchOrder());
		System.out.println("현재 페이지: "+search.getCurrentPage());
		System.out.println("페이지 사이즈: "+search.getPageSize());
						
		sql = returnQarry(search);
		pStmt = con.prepareStatement(sql);		
		rs = pStmt.executeQuery();
				
		
		while (rs.next()) {			
			Product tempProd = new Product();
			tempProd.setProdNo(rs.getInt("PROD_NO"));
			tempProd.setProdName(rs.getString("PROD_NAME"));
			tempProd.setProdDetail(rs.getString("PROD_DETAIL"));
			tempProd.setManuDate(rs.getString("MANUFACTURE_DAY"));
			tempProd.setPrice(rs.getInt("PRICE"));		
			tempProd.setFileName(rs.getString("IMAGE_FILE"));
			tempProd.setRegDate(rs.getDate("REG_DATE"));
			tempProd.setProTranCode(rs.getString("TRAN_CODE"));				
								
			list.add(tempProd);				
		}
		
		System.out.println(list);
				
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();
		
		return map;		
	}
		
	
	//상품정보를 수정한다.
	public void updateProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update  PRODUCT set PROD_NAME=?, PROD_DETAIL=?, MANUFACTURE_DAV=?, PRICE=?, IMAGE_FILE = ?  where PROD_NO=?";	
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setInt(6, product.getProdNo());		
		
		pStmt.executeUpdate();
		
		con.close();
	}
	
	//상품정보를 추가한다.
	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

			
		String sql = "INSERT INTO PRODUCT VALUES(seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";		
	
		
		PreparedStatement pStmt = con.prepareStatement(sql);		
	
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		
		
		//캘린더 JSP에서 그냥 값을 받으면 - 때문에 길이 초과
		String manufactureDate = product.getManuDate();
		manufactureDate = manufactureDate.replaceAll("-", "");		
		manufactureDate.trim();		
		System.out.println("등록날짜값"+ manufactureDate);
		pStmt.setString(3, manufactureDate);
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());		
		
		pStmt.executeUpdate();		
		
		con.close();
	}
	
	
	private String returnQarry(Search search) {		
		
		String sql = "SELECT  *  FROM "
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY product.prod_no) num, product.*, "
				+ "NVL((SELECT tran_status_code FROM transaction WHERE transaction.prod_no = product.prod_no),'0') tran_code FROM product";	

		
		if(search.getSearchCondition()!=null) {			
			//상품 번호로 검색
			if (search.getSearchCondition().equals("0") && CommonUtil.checkNumber(search.getSearchKeyword())) {
				sql += " WHERE product.prod_no ='" + Integer.parseInt(search.getSearchKeyword())+ "'";
			//상품 이름을 기준으로 조회
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {				
				sql += " WHERE product.prod_name like '%" + search.getSearchKeyword() + "%'";
			//상품 가격으로 조회
			}else if(search.getSearchCondition().equals("2") && CommonUtil.checkNumber(search.getSearchKeyword())) {
				if(CommonUtil.checkNumber(search.getSearchKeywordOptional())) {
					sql += " WHERE product.price >='" + Integer.parseInt(search.getSearchKeyword()) + "'";
				}else {
					sql += " WHERE product.price BETWEEN '" + Integer.parseInt(search.getSearchKeyword()) + "' AND '" + Integer.parseInt(search.getSearchKeywordOptional()) +"'";
				}
			}			
		}	
		
		sql += ") WHERE num BETWEEN "+ (((search.getCurrentPage()-1)*search.getPageSize())+1)+ "AND " + search.getCurrentPage()*search.getPageSize();
		
		if(search.getSearchOrder() !=null) {			
			// 기본순(0)일때는 정렬을 하지 않습니다 
			if(search.getSearchOrder().equals("1")) {
				sql += "ORDER BY price ";
			}else if(search.getSearchOrder().equals("2")) {
				sql += "ORDER BY price DESC";
			}else if(search.getSearchOrder().equals("3")) {
				sql += "ORDER BY prod_no DESC";
			}
		}
		
		return sql;
		
	}
	

}
