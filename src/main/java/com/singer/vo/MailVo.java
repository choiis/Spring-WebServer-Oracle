package com.singer.vo;

public class MailVo {

	private String email;
	private String title;
	private String contents;

	public MailVo() {

	}

	public MailVo(String email, String title, String contents) {
		super();
		this.email = email;
		this.title = title;
		this.contents = contents;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "MailVo [email=" + email + ", title=" + title + ", contents=" + contents + "]";
	}

}
