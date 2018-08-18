package com.singer.vo;

public class SM02Vo extends SuperVo {

	private int seq;
	private String userid;
	private String title;
	private String text;
	private String regdate;

	public SM02Vo() {
		super();
	}

	public SM02Vo(int seq, String userid, String title, String text, String regdate) {
		super();
		this.seq = seq;
		this.userid = userid;
		this.title = title;
		this.text = text;
		this.regdate = regdate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "SM02Vo [seq=" + seq + ", userid=" + userid + ", title=" + title + ", text=" + text + ", regdate="
				+ regdate + "]";
	}

}
