package com.model2.mvc.web.coupon;

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
@RequestMapping("/coupon/*")
public class CouponController {

	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;	
	
	@Autowired
	@Qualifier("couponServiceImpl")
	private CouponService service;
	
	
	//����Ʈ ������
	public CouponController() {
		// TODO Auto-generated constructor stub
	}	
	
	

	@RequestMapping("getCoupon")
	public ModelAndView getCoupon(@RequestParam("couponNo")int couponNo) throws Exception {
		
		Coupon coupon = service.findCoupon(couponNo);
		
		return new ModelAndView("forward:/coupon/getCoupon.jsp","coupon",coupon);
	}
	
	
	
	@RequestMapping(value = "addCoupon", method = RequestMethod.POST)
	public ModelAndView addCoupon(@ModelAttribute("coupon") Coupon coupon) throws Exception {
		
		service.insertCoupon(coupon);
		
		
		return new ModelAndView("forward:/coupon/getCoupon.jsp");
	}
	
	
	
	
	@RequestMapping("listCoupon")
	public ModelAndView listCoupon(@ModelAttribute("search") Search search) throws Exception {
		
		//���� ���������� ������ ù��° �������� ����
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		
		Map<String , Object> map=service.getCouponList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		// Model �� View ����
		modelAndView.setViewName("forward:/coupon/listCoupon.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		
		return modelAndView;
	}
	
	// ���� ������ ������Ʈ �ϰ� ����� ������(�ϴ� ����Ʈ�� ����)
	@RequestMapping(value = "updateCoupon", method = RequestMethod.POST)
	public ModelAndView updateCoupon(@ModelAttribute("coupon") Coupon coupon) throws Exception {
		
		service.updateCoupon(coupon);
		
		coupon = service.findCoupon(coupon.getCouponNo());
		
		return new ModelAndView("forward:/coupon/getCoupon.jsp","coupon",coupon);
	}
	
	
	// ����Ʈ���� ���� ���� ����
 	@RequestMapping(value = "updateCoupon", method = RequestMethod.GET)
	public ModelAndView updateCouponView(@RequestParam("couponNo")int couponNo) throws Exception {
		
			Coupon coupon = service.findCoupon(couponNo);
		
		return new ModelAndView("forward:/coupon/updateCoupon.jsp","coupon",coupon);
	}
	
	// ����Ʈ���� ������ ����
 	@RequestMapping(value = "deleteCoupon")
	public ModelAndView deleteCouponView(@RequestParam("couponNo")int couponNo) throws Exception {
		
 		//���� ���� ����
		service.removeCoupon(couponNo);			
		
		return new ModelAndView("forward:/common/alertView.jsp", "message", "������ �����Ǿ����ϴ�.");
	}	

}
