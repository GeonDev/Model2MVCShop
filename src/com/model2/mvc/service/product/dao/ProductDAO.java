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


public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	// 특정상품을 찾아 반환 한다.
	public ProductVO findProduct(int productNo) throws SQLException {		
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM PRODUCT WHERE PROD_NO = ?";
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
			productVO.setProTranCode("0");
		}		
		
		sql = "SELECT TRAN_STATUS_CODE FROM TRANSACTION WHERE PROD_NO = ?";
		pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, productNo);
		
		
		if(rs.next()) {
			productVO.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
		}
		
		
		con.close();		
		
		return productVO;
	} 
	
	//상품 목록을 반환한다.
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		
		String sql = "select PROD_NO, TRAN_STATUS_CODE from TRANSACTION";
		
		ProductVO tempTransCode = new ProductVO();		
		
		ArrayList<ProductVO> TransCodelist = new ArrayList<ProductVO>();
		
		PreparedStatement pStmt = con.prepareStatement(sql);		
		ResultSet rs = pStmt.executeQuery();
		
		while (rs.next()) {			
			tempTransCode.setProdNo(rs.getInt("PROD_NO"));
			tempTransCode.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
			TransCodelist.add(tempTransCode);
		}	
		
		
		//모든 상품을 선택한다.
		sql = "select * from PRODUCT ";		
		
		//searchVO로 필터를 지정한다면, 
		if (searchVO.getSearchCondition() != null) {
			//상품 번호를 기준으로 조회
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO='" + searchVO.getSearchKeyword()	+ "'";
			//상품 이름을 기준으로 조회
			} else if (searchVO.getSearchCondition().equals("1")) {				
				sql += " where PROD_NAME='" + searchVO.getSearchKeyword() + "'";
			//상품 가격으로 조회
			}else if(searchVO.getSearchCondition().equals("2")) {
				sql += " where PRICE='" + searchVO.getSearchKeyword() + "'";
			}
		}
		sql += " order by PROD_NO";
		
		//TYPE_SCROLL_INSENSITIVE : 커서 이동을 가능하지만 변화는 없음, CONCUR_UPDATABLE 데이터를 읽으면서 업데이트 가능
		pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);		
		rs = pStmt.executeQuery();
		
		//커서를 가장 아래로 이동 => 검색된 Raw 개수를 구하려고?
		rs.last();
		//최대 Row 개수를 저장해 둔다.
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		map.put("count", new Integer(total));		
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		//searchVO.getPage() : 선택한 페이지
		//searchVO.getPageUnit() : 페이지당 표시되는 수
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		
		if(total>0) {
			
			for(int i =0; i<searchVO.getPageUnit(); i++) {
				ProductVO tempProd = new ProductVO();
				tempProd.setProdNo(rs.getInt("PROD_NO"));
				tempProd.setProdName(rs.getString("PROD_NAME"));
				tempProd.setProdDetail(rs.getString("PROD_DETAIL"));
				tempProd.setManuDate(rs.getString("MANUFACTURE_DAY"));
				tempProd.setPrice(rs.getInt("PRICE"));		
				tempProd.setFileName(rs.getString("IMAGE_FILE"));
				tempProd.setRegDate(rs.getDate("REG_DATE"));
				tempProd.setProTranCode("0");
				
				//일단 구매 상품이 있다면 검색
				if(TransCodelist.size() !=0) {
					for(ProductVO temp : TransCodelist) {
						if(tempProd.getProdNo() == temp.getProdNo()) {
							tempProd.setProTranCode(temp.getProTranCode());						
						}
					}
				}								
				list.add(tempProd);					
				
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

			
		String sql = "INSERT INTO PRODUCT VALUES(seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";		
	
		
		PreparedStatement pStmt = con.prepareStatement(sql);		
	
		pStmt.setString(1, productVO.getProdName());
		pStmt.setString(2, productVO.getProdDetail());
		
		
		//캘린더 JSP에서 그냥 값을 받으면 - 때문에 길이 초과
		String manufactureDate = productVO.getManuDate();
		manufactureDate = manufactureDate.replaceAll("-", "");		
		manufactureDate.trim();		
		System.out.println("등록날짜값"+ manufactureDate);
		pStmt.setString(3, manufactureDate);
		pStmt.setInt(4, productVO.getPrice());
		pStmt.setString(5, productVO.getFileName());		
		
		pStmt.executeUpdate();		
		
		con.close();
	}
	

}
