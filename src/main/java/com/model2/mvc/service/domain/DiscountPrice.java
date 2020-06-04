package com.model2.mvc.service.domain;


//쿠폰 할인가 적용을 위한 도메인 클래스
public class DiscountPrice {

	
	private int discountNo;
	private int	price;
	
	
	public DiscountPrice() {
		// TODO Auto-generated constructor stub
	}


	public int getDiscountNo() {
		return discountNo;
	}


	public void setDiscountNo(int discountNo) {
		this.discountNo = discountNo;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "discountPrice [discountNo=" + discountNo + ", price=" + price + "]";
	}
	
	

}
