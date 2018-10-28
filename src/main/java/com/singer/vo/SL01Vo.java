package com.singer.vo;

public class SL01Vo extends SuperVo {

	private String userid;
	private String logintime;
	private String ip;
	private String browser;
	private String device;
	private String lcnt;
	private String username;
	private String usertype;

	public SL01Vo() {
		super();
	}

	public SL01Vo(String userid, String logintime, String ip, String browser, String device, String lcnt,
			String username, String usertype) {
		super();
		this.userid = userid;
		this.logintime = logintime;
		this.ip = ip;
		this.browser = browser;
		this.device = device;
		this.lcnt = lcnt;
		this.username = username;
		this.usertype = usertype;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getLcnt() {
		return lcnt;
	}

	public void setLcnt(String lcnt) {
		this.lcnt = lcnt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@Override
	public String toString() {
		return "SL01Vo [userid=" + userid + ", logintime=" + logintime + ", ip=" + ip + ", browser=" + browser
				+ ", device=" + device + ", lcnt=" + lcnt + ", username=" + username + ", usertype=" + usertype + "]";
	}

}
