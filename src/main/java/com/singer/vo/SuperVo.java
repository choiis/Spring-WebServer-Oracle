package com.singer.vo;

import com.singer.common.Constants;

public class SuperVo {
	protected int adminyn;
	protected boolean deleteYn;
	protected String showDate;
	protected int totCnt;
	protected int startRownum;
	protected int endRownum;
	protected int nowPage;
	protected int rowPerPage;
	protected int result;
	protected int like;

	protected SuperVo() {
		this.rowPerPage = Constants.ROW_PER_PAGE;
	}

	public int getAdminyn() {
		return adminyn;
	}

	public void setAdminyn(int adminyn) {
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
		this.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE + 1);
		this.setEndRownum(nowPage * Constants.ROW_PER_PAGE);
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	@Override
	public String toString() {
		return "SuperVo [adminyn=" + adminyn + ", deleteYn=" + deleteYn + ", showDate=" + showDate + ", totCnt="
				+ totCnt + ", startRownum=" + startRownum + ", endRownum=" + endRownum + ", nowPage=" + nowPage
				+ ", rowPerPage=" + rowPerPage + ", result=" + result + ", like=" + like + "]";
	}

}
