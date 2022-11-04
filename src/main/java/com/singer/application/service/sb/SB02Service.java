package com.singer.application.service.sb;

import java.util.List;

import com.singer.domain.vo.sb.SB02Vo;

public interface SB02Service {
	public int insertSB02Vo(SB02Vo sb02Vo, String userid) throws Exception;

	public int likeSB02Vo(SB02Vo sb02Vo) throws Exception;

	public List<SB02Vo> selectSB02Vo(SB02Vo sb02Vo, String userid) throws Exception;

	public int updateSB02Vo(SB02Vo sb02Vo) throws Exception;

	public int deleteSB02Vo(SB02Vo sb02Vo) throws Exception;

}
