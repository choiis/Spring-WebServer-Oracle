package com.singer.application.dto.sm;

import java.util.ArrayList;
import java.util.List;

import com.singer.domain.entity.sm.SM01Entity;

public class SMI1Composer {

	public static SMI1ListResponse entityListToResponse(List<SM01Entity> list) {
		List<SMI1Response> responseList = new ArrayList<>();
		for (SM01Entity entity : list) {
			responseList.add(new SMI1Response(entity.getUsername(), entity.getUserid(), entity.getBrth(),
					entity.getRegdate(), entity.getPfnum(), entity.getPcnum(), entity.getPbnum(), entity.getEmail()));
		}
		return new SMI1ListResponse(responseList);

	}
}
