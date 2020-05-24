package com.model2.mvc.web.product;


import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String addProduct(@ModelAttribute("product") Product prod) throws Exception {		
		
		
		//상품추가시 날짜에 (-) 제거		
		prod.setManuDate(prod.getManuDate().replaceAll("-", ""));		
		
		service.addProduct(prod);
		
		return "forward:/product/addProduct.jsp";
	}
	
	
	
	// 이미지 업로드를 위한 테스트 코드 (현재 작동하지 않음)
	public String addProduct(HttpServletRequest request) throws Exception {
		
		if(FileUpload.isMultipartContent(request)) {
			String temDir = "C:\\workspace\\Model2MVCShop\\WebContent\\images\\uploadFiles\\";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024*1204*10);
			fileUpload.setSizeThreshold(1024*100);
			
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				Product product = new Product();
				StringTokenizer token = null;
				
				java.util.List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();
				
				for(int i = 0;i<Size;i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					if(fileItem.isFormField()) {
						
						if(fileItem.getFieldName().equals("manuDate")) {
							token= new StringTokenizer(fileItem.getString("euc-kr"),"-");
							String manuDate = token.nextToken()+token.nextToken();
							product.setManuDate(manuDate);							
						}
						else if(fileItem.getFieldName().equals("prodName")) {
							product.setProdName(fileItem.getString("euc-kr"));
						}else if(fileItem.getFieldName().equals("prodDetail")){
							product.setProdDetail(fileItem.getString("euc-kr"));
						}else if(fileItem.getFieldName().equals("price")) {
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						}else { //파일 형식이면
							if(fileItem.getSize()>0) {
								int idx = fileItem.getName().lastIndexOf("\\");
								if(idx == -1) {
									idx = fileItem.getName().lastIndexOf("/");
								}
								String fileName = fileItem.getName().substring(idx+1);
								product.setFileName(fileName);
								try {
									//java.io.File uploadedFile = new File(temDir,fileName);
									File uploadedFile = new File();
									//fileItem.write(uploadedFile);
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e);
								}
							}else {
								product.setFileName("../../images/empty.GIF");
							}
						}//else
					}//for
				}
					service.addProduct(product);
					request.setAttribute("product", product);					
				}else {
					int overSize = (request.getContentLength()/1000000);
					System.out.println("<script>alert('파일 크기는 1MB입니다. 올리신 파일은 "+overSize+ "MB 입니다.');");
					System.out.println("history.back();</script>");
				}				
			}else {
				System.out.println("인코딩 타입이 multipart/form-date 가 아닙니다.");
			}	
		
		return "forword:/product/getProduct.jsp";
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
