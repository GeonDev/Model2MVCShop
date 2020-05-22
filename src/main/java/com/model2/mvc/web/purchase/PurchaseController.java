package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
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
	
	
	//디폴트 생성자
	public PurchaseController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prodNo")int prodNo) throws Exception {
		
		Product product = prodService.getProduct(prodNo);		
		
		
		return new ModelAndView("forward:/purchase/addPurchaseView.jsp","product",product);
	}
	
	
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,@RequestParam("prodNo")int prodNo, HttpSession session ) throws Exception {
					
		purchase.setBuyer((User)session.getAttribute("user"));		
		purchase.setPurchaseProd(prodService.getProduct(prodNo));
		
		System.out.println("저장상품 명 : "+purchase.getPurchaseProd());
		
		
		service.addPurchase(purchase);		 		
				
		return new ModelAndView("forward:/purchase/addPurchase.jsp","purchase",purchase);
	}
	

	@RequestMapping("/getPurchase.do")
	public ModelAndView getPuchase(@RequestParam("tranNo")int tranNo) throws Exception {
		
		Purchase purchase = service.getPurchase(tranNo);
		
		return new ModelAndView("forward:/purchase/getPurchase.jsp","purchase",purchase);
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPuchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception {
		
		//현재 페이지값이 없으면 첫번째 페이지로 설정
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		User buyer = (User)session.getAttribute("user");		
		
		
		Map<String , Object> map=service.getPurchaseList(search, buyer.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		// Model 과 View 연결
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePuchase.do")
	public ModelAndView updatePuchase(@ModelAttribute("purchase") Purchase purchase  ,@RequestParam("tranNo")int tranNo ) throws Exception {
		
		service.updatePurcahse(purchase);
		
		return new ModelAndView("forward:/purchase/updatePurchase.jsp","purchase", purchase);
	}
	
	
	@RequestMapping("/updatePuchaseView.do")
	public ModelAndView updatePuchaseView(@RequestParam("tranNo")int tranNo) throws Exception {
		
		Purchase purchase = service.getPurchase(tranNo);
		
		return new ModelAndView("forward:/purchase/updatePurchaseView.jsp","purchase",purchase);
	}
	
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@RequestParam("prodNo")int prodNo) throws Exception {
		
		Purchase purchase = service.getPurchase2(prodNo);
		
		service.updateTranCode(purchase);
		
		
		return new ModelAndView("redirect:/listPurchase.do");
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProd( ) {
		
		
		return new ModelAndView("redirect:/listProduct.do?menu=manage");
	}
	

}
