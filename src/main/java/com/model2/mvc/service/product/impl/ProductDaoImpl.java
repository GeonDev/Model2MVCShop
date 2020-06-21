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
	
	
	// Ư����ǰ�� ã�� ��ȯ �Ѵ�.
	public Product findProduct(int productNo) throws SQLException {		
			
		return sqlSession.selectOne("ProductMapper.getProduct", productNo);
	} 	
	
	
	//��ǰ ����� ��ȯ�Ѵ�.
	public Map<String,Object> getProductList(Search search) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Product> list = sqlSession.selectList("ProductMapper.getProductList", search);
		int totalProductCount = sqlSession.selectOne("ProductMapper.getCountProduct", search);	
		
		System.out.println("�˻� ���� : "+search.getSearchCondition());
		System.out.println("�˻� Ű���� : "+search.getSearchKeyword());	
		System.out.println("���� ������: "+search.getCurrentPage());
		System.out.println("������ ������: "+search.getPageSize());		
		System.out.println("��ü ���Ǽ�:" + totalProductCount);		
		
		map.put("count", totalProductCount);		
		map.put("list", list);
		
		return map;		
	}
		
	
	//��ǰ������ �����Ѵ�.
	public void updateProduct(Product product) throws Exception {
		
		sqlSession.update("ProductMapper.updateProduct", product);
	}
	
	//��ǰ������ �߰��Ѵ�.
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
