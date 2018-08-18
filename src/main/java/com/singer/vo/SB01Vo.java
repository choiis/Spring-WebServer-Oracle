package com.singer.vo;

import org.springframework.web.multipart.MultipartFile;

public class SB01Vo extends SuperVo {
	private int seq;
	private String title;
	private String text;
	private String userid;
	private String regdate;
	private int hit;
	private int good;
	private MultipartFile video;
	private int reply;
	private int selection;
	private String findText;

	public SB01Vo() {
		super();
	}

	public SB01Vo(int seq, String title, String text, String userid, String regdate, int hit, int good,
			MultipartFile video, int reply, int selection, String findText) {
		super();
		this.seq = seq;
		this.title = title;
		this.text = text;
		this.userid = userid;
		this.regdate = regdate;
		this.hit = hit;
		this.good = good;
		this.video = video;
		this.reply = reply;
		this.selection = selection;
		this.findText = findText;
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

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public MultipartFile getVideo() {
		return video;
	}

	public void setVideo(MultipartFile video) {
		this.video = video;
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

	@Override
	public String toString() {
		return "SB01Vo [seq=" + seq + ", title=" + title + ", text=" + text + ", userid=" + userid + ", regdate="
				+ regdate + ", hit=" + hit + ", good=" + good + ", video=" + video + ", reply=" + reply + ", selection="
				+ selection + ", findText=" + findText + "]";
	}

}
