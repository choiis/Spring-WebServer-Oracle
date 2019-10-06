package com.singer.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.singer.common.CommonUtil;

public class SP01Vo extends BoardVo {

	private String state;
	private String ptype;
	private MultipartFile explain;
	private int reply;
	private int selection;
	private String findText;
	private String registerid;

	private String sessionid;
	private String datelog;
	private String goodlog;
	private String hatelog;

	private List<SP01Vo> list;

	public SP01Vo() {
		super();
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

	public List<SP01Vo> getList() {
		return list;
	}

	public void setList(List<SP01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}
