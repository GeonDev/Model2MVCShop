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
	
	// Ư����ǰ�� ã�� ��ȯ �Ѵ�.
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
		}
		
		con.close();
		
		return productVO;
	} 
	
	//��ǰ ����� ��ȯ�Ѵ�.
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		
		String sql = "select * from PRODUCT ";		
		
		//searchVO�� ���͸� �����Ѵٸ�, 
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				//��ǰ ��ȣ�� �������� ��ȸ
				sql += " where PROD_NO='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				//��ǰ �̸��� �������� ��ȸ
				sql += " where PROD_NAME='" + searchVO.getSearchKeyword()
						+ "'";
			}
		}
		sql += " order by PROD_NO";
		
		//TYPE_SCROLL_INSENSITIVE : Ŀ�� �̵��� ���������� ��ȭ�� ����, CONCUR_UPDATABLE �����͸� �����鼭 ������Ʈ ����
		PreparedStatement pStmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);		
		ResultSet rs = pStmt.executeQuery();
		
		//Ŀ���� ���� �Ʒ��� �̵� => �˻��� Raw ������ ���Ϸ���?
		rs.last();
		//�ִ� Row ������ ������ �д�.
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);
		
		map.put("count", new Integer(total));		
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		//searchVO.getPage() : ������ ������
		//searchVO.getPageUnit() : �������� ǥ�õǴ� ��
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		
		
		if(total>0) {
			ProductVO tempProd = new ProductVO();
			for(int i =0; i<searchVO.getPageUnit(); i++) {
				if(rs.next()) {
					tempProd.setProdNo(rs.getInt("PROD_NO"));
					tempProd.setProdName(rs.getString("PROD_NAME"));
					tempProd.setProdDetail(rs.getString("PROD_DETAIL"));
					tempProd.setManuDate(rs.getString("MANUFACTURE_DAY"));
					tempProd.setPrice(rs.getInt("PRICE"));		
					tempProd.setFileName(rs.getString("IMAGE_FILE"));
					tempProd.setRegDate(rs.getDate("REG_DATE"));
									
					list.add(tempProd);
				}
			}				
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
				
		con.close();
		return map;
		
	}
	
	
	//��ǰ������ �����Ѵ�.
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
	
	//��ǰ������ �߰��Ѵ�.
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO  PRODUCT VALUES(?,?,?,?,?,?,sysdate)";
		PreparedStatement pStmt = con.prepareStatement(sql);		
		pStmt.setInt(1, productVO.getProdNo());
		pStmt.setString(2, productVO.getProdName());
		pStmt.setString(3, productVO.getProdDetail());
		pStmt.setString(4, productVO.getManuDate());
		pStmt.setInt(5, productVO.getPrice());
		pStmt.setString(6, productVO.getFileName());
		
		
		
		pStmt.executeUpdate();		
		
		con.close();
	}
	
	
	
	

}
