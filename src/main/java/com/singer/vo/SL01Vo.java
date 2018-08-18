package com.singer.vo;

public class SL01Vo {

	private String userid;
	private String logintime;
	private String loginurl;
	private String browser;
	private int totCnt;
	private int startRownum;
	private int endRownum;
	private int nowPage;

	public SL01Vo() {

	}

	public SL01Vo(String userid, String logintime, String loginurl, String browser, int totCnt, int startRownum,
			int endRownum, int nowPage) {
		super();
		this.userid = userid;
		this.logintime = logintime;
		this.loginurl = loginurl;
		this.browser = browser;
		this.totCnt = totCnt;
		this.startRownum = startRownum;
		this.endRownum = endRownum;
		this.nowPage = nowPage;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLoginurl() {
		return loginurl;
	}

	public void setLoginurl(String loginurl) {
		this.loginurl = loginurl;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public int getStartRownum() {
		return startRownum;
	}

	public void setStartRownum(int startRownum) {
		this.startRownum = startRownum;
	}

	public int getEndRownum() {
		return endRownum;
	}

	public void setEndRownum(int endRownum) {
		this.endRownum = endRownum;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	@Override
	public String toString() {
		return "SL01Vo [userid=" + userid + ", logintime=" + logintime + ", loginurl=" + loginurl + ", browser="
				+ browser + ", totCnt=" + totCnt + ", startRownum=" + startRownum + ", endRownum=" + endRownum
				+ ", nowPage=" + nowPage + "]";
	}

}
