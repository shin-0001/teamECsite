package com.internousdev.laravel.dto;

import java.util.Date;

public class CartInfoDTO {

	//カートテーブル
	private int id;
	private String userId;
	private int productId;
	private int productCount;
	private String registDate;
	private String updateDate;
	
	//商品情報テーブル
	private String productName;
	private String productNameKana;
	private int price;
	private String imageFilePath;
	private String imageFileName;
	private Date releaseDate;
	private String releaseCompany;
	private String status;
	private int totalFee;
	
	//カートテーブル
	public int getId() {
		return id;
	}
	
	public void setId(int id) { 
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getProductCount() {
		return productCount;
	}
	
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
	public String getRegistDate() {
		return registDate;
	}
	
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	//商品情報テーブル
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductNameKana() {
		return productNameKana;
	}
	
	public void setProductNameKana(String productNameKana) {
		this.productNameKana = productNameKana;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}
	
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public String getImageFilePath() {
		return imageFilePath;
	}
	
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getReleaseCompany() {
		return releaseCompany;
	}
	
	public void setReleaseCompany(String releaseCompany) {
		this.releaseCompany = releaseCompany;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getTotalFee() {
		return totalFee;
	}
	
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
}
