package com.singer.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardVo extends SuperVo {

	private static final long serialVersionUID = 7956711521174141824L;
	protected int seq;
	protected String title;
	protected String text;
	protected String userid;
	protected String regdate;
	protected int good;
	protected int hit;

	protected int selection;
	protected String findText;

	protected String sessionid;
	protected String goodlog;
	protected String hatelog;
	protected String datelog;
	protected int reply;

}
