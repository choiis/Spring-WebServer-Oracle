package com.singer.dao;

import java.util.List;

import com.singer.vo.SB02Vo;

public interface SB02Dao {

	public int insertSB02Vo(SB02Vo sb02Vo) throws Exception;

	public int likeSB02Vo(SB02Vo sb02Vo) throws Exception;

	public List<SB02Vo> selectSB02Vo(SB02Vo sb02Vo) throws Exception;

	public int updateSB02Vo(SB02Vo sb02Vo) throws Exception;

	public int deleteSB02Vo(SB02Vo sb02Vo) throws Exception;

	public int delete_seqSB02Vo(SB02Vo sb02Vo) throws Exception;

}
