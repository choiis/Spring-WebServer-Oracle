package com.singer.service;

import java.util.List;

import com.singer.vo.SM01Vo;

public interface SMI1Service {

	public List<SM01Vo> selectSMI1Vo(String searchCode, String searchParam) throws Exception;
}
