package com.model2.mvc.service.purchase.test;

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
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService service;


	
	//@Test
	public void testGetProd() throws Exception {
		
		Purchase purchase = service.getPurchase(10000);		
		
		System.out.println("★ 검색값 : "+ purchase);

		Assert.assertEquals("손건", purchase.getReceiverName());
		Assert.assertEquals("1", purchase.getPaymentOption());
		
	}
	
	//@Test
	public void updateProd() throws Exception{
		
		User buyer = new User();
		buyer.setUserId("10000");
		
		Product prod = new Product();
		prod.setProdNo(10000);
		
		Purchase purchase = new Purchase();
		purchase.setPaymentOption("1");
		purchase.setBuyer(buyer);
		purchase.setPurchaseProd(prod);
		purchase.setTranNo(10000);
		
		service.updatePurcahse(purchase);
		
		purchase = service.getPurchase(10000);		
		
		System.out.println("★ 검색값 : "+ purchase);

		Assert.assertEquals("손건", purchase.getReceiverName());		
	}
	
	@Test
	public void getListAll() throws Exception{
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);	 
	 	search.setSearchKeyword("user01");
	 	
	 	
	 	Map<String,Object> map = service.getSaleList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	
	 	
	 	int totalCount = (int)map.get("count");
	 	System.out.println(totalCount);
	 	//System.out.println("★선택된 구매★"+ list);
	 	
	 	Assert.assertEquals(totalCount, 3);
	 	//Assert.assertNotNull(list);
	 	System.out.println("==============================");
	 	
	 	
	}
}