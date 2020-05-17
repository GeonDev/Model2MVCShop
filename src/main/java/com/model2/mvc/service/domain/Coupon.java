package com.model2.mvc.service.domain;

public class Coupon {
	private int couponNo;
	private String couponName;
	private int discountRatio;
	private int maximumDiscount;
	private int minimum_price;
	
	public Coupon(){
	}

	public int getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(int couponNo) {
		this.couponNo = couponNo;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getDiscountRatio() {
		return discountRatio;
	}

	public void setDiscountRatio(int discountRatio) {
		this.discountRatio = discountRatio;
	}

	public int getMaximumDiscount() {
		return maximumDiscount;
	}

	public void setMaximumDiscount(int maximumDiscount) {
		this.maximumDiscount = maximumDiscount;
	}

	public int getMinimum_price() {
		return minimum_price;
	}

	public void setMinimum_price(int minimum_price) {
		this.minimum_price = minimum_price;
	}

	@Override
	public String toString() {
		return "Coupon [couponNo=" + couponNo + ", couponName=" + couponName + ", discountRatio=" + discountRatio
				+ ", maximumDiscount=" + maximumDiscount + ", minimum_price=" + minimum_price + "]";
	}
	
	
}