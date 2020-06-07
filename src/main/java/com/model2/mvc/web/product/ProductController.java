package com.model2.mvc.web.product;


import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	private static final String SAVE_PATH = "/images/uploadFiles/";
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService service;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@Autowired
	private ServletContext context;
	
	
	//����Ʈ ������
	public ProductController() {
		System.out.println(this.getClass());		
	}
	
	//�߰� ��ǰ ���� �Է� ȭ��
	@RequestMapping(value="addProduct", method=RequestMethod.GET )
	public String  addProduct() {	
		
		return "redirect:/product/addProductView.jsp";
	}
	
	
	//��ǰ �߰��� ��� ȭ��
	@RequestMapping(value="addProduct")
	public String addProduct(@ModelAttribute("product") Product prod, @RequestParam("uploadFile") List<MultipartFile> files ,HttpServletRequest request) throws Exception {		
		
		MultipartFile file = files.get(0);
		
		//���� ���ε� ���� - ��Ƽ ������ ������ �ֵ��� ����
		File f =new File(SAVE_PATH+file.getOriginalFilename());
		
		//���ϴ� ��ġ�� ���� ����
		file.transferTo(f);	
		
		//Ȥ�� ���ε��� �̻��� ������ �־� FileName �缳��
		prod.setFileName(file.getOriginalFilename());
		
		//��ǰ�߰��� ��¥�� (-) ����		
		prod.setManuDate(prod.getManuDate().replaceAll("-", ""));		
		
		service.addProduct(prod);
		
		return "forward:/product/addProduct.jsp";
	}
	

	
	@RequestMapping(value="getProduct")
	public String getProduct( @RequestParam("prodNo") int prodNo, @RequestParam(value ="menu") String menu, Model model, @CookieValue(value = "history", defaultValue = "") String history, HttpServletResponse response) throws Exception {				
						
		Product prod = service.getProduct(prodNo);			
		
		model.addAttribute("product", prod);
		
		if(menu!=null) {
		//�Ǹ���, ������ ��� ����
		model.addAttribute("menu", menu);				
		
			if(menu.equals("manage")) {
				return "forward:/product/updateProduct.jsp";				
			}
		}		
		
		
		/***************************��Ű�� ��ǰ��ȣ �߰�*****************************/		
		history += prodNo+",";
		history = history.replace("null", "");	
		history = history.trim();
		
		//CommonUtil.checkOvperlap(",", history, Integer.toString(prodNo))
		Cookie cookie = new Cookie("history", history);
		cookie.setPath("/");
		response.addCookie(cookie);		
		
		System.out.println("��Ű ���� : "+ history);
		/***************************��Ű�� ��ǰ��ȣ �߰�*****************************/
		
		return "forward:/product/getProduct.jsp";
	}	
	
	
	
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search, @RequestParam("menu") String menu , Model model ) throws Exception {
			
		//���� ���������� ������ ù��° �������� ����
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		
		if(search.getSearchOrder()==null) {
			search.setSearchOrder("0");
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
	
	@RequestMapping(value="updateProduct")
	public String updateProduct(@ModelAttribute("product") Product prod ,Model model, @RequestParam("uploadFile") List<MultipartFile> files) throws Exception {
   
		MultipartFile file = files.get(0);
		
		if(file !=null) {		
					
			File f =new File(SAVE_PATH+file.getOriginalFilename());
					
			//���ϴ� ��ġ�� ���� ����
			file.transferTo(f);	
					
			//Ȥ�� ���ε��� �̻��� ������ �־� FileName �缳��
			prod.setFileName(file.getOriginalFilename());
		}
		
		//���޹��� ��ǰ ���� ������Ʈ
		service.updateProduct(prod);					
			
		model.addAttribute("prodNo", prod.getProdNo());
		
		return "forward:/product/getProduct";
	}	
	
}
