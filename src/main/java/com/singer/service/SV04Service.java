package com.singer.service;

import java.util.List;

import com.singer.vo.SV04Vo;

public interface SV04Service {

	public int insertSV04Vo(SV04Vo sv04Vo, String userid) throws Exception;

	public int likeSV04Vo(SV04Vo sv04Vo) throws Exception;

	public List<SV04Vo> selectSV04Vo(SV04Vo sv04Vo, String userid) throws Exception;

	public int updateSV04Vo(SV04Vo sv04Vo) throws Exception;

	public int deleteSV04Vo(SV04Vo sv04Vo, String sessionid) throws Exception;
}
