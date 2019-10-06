package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;

public class SV01Vo extends BoardVo {

	private int recall;
	private int votedCnt;
	private int reply;

	private String findText;

	private String sessionid;

	private String goodlog;
	private String hatelog;
	private int multiselect;
	private String datelog;

	private List<SV01Vo> list;
	private List<SV02Vo> sv02Vos;

	private int votedYn;

	public SV01Vo() {
		super();
	}

	public String getFindText() {
		return findText;
	}

	public void setFindText(String findText) {
		this.findText = findText;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public int getMultiselect() {
		return multiselect;
	}

	public void setMultiselect(int multiselect) {
		this.multiselect = multiselect;
	}

	public List<SV02Vo> getSv02Vos() {
		return sv02Vos;
	}

	public void setSv02Vos(List<SV02Vo> sv02Vos) {
		this.sv02Vos = sv02Vos;
	}

	public int getVotedYn() {
		return votedYn;
	}

	public void setVotedYn(int votedYn) {
		this.votedYn = votedYn;
	}

	public List<SV01Vo> getList() {
		return list;
	}

	public void setList(List<SV01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

	public int getRecall() {
		return recall;
	}

	public void setRecall(int recall) {
		this.recall = recall;
	}

	public int getVotedCnt() {
		return votedCnt;
	}

	public void setVotedCnt(int votedCnt) {
		this.votedCnt = votedCnt;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
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

	public String getDatelog() {
		return datelog;
	}

	public void setDatelog(String datelog) {
		this.datelog = datelog;
	}

}
