package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateTranCodeAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));		
		String tranCode = request.getParameter("tranCode").trim();
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = new PurchaseVO();			
		
		purchaseVO = service.getPurchase(tranNo);
		purchaseVO.setTranCode(tranCode);
		
		//TranCode를 업데이트 한다.
		service.updateTranCode(purchaseVO);
		
		//*******************************************************************//
		
		SearchVO searchVO=new SearchVO();
		
		UserVO buyer = new UserVO();
		
		HttpSession session = request.getSession();		
		buyer = (UserVO)session.getAttribute("user");
		
		searchVO.setPage(1);	
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));		
	
		String buyerId = buyer.getUserId();
		
		HashMap<String,Object> map=service.getPurchaseList(searchVO, buyerId);

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);	
		
		
		//*******************************************************************//		
	
		return "forward:/purchase/listPurchase.jsp";
	}

}
