package com.eBook.Backend.models.response;


//Class for Book API response .
public class BookRes {
	//Following fields shows what shall be stored in response.
	private String bId;
	private String bTitle;
	private String bAuthor;
	private String bGenre;
	private double bPrice;
	
	//All args constructor.
	public BookRes(String bTitle, String bAuthor, String bGenre, double bPrice) {
		super();
		this.bTitle = bTitle;
		this.bAuthor = bAuthor;
		this.bGenre = bGenre;
		this.bPrice = bPrice;
	}
	
	
	//Getters and setters for the above fields.
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getbTitle() {
		return bTitle;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public String getbAuthor() {
		return bAuthor;
	}
	public void setbAuthor(String bAuthor) {
		this.bAuthor = bAuthor;
	}
	public String getbGenre() {
		return bGenre;
	}
	public void setbGenre(String bGenre) {
		this.bGenre = bGenre;
	}
	public double getbPrice() {
		return bPrice;
	}
	public void setbPrice(double bPrice) {
		this.bPrice = bPrice;
	}
}
