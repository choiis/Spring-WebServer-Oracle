package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;
import com.singer.common.Constants.YES_NO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SB01Vo extends BoardVo {

	private static final long serialVersionUID = 5522985426890393310L;

	private String video;

	private YES_NO videobool;

	private List<SB01Vo> list;

	public void setList(List<SB01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}
