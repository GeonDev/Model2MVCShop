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
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class ProdServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
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

		
		//==> API 확인
		Assert.assertEquals("testUserId", prod.getProdName());

	} 
	
	//@Test
	public void testGetProd() throws Exception {
		
		Product prod = prodService.getProduct(10000);
		
		//int 형으로 반환하게 했습니다.
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