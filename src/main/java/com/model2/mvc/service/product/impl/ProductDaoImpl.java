package com.model2.mvc.service.product.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;




@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	

	
	public ProductDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	// 특정상품을 찾아 반환 한다.
	public Product findProduct(int productNo) throws SQLException {		
			
		return sqlSession.selectOne("ProductMapper.getProduct", productNo);
	} 	
	
	
	//상품 목록을 반환한다.
	public Map<String,Object> getProductList(Search search) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Product> list = new ArrayList<Product>();
	
		
		System.out.println("검색 조건 : "+search.getSearchCondition());
		System.out.println("검색 키워드 : "+search.getSearchKeyword());	
		System.out.println("현재 페이지: "+search.getCurrentPage());
		System.out.println("페이지 사이즈: "+search.getPageSize());
		
		
		int totalProductCount = sqlSession.selectOne("ProductMapper.getCountProduct", search) ;
		list = sqlSession.selectList("ProductMapper.getProductList", search);
		
		System.out.println("전체 물건수:" + totalProductCount);		
		
		map.put("count", totalProductCount);		
		map.put("list", list);
		
		return map;		
	}
		
	
	//상품정보를 수정한다.
	public void updateProduct(Product product) throws Exception {
		
		sqlSession.update("ProductMapper.updateProduct", product);
	}
	
	//상품정보를 추가한다.
	public void insertProduct(Product product) throws Exception {
		
		sqlSession.insert("ProductMapper.insertProduct", product);
		
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
			}else if(search.getSearchCondition().equals("2")) {
				if(CommonUtil.checkNumber(search.getSearchKeyword()) && !CommonUtil.checkNumber(search.getSearchKeywordOptional())) {
					sql += " WHERE product.price >='" + Integer.parseInt(search.getSearchKeyword()) + "'";
				}else if(!CommonUtil.checkNumber(search.getSearchKeyword()) && CommonUtil.checkNumber(search.getSearchKeywordOptional())) {
					sql += " WHERE product.price <='" + Integer.parseInt(search.getSearchKeywordOptional()) + "'";
					
				}else if(CommonUtil.checkNumber(search.getSearchKeyword()) && CommonUtil.checkNumber(search.getSearchKeywordOptional()) ) {
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

	@Override
	public Product findProductName(String prodName) throws Exception {
		
		return sqlSession.selectOne("ProductMapper.getProductName", prodName);
	}
	

}
