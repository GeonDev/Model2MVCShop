package com.model2.mvc.web.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.discount.DiscountService;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.DiscountPrice;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;


@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService service;
	
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService prodService;
	
	
	@Autowired
	@Qualifier("discountServiceImpl")
	private DiscountService discountService;
		
	
	
	public PurchaseRestController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/discountPrice" ,method=RequestMethod.POST)
	public DiscountPrice calculatePrice(@RequestBody DiscountPrice result) throws Exception {
		Discount discount = discountService.findDiscount(result.getDiscountNo());
		
		if(discount != null) {			
			int discountPrice = (int)Math.floor(result.getPrice()*(discount.getDiscountCoupon().getDiscountRatio()/100.0));
			
			if(discountPrice > discount.getDiscountCoupon().getMaximumDiscount()) {
				
				result.setPrice(result.getPrice() - discount.getDiscountCoupon().getMaximumDiscount());
			
			}else {
				result.setPrice(result.getPrice() - discountPrice);
			}			
		}
		
		System.out.println("할인 가격 Return : "+result);
		return result;
	}
	
	@RequestMapping(value = "json/getPurchase/{prodNo}")
	public Purchase getPurcahse(@PathVariable int pridNo) throws Exception {
				
		return service.getPurchase2(pridNo);
	}
	
	
	
	
	
}