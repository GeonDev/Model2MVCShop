package com.model2.mvc.web.couponInventory;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.coupon.CouponService;
import com.model2.mvc.service.domain.Coupon;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

@Controller
@RequestMapping("/inventory/*")
public class CouponInventoryController {

	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;	
	
	@Autowired
	@Qualifier("couponServiceImpl")
	private CouponService service;
	
	
	//디폴트 생성자
	public CouponInventoryController() {
		// TODO Auto-generated constructor stub
	}				
	
	
	@RequestMapping(value = "addCoupon", method = RequestMethod.POST)
	public ModelAndView addCoupon(@RequestParam("selectCoupon")String selectCoupon, @RequestParam("ownerId") String userId ) throws Exception {
		
		System.out.println("선택된 번호"+ selectCoupon );
		System.out.println("유저 정보"+ userId );
		
		return null;
		//return new ModelAndView("forward:/coupon/getCoupon.jsp");
	}
	

}
