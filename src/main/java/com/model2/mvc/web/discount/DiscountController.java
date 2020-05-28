package com.model2.mvc.web.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.coupon.CouponService;
import com.model2.mvc.service.discount.DiscountService;
import com.model2.mvc.service.domain.Coupon;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/discount/*")
public class DiscountController {

	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;	
	
	@Autowired
	@Qualifier("couponServiceImpl")
	private CouponService couponService;
	
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	

	@Autowired
	@Qualifier("discountServiceImpl")
	private DiscountService discountService;	
	
	
	
	//디폴트 생성자
	public DiscountController() {
		// TODO Auto-generated constructor stub
	}				
	
	
	@RequestMapping(value = "addDiscount", method = RequestMethod.POST)
	public ModelAndView addCoupon(@RequestParam("selectCoupon")int selectCoupon, @RequestParam("ownerId") String userId ) throws Exception {
		
		System.out.println("선택된 번호"+ selectCoupon );
		System.out.println("유저 정보"+ userId );
		
		User user = userService.getUser(userId);
		Coupon coupon = couponService.findCoupon(selectCoupon);
		
		Discount discount = new Discount();
		
		discount.setDiscountCoupon(coupon);
		discount.setOwner(user);
		
		discountService.addDiscount(discount);
		
		return new ModelAndView("forward:/discount/addDiscountView.jsp");		
	}
	

}
