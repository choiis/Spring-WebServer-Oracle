package com.singer.service;

import java.util.List;

import com.singer.vo.CommVo;

public interface CommService {
	public List<CommVo> selectCode(CommVo vo) throws Exception;

	public List<CommVo> selectMenu(CommVo vo, String authlevel) throws Exception;
}
