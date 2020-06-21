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
		List<Product> list = sqlSession.selectList("ProductMapper.getProductList", search);
		int totalProductCount = sqlSession.selectOne("ProductMapper.getCountProduct", search);	
		
		System.out.println("검색 조건 : "+search.getSearchCondition());
		System.out.println("검색 키워드 : "+search.getSearchKeyword());	
		System.out.println("현재 페이지: "+search.getCurrentPage());
		System.out.println("페이지 사이즈: "+search.getPageSize());		
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


	@Override
	public Product findProductName(String prodName) throws Exception {
		
		return sqlSession.selectOne("ProductMapper.getProductName", prodName);
	}

	@Override
	public int getLastProdno() throws Exception {
		
		return sqlSession.selectOne("ProductMapper.getLastProdNo");
	}

	@Override
	public List<String> getProductNameList(String prodName) throws Exception {
		List<String> list = sqlSession.selectList("ProductMapper.getProductNameList", prodName);
		return list;
	}
	

}
