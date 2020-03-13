package com.singer.service;

import java.util.List;

import com.singer.vo.SV01Vo;

public interface SV01Service {
	public int insertSV01Vo(SV01Vo sv01Vo, String userid) throws Exception;

	public List<SV01Vo> selectSV01Vo(SV01Vo sv01Vo) throws Exception;

	public List<SV01Vo> selectFindSV01Vo(SV01Vo sv01Vo) throws Exception;

	public SV01Vo selectOneSV01Vo(SV01Vo sv01Vo, String userid) throws Exception;

	public int updateSV01Vo(SV01Vo sv01Vo) throws Exception;

	public int deleteSV01Vo(SV01Vo sv01Vo, String sessionid) throws Exception;

	public SV01Vo likeSV01Vo(SV01Vo sv01Vo, String sessionid) throws Exception;

	public SV01Vo hateSV01Vo(SV01Vo sv01Vo, String sessionid) throws Exception;
}
