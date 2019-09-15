package com.singer.service;

import java.util.List;

import com.singer.vo.CommVo;

public interface CommService {
	public List<CommVo> selectCode(CommVo vo) throws Exception;

	public List<CommVo> selectMenu(String authlevel) throws Exception;

	public List<CommVo> insertMenu(CommVo commVo, String userid, String authlevel) throws Exception;

	public List<CommVo> updateMenu(CommVo commVo, String userid, String authlevel) throws Exception;

	public List<CommVo> deleteMenu(CommVo commVo, String authlevel) throws Exception;

	public List<CommVo> selectCodeGrp(CommVo commVo) throws Exception;

	public List<CommVo> insertCode(CommVo commVo, String userid) throws Exception;

	public List<CommVo> deleteCode(CommVo commVo) throws Exception;

	public int updateCode(CommVo commVo) throws Exception;
}
