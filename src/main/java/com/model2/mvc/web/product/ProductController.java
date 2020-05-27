package com.model2.mvc.web.product;


import java.io.File;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService service;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@Value("#{commonProperties['uploadPath']}")
	String uploadPath;
	
	
	//디폴트 생성자
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//추가 상품 정보 입력 화면
	@RequestMapping(value="addProduct", method=RequestMethod.GET )
	public String  addProduct() {	
		
		return "redirect:/product/addProductView.jsp";
	}
	
	
	//상품 추가후 결과 화면
	@RequestMapping(value="addProduct")
	public String addProduct(@ModelAttribute("product") Product prod, @RequestParam("uploadFile") MultipartFile file ,HttpServletRequest request) throws Exception {		
				
	
        System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆현재 절대 경로는 :" +request.getSession().getServletContext().getRealPath("/"));
        
		//파일 업로드 구분 - 워크스페이스 경로가 다르면 재 설정을 해야 한다......
		File f =new File(uploadPath+file.getOriginalFilename());
		
		//원하는 위치에 파일 저장
		file.transferTo(f);	
		
		//혹시 바인딩중 이상이 있을수 있어 FileName 재설정
		prod.setFileName(file.getOriginalFilename());
		
		//상품추가시 날짜에 (-) 제거		
		prod.setManuDate(prod.getManuDate().replaceAll("-", ""));		
		
		service.addProduct(prod);
		
		return "forward:/product/addProduct.jsp";
	}
	

	
	@RequestMapping(value="getProduct")
	public String getProduct( @RequestParam("prodNo") int prodNo, @RequestParam(value ="menu", required=false) String menu, Model model) throws Exception {				
						
		Product prod = service.getProduct(prodNo);
			
		
		model.addAttribute("product", prod);
		
		if(menu!=null) {
		//판매자, 구매자 모드 전달
		model.addAttribute("menu", menu);				
		
			if(menu.equals("manage")) {
				return "forward:/product/updateProduct.jsp";				
			}
		}		
		
		return "forward:/product/getProduct.jsp";
	}	
	
	
	
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search, @RequestParam("menu") String menu , Model model ) throws Exception {
		
		//현재 페이지값이 없으면 첫번째 페이지로 설정
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
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		//판매자, 구매자 모드 전달
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping(value="updateProduct")
	public String updateProduct(@ModelAttribute("product") Product prod ,Model model) throws Exception {
			
		//전달받은 상품 정보 업데이트
		service.updateProduct(prod);			
		//String prodNo = Integer.toString(prod.getProdNo());	
		
		model.addAttribute("prodNo", prod.getProdNo());
		
		return "forward:/product/getProduct";
	}
	
	
	
	
	
}
