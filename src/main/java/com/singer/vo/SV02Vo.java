package com.singer.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SV02Vo extends SuperVo {

	private static final long serialVersionUID = 5374258406705298004L;
	private int seq;
	private int idx;
	private String content;
	private String regdate;
	private String userid;
	private int voted;
	private int totVoted;
	private int votedYn;
	private List<SV02Vo> sv02Vos;

}
