package com.singer.vo;

import java.io.Serializable;

import com.singer.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperVo implements Serializable, Cloneable {

	private static final long serialVersionUID = -594197827136251262L;
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

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		this.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE + 1);
		this.setEndRownum(nowPage * Constants.ROW_PER_PAGE);
	}

}
