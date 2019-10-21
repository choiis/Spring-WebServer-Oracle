package com.singer.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVo extends SuperVo {
	private int seq;
	private String title;
	private String text;
	private String userid;
	private String regdate;
	private int good;
	private int hit;

	protected BoardVo() {

	}

}
