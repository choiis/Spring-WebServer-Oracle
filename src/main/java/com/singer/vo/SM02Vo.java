package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;

public class SM02Vo extends SuperVo {

	private int seq;
	private String userid;
	private String title;
	private String text;
	private String regdate;

	private List<SM02Vo> list;

	public SM02Vo() {
		super();
	}

	public SM02Vo(int seq, String userid, String title, String text, String regdate, List<SM02Vo> list) {
		super();
		this.seq = seq;
		this.userid = userid;
		this.title = title;
		this.text = text;
		this.regdate = regdate;
		this.list = list;
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

	public List<SM02Vo> getList() {
		return list;
	}

	public void setList(List<SM02Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
			super.setNowPage(list.get(0).getNowPage());
		} else {
			super.setTotCnt(0);
			super.setNowPage(0);
		}
	}

	@Override
	public String toString() {
		return "SM02Vo [seq=" + seq + ", userid=" + userid + ", title=" + title + ", text=" + text + ", regdate="
				+ regdate + ", list=" + list + "]";
	}

}
