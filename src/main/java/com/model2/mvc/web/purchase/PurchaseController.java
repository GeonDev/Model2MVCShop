package com.model2.mvc.web.purchase;

import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.discount.DiscountService;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService service;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService prodService;

	@Autowired
	@Qualifier("discountServiceImpl")
	private DiscountService discountService;

	// 디폴트 생성자
	public PurchaseController() {
		// TODO Auto-generated constructor stub
	}

	// getProduct.jsp에서 넘어감
	@RequestMapping(value = "addPurchase", method = RequestMethod.GET)
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo, Model model, HttpSession session)
			throws Exception {

		Product product = prodService.getProduct(prodNo);

		///////////////////////////////////// 해당 유저가 보유한 쿠폰정보
		///////////////////////////////////// ////////////////////////////////////
		List<Discount> list = discountService.getDiscountList(((User) session.getAttribute("user")).getUserId());

		for (Discount temp : list) {
			if (temp.getDiscountCoupon().getMinimum_price() > product.getPrice()) {
				list.remove(temp);
			}
		}
		model.addAttribute("list", list);

		///////////////////////////////////// 해당 유저가 보유한 쿠폰정보
		///////////////////////////////////// ////////////////////////////////////
		model.addAttribute("product", product);

		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆ 선택된 쿠폰 " + list);

		return new ModelAndView("forward:/purchase/addPurchaseView.jsp");
	}

	@RequestMapping(value = "addPurchase", method = RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase, @RequestParam("prodNo") int prodNo,
			@RequestParam("discountCoupon") int discountNo, HttpSession session) throws Exception {
		// 왠지 모르지만 @ModelAttribute값을 받아오지 못하여 HttpServletRequest로 처리

		Discount discount = discountService.findDiscount(discountNo);
		Product product = prodService.getProduct(prodNo);

		// 상품 할인가 적용
		if (discount != null) {
			int totalPrice = product.getPrice();
			int discountPrice = (int) Math
					.floor(totalPrice * (discount.getDiscountCoupon().getDiscountRatio() / 100.0));

			if (discountPrice > discount.getDiscountCoupon().getMaximumDiscount()) {
				totalPrice = totalPrice - discount.getDiscountCoupon().getMaximumDiscount();
			} else {
				totalPrice = totalPrice - discountPrice;
			}
			System.out.println("★★★★★★★★★★★★★★★★★★★★★선택된 쿠폰 : " + discount.getDiscountCoupon().getCouponName());
			System.out.println("★★★★★★★★★★★★★★★★★★★★★★할인 금액 : " + discountPrice);

			// 사용한 쿠폰 삭제
			discountService.deleteDiscount(discountNo);
			product.setPrice(totalPrice);
		}

		purchase.setBuyer((User) session.getAttribute("user"));
		purchase.setPurchaseProd(product);

		service.addPurchase(purchase);

		return new ModelAndView("forward:/purchase/addPurchase.jsp", "purchase", purchase);
	}

	@RequestMapping("getPurchase")
	public ModelAndView getPuchase(@RequestParam("tranNo") int tranNo) throws Exception {

		Purchase purchase = service.getPurchase(tranNo);

		return new ModelAndView("forward:/purchase/getPurchase.jsp", "purchase", purchase);
	}

	@RequestMapping("listPurchase")
	public ModelAndView listPuchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception {

		// 현재 페이지값이 없으면 첫번째 페이지로 설정
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		User buyer = (User) session.getAttribute("user");

		Map<String, Object> map = service.getPurchaseList(search, buyer.getUserId());

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("count")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);

		ModelAndView modelAndView = new ModelAndView();

		// Model 과 View 연결
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);

		return modelAndView;
	}

	@RequestMapping(value = "updatePurchase", method = RequestMethod.POST)
	public ModelAndView updatePuchase(@ModelAttribute("purchase") Purchase purchase) throws Exception {

		service.updatePurcahse(purchase);

		String url = "forward:/purchase/getPurchase?tranNo=" + purchase.getTranNo();
		return new ModelAndView(url, "purchase", purchase);
	}

	@RequestMapping(value = "updatePurchase", method = RequestMethod.GET)
	public ModelAndView updatePuchaseView(@RequestParam("tranNo") int tranNo) throws Exception {

		Purchase purchase = service.getPurchase(tranNo);		
		 return new ModelAndView("forward:/purchase/updatePurchaseView.jsp","purchase",purchase);		 
	}

	@RequestMapping("updateTranCode")
	public ModelAndView updateTranCode(@RequestParam("tranNo") int tranNo, @RequestParam("tranCode") String tranCode)
			throws Exception {

		Purchase purchase = service.getPurchase(tranNo);
		purchase.setTranCode(tranCode);

		// TranCode를 업데이트 한다.
		service.updateTranCode(purchase);

		return new ModelAndView("forward:/common/alertView.jsp","message", "상품을 수령하셨습니다.");
	}

	@RequestMapping("updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo") int prodNo,
			@RequestParam("tranCode") String tranCode) throws Exception {

		Purchase purchase = new Purchase();

		purchase = service.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);

		// TransCode 업데이트
		service.updateTranCode(purchase);

		// return new ModelAndView("redirect:/product/listProduct?menu=manage");
		return new ModelAndView("forward:/common/alertView.jsp", "message", "상품이 배송 처리되었습니다.");
	}

}
