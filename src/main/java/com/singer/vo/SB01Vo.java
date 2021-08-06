package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;
import com.singer.common.Constants.YES_NO;

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
public class SB01Vo extends BoardVo {

	private static final long serialVersionUID = -6895941086562069423L;

	private String video;

	private String videopath;

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
