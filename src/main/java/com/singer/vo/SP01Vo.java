package com.singer.vo;

import org.springframework.web.multipart.MultipartFile;

public class SP01Vo extends SuperVo {
	private int seq;
	private String title;
	private String text;
	private String userid;
	private String regdate;
	private String state;
	private String ptype;
	private int hit;
	private int good;
	private MultipartFile explain;
	private int reply;
	private int selection;
	private String findText;
	private String registerid;

	private String sessionid;
	private String datelog;
	private String goodlog;
	private String hatelog;

	public SP01Vo() {
		super();
	}

	public SP01Vo(int seq, String title, String text, String userid, String regdate, String state, String ptype,
			int hit, int good, MultipartFile explain, int reply, int selection, String findText, String registerid,
			String sessionid, String datelog, String goodlog, String hatelog) {
		super();
		this.seq = seq;
		this.title = title;
		this.text = text;
		this.userid = userid;
		this.regdate = regdate;
		this.state = state;
		this.ptype = ptype;
		this.hit = hit;
		this.good = good;
		this.explain = explain;
		this.reply = reply;
		this.selection = selection;
		this.findText = findText;
		this.registerid = registerid;
		this.sessionid = sessionid;
		this.datelog = datelog;
		this.goodlog = goodlog;
		this.hatelog = hatelog;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public MultipartFile getExplain() {
		return explain;
	}

	public void setExplain(MultipartFile explain) {
		this.explain = explain;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
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

	public String getRegisterid() {
		return registerid;
	}

	public void setRegisterid(String registerid) {
		this.registerid = registerid;
	}

	@Override
	public String toString() {
		return "SP01Vo [seq=" + seq + ", title=" + title + ", text=" + text + ", userid=" + userid + ", regdate="
				+ regdate + ", state=" + state + ", ptype=" + ptype + ", hit=" + hit + ", good=" + good + ", explain="
				+ explain + ", reply=" + reply + ", selection=" + selection + ", findText=" + findText + ", registerid="
				+ registerid + ", sessionid=" + sessionid + ", datelog=" + datelog + ", goodlog=" + goodlog
				+ ", hatelog=" + hatelog + "]";
	}

}
