package com.singer.vo;

public class SuperVo {
	private String adminyn;
	private boolean deleteYn;
	private String showDate;
	private int totCnt;
	private int startRownum;
	private int endRownum;
	private int nowPage;

	public SuperVo() {

	}

	public SuperVo(String adminyn, boolean deleteYn, String showDate, int totCnt, int startRownum, int endRownum,
			int nowPage) {
		super();
		this.adminyn = adminyn;
		this.deleteYn = deleteYn;
		this.showDate = showDate;
		this.totCnt = totCnt;
		this.startRownum = startRownum;
		this.endRownum = endRownum;
		this.nowPage = nowPage;
	}

	public String getAdminyn() {
		return adminyn;
	}

	public void setAdminyn(String adminyn) {
		this.adminyn = adminyn;
	}

	public boolean isDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(boolean deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public int getStartRownum() {
		return startRownum;
	}

	public void setStartRownum(int startRownum) {
		this.startRownum = startRownum;
	}

	public int getEndRownum() {
		return endRownum;
	}

	public void setEndRownum(int endRownum) {
		this.endRownum = endRownum;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	@Override
	public String toString() {
		return "SuperVo [adminyn=" + adminyn + ", deleteYn=" + deleteYn + ", totCnt=" + totCnt + ", startRownum="
				+ startRownum + ", endRownum=" + endRownum + ", nowPage=" + nowPage + "]";
	}

}
