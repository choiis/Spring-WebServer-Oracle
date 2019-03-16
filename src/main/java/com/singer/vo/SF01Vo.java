package com.singer.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SF01Vo extends SuperVo {
	private int seq;
	private String title;
	private String text;
	private String userid;
	private String regdate;
	private int hit;
	private int good;
	private String filename;
	private MultipartFile fileblob;
	private int reply;
	private int selection;
	private String findText;

	private String sessionid;
	private String datelog;
	private String goodlog;
	private String hatelog;

	private List<SF01Vo> list;

	public SF01Vo() {
		super();
	}

	public SF01Vo(int seq, String title, String text, String userid, String regdate, int hit, int good, String filename,
			MultipartFile fileblob, int reply, int selection, String findText, String sessionid, String datelog,
			String goodlog, String hatelog, List<SF01Vo> list) {
		super();
		this.seq = seq;
		this.title = title;
		this.text = text;
		this.userid = userid;
		this.regdate = regdate;
		this.hit = hit;
		this.good = good;
		this.filename = filename;
		this.fileblob = fileblob;
		this.reply = reply;
		this.selection = selection;
		this.findText = findText;
		this.sessionid = sessionid;
		this.datelog = datelog;
		this.goodlog = goodlog;
		this.hatelog = hatelog;
		this.list = list;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public MultipartFile getFileblob() {
		return fileblob;
	}

	public void setFileblob(MultipartFile fileblob) {
		this.fileblob = fileblob;
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

	public List<SF01Vo> getList() {
		return list;
	}

	public void setList(List<SF01Vo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SF01Vo [seq=" + seq + ", title=" + title + ", text=" + text + ", userid=" + userid + ", regdate="
				+ regdate + ", hit=" + hit + ", good=" + good + ", filename=" + filename + ", fileblob=" + fileblob
				+ ", reply=" + reply + ", selection=" + selection + ", findText=" + findText + ", sessionid="
				+ sessionid + ", datelog=" + datelog + ", goodlog=" + goodlog + ", hatelog=" + hatelog + ", list="
				+ list + "]";
	}

}
