package com.model2.mvc.service.domain;

public class FileImages {

	private int imagesNo;
	private int	prodNo;
	private String fileName;
	
	
	public int getImagesNo() {
		return imagesNo;
	}
	public void setImagesNo(int imagesNo) {
		this.imagesNo = imagesNo;
	}
	public int getProdNo() {
		return prodNo;
	}
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		return "Images [imagesNo=" + imagesNo + ", prodNo=" + prodNo + ", fileName=" + fileName + "]";
	}
	
	
	
}
