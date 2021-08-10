package com.singer.vo;

import com.singer.common.Constants.USER_CODE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SL01Vo extends SuperVo {

	private static final long serialVersionUID = -4783391185566339335L;

	private String userid;
	private String logintime;
	private String logdate;
	private String ip;
	private String browser;
	private String device;
	private String lcnt;
	private String username;
	private USER_CODE usertype;
	private String message;
	private String chatroom;
}
