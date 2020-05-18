package com.model2.mvc.service.prod.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class ProdServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService prodService;

	//@Test
	public void testAddProd() throws Exception {
		
	
		Product prod = new Product();		
		
		prod.setProdName("testProd");
		prod.setProdDetail("test Script");
		prod.setPrice(1000);	
		prod.setManuDate("20200505");
		prod.setFileName("a7.jpg");
		
		prodService.addProduct(prod);
		
		prod = prodService.getProductName("testProd");

		
		//==> API Ȯ��
		Assert.assertEquals("testUserId", prod.getProdName());

	} 
	
	//@Test
	public void testGetProd() throws Exception {
		
		Product prod = prodService.getProduct(10000);
		
		//int ������ ��ȯ�ϰ� �߽��ϴ�.
		Assert.assertEquals(10000, prod.getProdNo());
		
	}
	
	//@Test
	public void updateProd() throws Exception{
		
		Product prod = new Product();
		
		prod.setProdName("test");
		prod.setProdDetail("test");
		prod.setManuDate("20200505");
		prod.setPrice(1000);
		prod.setFileName("A.jpg");
		
	}
	
	@Test
	public void getListAll() throws Exception{
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("a");
	 	search.setSearchOrder("1");
	 	
	 	Map<String,Object> map = prodService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	
	 	
	 	Integer totalCount = (Integer)map.get("count");
	 	System.out.println(totalCount);
	 	System.out.println(list);
	 	
	 	Assert.assertNotNull(list);
	 	System.out.println("==============================");
	 	
	 	
	}
}