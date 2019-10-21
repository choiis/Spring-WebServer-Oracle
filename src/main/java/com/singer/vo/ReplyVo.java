package com.singer.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyVo extends SuperVo {

	protected int seq;
	protected int seq01;
	protected int parents;
	protected String text;
	protected String userid;
	protected String regdate;
	protected int good;
	protected int reply;

	protected ReplyVo() {

	}
}
