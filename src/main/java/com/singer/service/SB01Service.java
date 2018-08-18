package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.singer.vo.SB01Vo;

public interface SB01Service {
	public int insertSB01Vo(SB01Vo sb01Vo) throws Exception;

	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception;

	public List<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo) throws Exception;

	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo, String userid) throws Exception;

	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception;

	public int likeSB01Vo(SB01Vo sb01Vo) throws Exception;

	public int hateSB01Vo(SB01Vo sb01Vo) throws Exception;

	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception;

	public int insertVideo(Map<String, Object> hashMap) throws Exception;

	public HashMap<String, Object> selectVideo(SB01Vo sb01Vo) throws Exception;
}
