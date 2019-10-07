package com.singer.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.singer.common.CommonUtil;

public class SR01Vo extends BoardVo {

	private String markertitle;
	private double mapx;
	private double mapy;
	private MultipartFile photo;

	private String sessionid;
	private String datelog;
	private String goodlog;
	private String hatelog;

	private double grade;
	private double avggrade;
	private int idx;
	private int photocnt;

	private List<SR01Vo> list;

	public SR01Vo() {
		super();
	}

	public String getMarkertitle() {
		return markertitle;
	}

	public void setMarkertitle(String markertitle) {
		this.markertitle = markertitle;
	}

	public double getMapx() {
		return mapx;
	}

	public void setMapx(double mapx) {
		this.mapx = mapx;
	}

	public double getMapy() {
		return mapy;
	}

	public void setMapy(double mapy) {
		this.mapy = mapy;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getDatelog() {
		return datelog;
	}

	public void setDatelog(String datelog) {
		this.datelog = datelog;
	}

	public String getGoodlog() {
		return goodlog;
	}

	public void setGoodlog(String goodlog) {
		this.goodlog = goodlog;
	}

	public String getHatelog() {
		return hatelog;
	}

	public void setHatelog(String hatelog) {
		this.hatelog = hatelog;
	}

	public List<SR01Vo> getList() {
		return list;
	}

	public void setList(List<SR01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public double getAvggrade() {
		return avggrade;
	}

	public void setAvggrade(double avggrade) {
		this.avggrade = avggrade;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getPhotocnt() {
		return photocnt;
	}

	public void setPhotocnt(int photocnt) {
		this.photocnt = photocnt;
	}

}
