package com.singer.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVo extends SuperVo {
	protected int seq;
	protected String title;
	protected String text;
	protected String userid;
	protected String regdate;
	protected int good;
	protected int hit;

	protected int selection;
	protected String findText;

	protected BoardVo() {

	}

}
