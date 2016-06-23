package com.starterkit.restfx.dataprovider.data;

public class BookVO {
	private Long id;
    private String title;
    private String authors;
    private StatusVO status;
    
	public BookVO(String title, String authors, StatusVO status) {
		this.title = title;
		this.authors = authors;
		this.status = status;
	}

	public BookVO(Long id, String title, String authors, StatusVO status) {
		super();
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.status = status;
	}

	// REV: kod do usuniecia
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public StatusVO getStatus() {
		return status;
	}

	public void setStatus(StatusVO status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}
