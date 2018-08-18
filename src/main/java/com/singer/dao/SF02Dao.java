package com.singer.dao;

import java.util.List;

import com.singer.vo.SF02Vo;

public interface SF02Dao {

	public int insertSF02Vo(SF02Vo sf02Vo) throws Exception;

	public int likeSF02Vo(SF02Vo sf02Vo) throws Exception;

	public List<SF02Vo> selectSF02Vo(SF02Vo sf02Vo) throws Exception;

	public int updateSF02Vo(SF02Vo sf02Vo) throws Exception;

	public int deleteSF02Vo(SF02Vo sf02Vo) throws Exception;

	public int delete_seqSF02Vo(SF02Vo sf02Vo) throws Exception;

}
