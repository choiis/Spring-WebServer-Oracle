package com.singer.service;

import java.util.List;

import com.singer.vo.SF02Vo;

public interface SF02Service {
	public int insertSF02Vo(SF02Vo sf02Vo, String userid) throws Exception;

	public int likeSF02Vo(SF02Vo sf02Vo) throws Exception;

	public List<SF02Vo> selectSF02Vo(SF02Vo sf02Vo, String userid) throws Exception;

	public int updateSF02Vo(SF02Vo sf02Vo) throws Exception;

	public int deleteSF02Vo(SF02Vo sf02Vo) throws Exception;

}
