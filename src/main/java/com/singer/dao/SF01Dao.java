package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.singer.vo.SF01Vo;

public interface SF01Dao {

	public int insertSF01Vo(SF01Vo SF01Vo) throws Exception;

	public List<SF01Vo> selectSF01Vo(SF01Vo SF01Vo) throws Exception;

	public SF01Vo selectOneSF01Vo(SF01Vo SF01Vo) throws Exception;

	public int updateSF01Vo(SF01Vo SF01Vo) throws Exception;

	public int likeSF01Vo(SF01Vo SF01Vo) throws Exception;

	public int hateSF01Vo(SF01Vo SF01Vo) throws Exception;

	public int clickSF01Vo(SF01Vo SF01Vo) throws Exception;

	public int deleteSF01Vo(SF01Vo SF01Vo) throws Exception;

	public int insertFile(Map<String, Object> hashMap) throws Exception;

	public HashMap<String, Object> selectFile(SF01Vo SF01Vo) throws Exception;
}
