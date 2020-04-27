package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action{

	public ListPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SearchVO searchVO=new SearchVO();
		
		UserVO buyer = new UserVO();
		
		HttpSession session = request.getSession();		
		buyer = (UserVO)session.getAttribute("user");
		
		int page=1;
		
		//������ �������� ������ �ش� �������� ����
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}	
		
		searchVO.setPage(page);

		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		PurchaseServiceImpl service = new PurchaseServiceImpl();
		String buyerId = buyer.getUserId();
		
		HashMap<String,Object> map=service.getPurchaseList(searchVO, buyerId);

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
				
		return "forward:/purchase/listPurchase.jsp";
	}
	
	
	
	
	

}