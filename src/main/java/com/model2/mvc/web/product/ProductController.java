package com.model2.mvc.web.product;


import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
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
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.FileImages;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.fileImages.FileImagesService;
import com.model2.mvc.service.product.ProductService;


@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService service;
	
	@Autowired
	@Qualifier("fileImagesServiceImpl")
	private FileImagesService fileService;
	
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@Value("#{commonProperties['uploadPath']}")
	String uploadPath;
	
	@Autowired
	private ServletContext context;
 		
	
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
	public String addProduct(@ModelAttribute("product") Product prod, @RequestParam("uploadFile") List<MultipartFile> files) throws Exception {		

        String path = context.getRealPath("/");        
        path = path.substring(0,path.indexOf("\\.metadata"));         
        path = path +  uploadPath;              
      
		
		int prodNo = service.getLastProdno()+1;
		
		prod.setProdNo(prodNo);
		//상품추가시 날짜에 (-) 제거		
		prod.setManuDate(prod.getManuDate().replaceAll("-", ""));		
		
		service.addProduct(prod);
		
		
		if(files !=null) {
	        for (MultipartFile multipartFile : files) {
	        	//파일 업로드 구분 - 워크스페이스 경로가 다르면 재 설정을 해야 한다......
	    		File f =new File(path+multipartFile.getOriginalFilename());
	    		
	    		//원하는 위치에 파일 저장
	    		multipartFile.transferTo(f);	    		
	    		
	    		FileImages file = new FileImages(prodNo,multipartFile.getOriginalFilename());
	    		
	    		fileService.addFileImage(file);
			} 			
		}		
		
		return "forward:/product/addProduct.jsp";
	}
	

	
	@RequestMapping(value="getProduct")
	public String getProduct( @RequestParam("prodNo") int prodNo, @RequestParam(value ="menu") String menu, Model model, @CookieValue(value = "history", defaultValue = "") String history, HttpServletResponse response) throws Exception {				
						
		Product prod = service.getProduct(prodNo);	
		
		// 파일 리스트를 받아서 세팅
		//prod.setFileName(fileService.getFileList(prodNo));
		
		model.addAttribute("product", prod);
		
		if(menu!=null) {
		//판매자, 구매자 모드 전달
		model.addAttribute("menu", menu);				
		
			if(menu.equals("manage")) {
				return "forward:/product/updateProduct.jsp";				
			}
		}				
		
		/***************************쿠키에 상품번호 추가*****************************/		
		history += prodNo+",";
		history = history.replace("null", "");	
		history = history.trim();
		
		//CommonUtil.checkOvperlap(",", history, Integer.toString(prodNo))
		Cookie cookie = new Cookie("history", history);
		cookie.setPath("/");
		response.addCookie(cookie);		
		
		System.out.println("쿠키 정보 : "+ history);
		/***************************쿠키에 상품번호 추가*****************************/
		
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
	
	@RequestMapping(value="mainProduct")
	public ModelAndView mainProduct() throws Exception {
		
		Search search = new Search(); 
		
		search.setCurrentPage(1);		
		search.setPageSize(pageSize);
		
		search.setSearchOrder("");
		search.setSearchKeyword("");
				
		
		return new ModelAndView("forward:/main.jsp","list",(List<Product>)service.getProductList(search).get("list"));
	}
	
	
	
	
	@RequestMapping(value="updateProduct")
	public String updateProduct(@ModelAttribute("product") Product prod ,Model model, @RequestParam("uploadFile") List<MultipartFile> files) throws Exception {
			
        String path = context.getRealPath("/");        
        path = path.substring(0,path.indexOf("\\.metadata"));         
        path = path +  uploadPath;
		
       		
		if(files !=null) {
	        for (MultipartFile multipartFile : files) {
	        	//파일 업로드 구분 - 워크스페이스 경로가 다르면 재 설정을 해야 한다......
	    		File f =new File(path+multipartFile.getOriginalFilename());
	    		
	    		//원하는 위치에 파일 저장
	    		multipartFile.transferTo(f);	    		
	    		
	    		FileImages file = new FileImages(prod.getProdNo(),multipartFile.getOriginalFilename());
	    		
	    		fileService.addFileImage(file);
			} 			
		}
		
		//전달받은 상품 정보 업데이트
		service.updateProduct(prod);					
			
		model.addAttribute("prodNo", prod.getProdNo());
		
		return "forward:/product/getProduct?menu=search";
	}
	
	@RequestMapping(value="deleteProductImage")
	public ModelAndView  deleteProductImage(@RequestParam("prodNo")int prodNo, @RequestParam("fileName")String fileName) throws Exception {	
		
		FileImages file = new FileImages(prodNo, fileName);
		
		fileService.deleteFileImage(file);
		return new ModelAndView("forward:/common/alertView.jsp", "message", "선택하신 이미지가 삭제되었습니다.");
	}
	
	
	
}
