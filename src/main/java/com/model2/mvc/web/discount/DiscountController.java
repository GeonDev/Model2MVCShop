package com.model2.mvc.web.discount;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
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
		return new ModelAndView("forward:/common/alertView.jsp", "message", "쿠폰이 지급되었습니다.");				
	}
	
	
	@RequestMapping(value = "deleteDiscount")
	public ModelAndView deleteDiscount(@RequestParam("discountNo")int discountNo) throws Exception {
		System.out.println("선택된 쿠폰"+ discountNo );
		
		
		discountService.deleteDiscount(discountNo);
		
		
		return new ModelAndView("forward:/common/alertView.jsp", "message", "쿠폰이 삭제되었습니다.");	
	}
	
	
	
	@RequestMapping("DiscountList")
	public ModelAndView listDiscount(@ModelAttribute("search") Search search, HttpSession session) throws Exception {
		
		//현재 페이지값이 없으면 첫번째 페이지로 설정
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		User owner = (User)session.getAttribute("user");
		
		Map<String , Object> map = discountService.getDiscountCouponList(search, owner.getUserId());
		
				
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		// Model 과 View 연결
		modelAndView.setViewName("forward:/coupon/listDiscount.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		
		return modelAndView;
	}
	
		
	@RequestMapping(value = "listDiscountHistory", method = RequestMethod.POST)
	public ModelAndView listDiscountHistory(@ModelAttribute("search") Search search) throws Exception {
		
		//현재 페이지값이 없으면 첫번째 페이지로 설정
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		
		//Condition, order 가없을 경우 기본 값 세팅
		if(CommonUtil.null2str(search.getSearchCondition()) == "") {
			search.setSearchCondition("0");
		}
		
		if(CommonUtil.null2str(search.getSearchOrder()) == "") {
			search.setSearchOrder("0");			
		}			
		
		search.setPageSize(pageSize);
		
		Map<String , Object> map = discountService.getDiscountCouponList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);		
		
		ModelAndView modelAndView = new ModelAndView();		
		
		// Model 과 View 연결
		modelAndView.setViewName("forward:/coupon/listDiscountHistory.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "listDiscountHistory" ,method = RequestMethod.GET)
	public ModelAndView listDiscountHistory(@RequestParam("couponNo") int couponNo) throws Exception {
		
		Search search = new Search();		

		search.setCurrentPage(1);	
		search.setSearchCondition("0");	
		search.setSearchOrder("0");			
		search.setPageSize(pageSize);
		search.setSearchKeyword(Integer.toString(couponNo));
		
		Map<String , Object> map = discountService.getDiscountCouponList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);		
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		// Model 과 View 연결
		modelAndView.setViewName("forward:/coupon/listDiscountHistory.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		
		return modelAndView;
	}
	
	

}
