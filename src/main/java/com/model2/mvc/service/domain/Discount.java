package com.model2.mvc.service.domain;

import java.sql.Date;

// 보유한 쿠폰 정보 

public class Discount {
	private int discountNo;
	private User owner;
	private Coupon discountCoupon;
	private Date issuedDate;
	private Date expirationDate;
	
	
	public Discount(){
	}


	public int getDiscountNo() {
		return discountNo;
	}


	public void setDiscountNo(int discountNo) {
		this.discountNo = discountNo;
	}


	public User getOwner() {
		return owner;
	}


	public void setOwner(User owner) {
		this.owner = owner;
	}


	public Coupon getDiscountCoupon() {
		return discountCoupon;
	}


	public void setDiscountCoupon(Coupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}


	public Date getIssuedDate() {
		return issuedDate;
	}


	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}


	public Date getExpirationDate() {
		return expirationDate;
	}


	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	@Override
	public String toString() {
		return "Discount [discountNo=" + discountNo + ", owner=" + owner + ", discountCoupon=" + discountCoupon
				+ ", issuedDate=" + issuedDate + ", expirationDate=" + expirationDate + "]";
	}

	
	
	
}