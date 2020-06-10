package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService prodService;
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET )
	public Product getProduct( @PathVariable int prodNo ) throws Exception{		
		//Business Logic
		return prodService.getProduct(prodNo);
	}
	
	
	@RequestMapping(value="json/listProduct/{page}")
	public List<Product> listProduct(@PathVariable int page) throws Exception {
			
		Search search = new Search(); 
		
		search.setCurrentPage(page);		
		search.setPageSize(pageSize);
		
		search.setSearchOrder("");
		search.setSearchKeyword("");				
		
		return (List<Product>) prodService.getProductList(search).get("list");
	}
	
}