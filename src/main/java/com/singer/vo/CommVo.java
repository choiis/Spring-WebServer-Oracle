package com.singer.vo;

import java.util.List;

public class CommVo extends SuperVo {

	private String codecd;

	private String codenm;

	private String codegrp;

	private String username;

	private String regdate;

	private String reguser;

	private String menucd;

	private String menunm;

	private String menuurl;

	private String authlevel;

	private String moduser;

	private String moddate;

	private String codegrpnm;

	private List<CommVo> commList;

	public CommVo() {
		super();
	}

	public CommVo(String codecd, String codenm, String codegrp, String username, String regdate, String reguser,
			String menucd, String menunm, String menuurl, String authlevel, String moduser, String moddate,
			String codegrpnm, List<CommVo> commList) {
		super();
		this.codecd = codecd;
		this.codenm = codenm;
		this.codegrp = codegrp;
		this.username = username;
		this.regdate = regdate;
		this.reguser = reguser;
		this.menucd = menucd;
		this.menunm = menunm;
		this.menuurl = menuurl;
		this.authlevel = authlevel;
		this.moduser = moduser;
		this.moddate = moddate;
		this.codegrpnm = codegrpnm;
		this.commList = commList;
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

	public String getReguser() {
		return reguser;
	}

	public void setReguser(String reguser) {
		this.reguser = reguser;
	}

	public String getMenucd() {
		return menucd;
	}

	public void setMenucd(String menucd) {
		this.menucd = menucd;
	}

	public String getMenunm() {
		return menunm;
	}

	public void setMenunm(String menunm) {
		this.menunm = menunm;
	}

	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	public String getAuthlevel() {
		return authlevel;
	}

	public void setAuthlevel(String authlevel) {
		this.authlevel = authlevel;
	}

	public String getModuser() {
		return moduser;
	}

	public void setModuser(String moduser) {
		this.moduser = moduser;
	}

	public String getModdate() {
		return moddate;
	}

	public void setModdate(String moddate) {
		this.moddate = moddate;
	}

	public String getCodegrpnm() {
		return codegrpnm;
	}

	public void setCodegrpnm(String codegrpnm) {
		this.codegrpnm = codegrpnm;
	}

	public List<CommVo> getCommList() {
		return commList;
	}

	public void setCommList(List<CommVo> commList) {
		this.commList = commList;
	}

	@Override
	public String toString() {
		return "CommVo [codecd=" + codecd + ", codenm=" + codenm + ", codegrp=" + codegrp + ", username=" + username
				+ ", regdate=" + regdate + ", reguser=" + reguser + ", menucd=" + menucd + ", menunm=" + menunm
				+ ", menuurl=" + menuurl + ", authlevel=" + authlevel + ", moduser=" + moduser + ", moddate=" + moddate
				+ ", codegrpnm=" + codegrpnm + ", commList=" + commList + "]";
	}

}