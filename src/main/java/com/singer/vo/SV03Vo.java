package com.singer.vo;

public class SV03Vo extends SuperVo {
	private int seq;
	private int idx;
	private String userid;
	private String regdate;

	private String sessionid;

	public SV03Vo() {
		super();
	}

	public SV03Vo(int seq, int idx, String userid, String regdate, String sessionid) {
		super();
		this.seq = seq;
		this.idx = idx;
		this.userid = userid;
		this.regdate = regdate;
		this.sessionid = sessionid;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	@Override
	public String toString() {
		return "SV03Vo [seq=" + seq + ", idx=" + idx + ", userid=" + userid + ", regdate=" + regdate + ", sessionid="
				+ sessionid + "]";
	}

}
