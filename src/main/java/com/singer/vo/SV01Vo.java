package com.singer.vo;

import java.util.List;

public class SV01Vo extends SuperVo {
	private int seq;
	private String title;
	private String text;
	private String userid;
	private String regdate;
	private int hit;
	private int recall;

	private String findText;

	private String sessionid;

	private int multiselect;

	private List<SV01Vo> list;

	private List<SV02Vo> sv02Vos;

	private int votedYn;

	public SV01Vo() {
		super();
	}

	public SV01Vo(int seq, String title, String text, String userid, String regdate, int hit, int recall,
			String findText, String sessionid, int multiselect, List<SV01Vo> list, List<SV02Vo> sv02Vos, int votedYn) {
		super();
		this.seq = seq;
		this.title = title;
		this.text = text;
		this.userid = userid;
		this.regdate = regdate;
		this.hit = hit;
		this.recall = recall;
		this.findText = findText;
		this.sessionid = sessionid;
		this.multiselect = multiselect;
		this.list = list;
		this.sv02Vos = sv02Vos;
		this.votedYn = votedYn;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
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
	}

	public int getRecall() {
		return recall;
	}

	public void setRecall(int recall) {
		this.recall = recall;
	}

	@Override
	public String toString() {
		return "SV01Vo [seq=" + seq + ", title=" + title + ", text=" + text + ", userid=" + userid + ", regdate="
				+ regdate + ", hit=" + hit + ", recall=" + recall + ", findText=" + findText + ", sessionid="
				+ sessionid + ", multiselect=" + multiselect + ", list=" + list + ", sv02Vos=" + sv02Vos + ", votedYn="
				+ votedYn + "]";
	}

}
