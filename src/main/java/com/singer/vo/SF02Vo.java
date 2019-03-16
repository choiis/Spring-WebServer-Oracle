package com.singer.vo;

import java.util.List;

public class SF02Vo extends SuperVo {
	private int seq;
	private int seq01;
	private String text;
	private String userid;
	private String regdate;
	private int good;

	private List<SF02Vo> list;

	public SF02Vo() {
		super();
	}

	public SF02Vo(int seq, int seq01, String text, String userid, String regdate, int good, List<SF02Vo> list) {
		super();
		this.seq = seq;
		this.seq01 = seq01;
		this.text = text;
		this.userid = userid;
		this.regdate = regdate;
		this.good = good;
		this.list = list;
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

	public List<SF02Vo> getList() {
		return list;
	}

	public void setList(List<SF02Vo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SF02Vo [seq=" + seq + ", seq01=" + seq01 + ", text=" + text + ", userid=" + userid + ", regdate="
				+ regdate + ", good=" + good + ", list=" + list + "]";
	}

}
