package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService service;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//����Ʈ ������
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//�߰� ��ǰ ���� �Է� ȭ��
	@RequestMapping("/addProductView.do")
	public String  addProductView() {	
		
		return "redirect:/product/addProductView.jsp";
	}
	
	
	//��ǰ �߰��� ��� ȭ��
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product prod) throws Exception {	
		
		
		//��ǰ�߰��� ��¥�� (-) ����		
		prod.setManuDate(prod.getManuDate().replaceAll("-", ""));		
		
		service.addProduct(prod);
		
		return "forward:/product/addProduct.jsp";
	}
	
	
	@RequestMapping("/getProduct.do")
	public String getProduct( @RequestParam("prodNo") int prodNo, @RequestParam(value ="menu", required=false) String menu, Model model) throws Exception {				
		
		System.out.println("�ڡڡڡڿ���� �Ѿ");
		
		Product prod = service.getProduct(prodNo);
			
		
		model.addAttribute("product", prod);
		
		if(menu!=null) {
		//�Ǹ���, ������ ��� ����
		model.addAttribute("menu", menu);				
		
			if(menu.equals("manage")) {
				return "forward:/product/updateProduct.jsp";				
			}
		}		
		
		return "forward:/product/getProduct.jsp";
	}	
	
	
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search, @RequestParam("menu") String menu , Model model ) throws Exception {
		
		//���� ���������� ������ ù��° �������� ����
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=service.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		//�Ǹ���, ������ ��� ����
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product prod ) throws Exception {
	
		//���޹��� ��ǰ ���� ������Ʈ
		service.updateProduct(prod);			
	
		
		return "forward:/getProduct.do?prodNo="+prod.getProdNo();
	}
	
	
	
	
	
}
