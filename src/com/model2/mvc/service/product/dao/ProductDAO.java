package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;
import com.sun.org.apache.regexp.internal.recompile;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	// 특정상품을 찾아 반환 한다.
	public ProductVO findProduct(int productNo) throws SQLException {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM PRODUCT WHRER PROD_NO = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, productNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		
		ProductVO productVO = new ProductVO();
		
		if(rs.next()) {
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));			
		}
		
		con.close();
		
		return productVO;
	} 
	
	//상품 목록을 반환한다.
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		
		String sql = "select * from PRODUCT ";		
		
		//searchVO로 필터를 지정한다면, 
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				//상품 번호를 기준으로 조회
				sql += " where PROD_NO='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				//상품 이름을 기준으로 조회
				sql += " where PROD_NAME='" + searchVO.getSearchKeyword()
						+ "'";
			}
		}
		sql += " order by PROD_NO";
		
		//TYPE_SCROLL_INSENSITIVE : 커서 이동을 가능하지만 변화는 없음, CONCUR_UPDATABLE 데이터를 읽으면서 업데이트 가능
		PreparedStatement pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);		
		ResultSet rs = pStmt.executeQuery();
		
		//커서를 가장 아래로 이동 => 검색된 Raw 개수를 구하려고?
		rs.last();
		//최대 Row 개수를 저장해 둔다.
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		map.put("count", new Integer(total));
		
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
				
		
		return map;
		
	}
	
	
	//상품정보를 수정한다.
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update  PRODUCT set PROD_NAME=?, PROD_DETAIL=?, MANUFACTURE_DAV=?, PRICE=?, IMAGE_FILE = ?  where PROD_NO=?";	
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, productVO.getProdName());
		pStmt.setString(2, productVO.getProdDetail());
		pStmt.setString(3, productVO.getManuDate());
		pStmt.setInt(4, productVO.getPrice());
		pStmt.setString(5, productVO.getFileName());
		pStmt.setInt(6, productVO.getProdNo());		
		
		pStmt.executeUpdate();
		
		con.close();
	}
	
	//상품정보를 추가한다.
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO  PRODUCT VALUES(?,?,?,?,?,?,?)";
		PreparedStatement pStmt = con.prepareStatement(sql);		
		pStmt.setInt(1, productVO.getProdNo());
		pStmt.setString(2, productVO.getProdName());
		pStmt.setString(3, productVO.getProdDetail());
		pStmt.setString(4, productVO.getManuDate());
		pStmt.setInt(5, productVO.getPrice());
		pStmt.setString(6, productVO.getFileName());
		pStmt.setDate(7, productVO.getRegDate());
		
		pStmt.executeUpdate();		
		
		con.close();
	}
	
	
	
	

}
