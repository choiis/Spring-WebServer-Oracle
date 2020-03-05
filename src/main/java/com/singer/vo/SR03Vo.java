package com.singer.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SR03Vo extends ReplyVo {

	private static final long serialVersionUID = 3044855339526539286L;
	private List<SR03Vo> list;

	public void setList(List<SR03Vo> list) {
		this.list = list;
	}

}
