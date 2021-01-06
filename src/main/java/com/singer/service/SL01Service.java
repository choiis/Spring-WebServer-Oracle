package com.singer.service;

import java.util.List;

import com.singer.vo.SL01Vo;

public interface SL01Service {

	public List<SL01Vo> selectSL01(SL01Vo sl01Vo) throws Exception;

	public int insertChatLog(SL01Vo sl01Vo) throws Exception;
}
