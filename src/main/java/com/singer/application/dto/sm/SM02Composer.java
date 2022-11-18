package com.singer.application.dto.sm;

import java.util.ArrayList;
import java.util.List;

import com.singer.common.util.CommonUtil;
import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sm.SM02Entity;

public class SM02Composer {

	public static SM02ListResponse entityListToResponse(List<SM02Entity> list) {
		List<SM02Response> responseList = new ArrayList<>();
		for (SM02Entity entity : list) {
			responseList
					.add(new SM02Response(entity.getTitle(), entity.getText(), entity.getRegdate(), entity.getSeq()));
		}
		int totCnt = 0;
		int nowPage = 0;
		if (list.size() != 0) {
			totCnt = CommonUtil.getPageCnt(list.get(0).getTotCnt());
			nowPage = list.get(0).getNowPage();
		}
		return new SM02ListResponse(responseList, nowPage, totCnt);
	}

	public static SM02Entity requestToEntity(SM02Request request, String userid) {
		SM02Entity entity = new SM02Entity();
		entity.setTitle(request.getTitle());
		entity.setText(request.getText());
		entity.setUserid(userid);
		entity.setRegdate(DateUtil.getTodayTime());
		return entity;
	}

	public static SM02Response entityToResponse(SM02Entity entity) {
		return new SM02Response(entity.getTitle(), entity.getText(), entity.getRegdate(), entity.getSeq());
	}
}
