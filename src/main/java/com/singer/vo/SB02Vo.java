package com.singer.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SB02Vo extends ReplyVo {

	private List<SB02Vo> list;

	public void setList(List<SB02Vo> list) {
		this.list = list;
	}

}
