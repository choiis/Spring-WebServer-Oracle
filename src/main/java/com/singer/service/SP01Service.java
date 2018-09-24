package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.singer.vo.SP01Vo;

public interface SP01Service {
	public int insertSP01Vo(SP01Vo sp01Vo) throws Exception;

	public List<SP01Vo> selectSP01Vo(SP01Vo sp01Vo) throws Exception;

	public List<SP01Vo> selectFindSP01Vo(SP01Vo sp01Vo) throws Exception;

	public List<SP01Vo> selectMyList(SP01Vo sp01Vo, String userid) throws Exception;

	public SP01Vo selectOneSP01Vo(SP01Vo sp01Vo, String userid) throws Exception;

	public int updateSP01Vo(SP01Vo sp01Vo) throws Exception;

	public int buySP01Vo(SP01Vo sp01Vo, String userid) throws Exception;

	public int sellSP01Vo(SP01Vo sp01Vo) throws Exception;

	public int cancelSP01Vo(SP01Vo sp01Vo) throws Exception;

	public int likeSP01Vo(SP01Vo sp01Vo, String sessionid) throws Exception;

	public int hateSP01Vo(SP01Vo sp01Vo, String sessionid) throws Exception;

	public int deleteSP01Vo(SP01Vo sp01Vo) throws Exception;

	public int insertExplain(Map<String, Object> hashMap) throws Exception;

	public HashMap<String, Object> selectExplain(SP01Vo sp01Vo) throws Exception;
}
