package com.singer.domain.entity.sm;

import com.singer.domain.entity.BoardEntity;
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
public class SM02Entity extends BoardEntity {

	private static final long serialVersionUID = -2264127018331896497L;
	private List<SM02Entity> list;

	public void setList(List<SM02Entity> list) {
		this.list = list;
		// �럹�씠吏뺤쓣 �쐞�븳 移댁슫�듃
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
			super.setNowPage(list.get(0).getNowPage());
		} else {
			super.setTotCnt(0);
			super.setNowPage(0);
		}
	}

}
