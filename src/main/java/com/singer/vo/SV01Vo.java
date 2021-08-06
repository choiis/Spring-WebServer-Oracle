package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;

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
public class SV01Vo extends BoardVo {

	private static final long serialVersionUID = -3009192462921744979L;
	private int recall;
	private int votedCnt;
	private int multiselect;

	private List<SV01Vo> list;
	private List<SV02Vo> sv02Vos;

	private int votedYn;

	public void setList(List<SV01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}
