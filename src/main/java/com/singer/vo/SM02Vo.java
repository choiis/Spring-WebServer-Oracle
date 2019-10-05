package com.singer.vo;

import java.util.List;

import com.singer.common.CommonUtil;

public class SM02Vo extends BoardVo {

	private List<SM02Vo> list;

	public SM02Vo() {
		super();
	}

	public List<SM02Vo> getList() {
		return list;
	}

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
