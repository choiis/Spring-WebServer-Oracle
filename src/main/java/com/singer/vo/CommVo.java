package com.singer.vo;

public class CommVo extends SuperVo {

	private String codecd;

	private String codenm;

	private String codegrp;

	private String username;

	private String regdate;

	public CommVo() {
		super();
	}

	public CommVo(String codecd, String codenm, String codegrp, String username, String regdate) {
		super();
		this.codecd = codecd;
		this.codenm = codenm;
		this.codegrp = codegrp;
		this.username = username;
		this.regdate = regdate;
	}

	public String getCodecd() {
		return codecd;
	}

	public void setCodecd(String codecd) {
		this.codecd = codecd;
	}

	public String getCodenm() {
		return codenm;
	}

	public void setCodenm(String codenm) {
		this.codenm = codenm;
	}

	public String getCodegrp() {
		return codegrp;
	}

	public void setCodegrp(String codegrp) {
		this.codegrp = codegrp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "CommVo [codecd=" + codecd + ", codenm=" + codenm + ", codegrp=" + codegrp + ", username=" + username
				+ ", regdate=" + regdate + "]";
	}

}