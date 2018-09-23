package com.singer.service;

import java.util.List;

import com.singer.vo.SM02Vo;

public interface SM02Service {
	public int insertSM02Vo(SM02Vo sm02Vo) throws Exception;

	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo) throws Exception;

	public SM02Vo selectOneSM02Vo(SM02Vo sm02Vo) throws Exception;

	public int deleteSM02Vo(SM02Vo sm02Vo) throws Exception;

	public int updateSM02Vo(SM02Vo sm02Vo) throws Exception;
}
