package com.eBook.Backend.models.response;

public class BookRes {
	private String bId;
	private byte[] bPreview;
	private String bTitle;
	private String bAuthor;
	private String bGenre;
	private double bPrice;
	
	public BookRes(byte[] bPreview, String bTitle, String bAuthor, String bGenre, double bPrice) {
		super();
		this.bPreview = bPreview;
		this.bTitle = bTitle;
		this.bAuthor = bAuthor;
		this.bGenre = bGenre;
		this.bPrice = bPrice;
	}
	
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public byte[] getbPreview() {
		return bPreview;
	}
	public void setbPreview(byte[] bPreview) {
		this.bPreview = bPreview;
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
