package com.singer.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SB02Vo extends ReplyVo {

	private static final long serialVersionUID = -2832282665649129317L;
	private List<SB02Vo> list;

	public void setList(List<SB02Vo> list) {
		this.list = list;
	}

}
