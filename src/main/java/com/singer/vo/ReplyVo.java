package com.singer.vo;

public class ReplyVo extends SuperVo {

	protected int seq;
	protected int seq01;
	protected int parents;
	protected String text;
	protected String userid;
	protected String regdate;
	protected int good;
	protected int reply;

	protected ReplyVo() {
		super();
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getSeq01() {
		return seq01;
	}

	public void setSeq01(int seq01) {
		this.seq01 = seq01;
	}

	public int getParents() {
		return parents;
	}

	public void setParents(int parents) {
		this.parents = parents;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

}
