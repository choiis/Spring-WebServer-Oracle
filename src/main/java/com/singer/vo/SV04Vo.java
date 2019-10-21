package com.singer.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SV04Vo extends ReplyVo {

	private List<SV04Vo> list;

	public void setList(List<SV04Vo> list) {
		this.list = list;
	}

}
