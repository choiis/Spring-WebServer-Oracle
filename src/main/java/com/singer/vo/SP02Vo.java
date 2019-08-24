package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;

public class SP02Vo extends SuperVo {

	private int seq;
	private int seq01;
	private String text;
	private String userid;
	private String regdate;
	private int good;

	private List<SP02Vo> list;

	public SP02Vo() {
		super();
	}

	public SP02Vo(int seq, int seq01, String text, String userid, String regdate, int good, List<SP02Vo> list) {
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

	public List<SP02Vo> getList() {
		return list;
	}

	public void setList(List<SP02Vo> list) {
		this.list = list;

		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
			super.setNowPage(this.list.get(0).getNowPage());
		} else {
			super.setTotCnt(0);
			super.setNowPage(0);
		}
	}

	@Override
	public String toString() {
		return "SP02Vo [seq=" + seq + ", seq01=" + seq01 + ", text=" + text + ", userid=" + userid + ", regdate="
				+ regdate + ", good=" + good + ", list=" + list + "]";
	}

}
