package com.singer.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SM01Vo extends SuperVo {

	private String userid;
	private String passwd;
	private String username;
	private String brth;
	private String grade;
	private String regdate;
	private String phone;
	private String email;
	private String usertype;
	private MultipartFile photo;

	private String device;
	private String browser;

	private List<SM01Vo> list;

	public SM01Vo() {
		super();
	}

	public SM01Vo(String userid, String passwd, String username, String brth, String grade, String regdate,
			String phone, String email, String usertype, MultipartFile photo, String device, String browser) {
		super();
		this.userid = userid;
		this.passwd = passwd;
		this.username = username;
		this.brth = brth;
		this.grade = grade;
		this.regdate = regdate;
		this.phone = phone;
		this.email = email;
		this.usertype = usertype;
		this.photo = photo;
		this.device = device;
		this.browser = browser;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBrth() {
		return brth;
	}

	public void setBrth(String brth) {
		this.brth = brth;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public List<SM01Vo> getList() {
		return list;
	}

	public void setList(List<SM01Vo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SM01Vo [userid=" + userid + ", passwd=" + passwd + ", username=" + username + ", brth=" + brth
				+ ", grade=" + grade + ", regdate=" + regdate + ", phone=" + phone + ", email=" + email + ", usertype="
				+ usertype + ", photo=" + photo + ", device=" + device + ", browser=" + browser + "]";
	}

}