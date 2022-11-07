package com.singer.application.service.sm;

import java.util.List;

import com.singer.domain.entity.sm.SM01Vo;

public interface SMI1Service {

	public List<SM01Vo> selectSMI1Vo(String searchCode, String searchParam) throws Exception;
}
