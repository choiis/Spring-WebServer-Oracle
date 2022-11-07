package com.singer.application.service.sm;

import java.util.List;

import com.singer.domain.entity.sm.SM02Vo;

public interface SM02Service {
	public int insertSM02Vo(SM02Vo sm02Vo, String userid) throws Exception;

	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo, String userid) throws Exception;

	public SM02Vo selectOneSM02Vo(SM02Vo sm02Vo) throws Exception;

	public int deleteSM02Vo(SM02Vo sm02Vo, String userid) throws Exception;

	public int updateSM02Vo(SM02Vo sm02Vo) throws Exception;
}
