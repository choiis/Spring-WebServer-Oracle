package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;

public class SB01Vo extends BoardVo {

	private String video;
	private int reply;
	private int selection;
	private String findText;

	private String sessionid;
	private String datelog;
	private String goodlog;
	private String hatelog;
	private int videobool;

	private List<SB01Vo> list;

	public SB01Vo() {
		super();
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
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

	public int getVideobool() {
		return videobool;
	}

	public void setVideobool(int videobool) {
		this.videobool = videobool;
	}

	public List<SB01Vo> getList() {
		return list;
	}

	public void setList(List<SB01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}
