package com.singer.service;

import java.util.List;

import com.singer.vo.SP02Vo;

public interface SP02Service {
	public int insertSP02Vo(SP02Vo sp02Vo) throws Exception;

	public int likeSP02Vo(SP02Vo sp02Vo) throws Exception;

	public List<SP02Vo> selectSP02Vo(SP02Vo sp02Vo, String userid) throws Exception;

	public int updateSP02Vo(SP02Vo sp02Vo) throws Exception;

	public int deleteSP02Vo(SP02Vo sp02Vo) throws Exception;

}
