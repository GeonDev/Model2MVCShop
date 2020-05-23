package com.model2.mvc.web.purchase;

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
	
	
	//디폴트 생성자
	public PurchaseController() {
		// TODO Auto-generated constructor stub
	}
	
	
	// getProduct.jsp에서 넘어감
	@RequestMapping(value =  "addPurchase", method = RequestMethod.GET)
	public ModelAndView addPurchaseView(@RequestParam("prodNo")int prodNo) throws Exception {
		
		Product product = prodService.getProduct(prodNo);		
		
		
		return new ModelAndView("forward:/purchase/addPurchaseView.jsp","product",product);
	}
	
		
	@RequestMapping(value =  "addPurchase", method = RequestMethod.POST)
	public ModelAndView addPurchase(HttpServletRequest request, @RequestParam("prodNo")int prodNo, HttpSession session ) throws Exception {
		//왠지 모르지만 @ModelAttribute값을 받아오지 못하여 HttpServletRequest로 처리		
		
		Purchase purchase = new Purchase();
		
		purchase.setBuyer((User)session.getAttribute("user"));		
		purchase.setPurchaseProd(prodService.getProduct(prodNo));
						
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		
		service.addPurchase(purchase);		 		
				
		return new ModelAndView("forward:/purchase/addPurchase.jsp","purchase",purchase);
	}
	

	@RequestMapping("getPurchase")
	public ModelAndView getPuchase(@RequestParam("tranNo")int tranNo) throws Exception {
		
		Purchase purchase = service.getPurchase(tranNo);
		
		return new ModelAndView("forward:/purchase/getPurchase.jsp","purchase",purchase);
	}
	
	@RequestMapping("listPurchase")
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
	
	@RequestMapping(value = "updatePuchase", method = RequestMethod.POST)
	public ModelAndView updatePuchase(@ModelAttribute("purchase") Purchase purchase  ,@RequestParam("tranNo")int tranNo ) throws Exception {
		
		service.updatePurcahse(purchase);
		
		return new ModelAndView("forward:/purchase/updatePurchase.jsp","purchase", purchase);
	}
	
	
	@RequestMapping(value = "updatePuchase", method = RequestMethod.GET)
	public ModelAndView updatePuchaseView(@RequestParam("tranNo")int tranNo) throws Exception {
		
		Purchase purchase = service.getPurchase(tranNo);
		
		return new ModelAndView("forward:/purchase/updatePurchaseView.jsp","purchase",purchase);
	}
	
	
	@RequestMapping("updateTranCode")
	public ModelAndView updateTranCode(@RequestParam("tranNo")int tranNo, @RequestParam("tranCode") String tranCode) throws Exception {
		
		
		Purchase purchase = service.getPurchase(tranNo);
		purchase.setTranCode(tranCode);
		
		//TranCode를 업데이트 한다.
		service.updateTranCode(purchase);
		
		
		return new ModelAndView("redirect:/purchase/listPurchase");
	}
	
	@RequestMapping("updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo")int prodNo, @RequestParam("tranCode") String tranCode ) throws Exception {
		
		Purchase purchase = new Purchase();			
		
	
		purchase = service.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		
		//TransCode 업데이트
		service.updateTranCode(purchase);
		
		return new ModelAndView("redirect:/product/listProduct?menu=manage");
	}
	

}
