package com.singer.domain.vo.sm;

import com.singer.domain.vo.BoardVo;
import java.util.List;

import com.singer.common.util.CommonUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SM02Vo extends BoardVo {

	private static final long serialVersionUID = -2264127018331896497L;
	private List<SM02Vo> list;

	public void setList(List<SM02Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
			super.setNowPage(list.get(0).getNowPage());
		} else {
			super.setTotCnt(0);
			super.setNowPage(0);
		}
	}

}
