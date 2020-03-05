package com.singer.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SF02Vo extends ReplyVo {

	private static final long serialVersionUID = 7773635527529423007L;
	private List<SF02Vo> list;

	public void setList(List<SF02Vo> list) {
		this.list = list;
	}

}
