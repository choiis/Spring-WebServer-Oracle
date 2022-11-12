package com.singer.application.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sm.SM02Dao;
import com.singer.domain.entity.sm.SM02Entity;

@Service
public class SM02Service {

	@Autowired
	private SM02Dao sm02Dao;

	@Transactional(rollbackFor = { Exception.class })
	public int insertSM02Vo(SM02Entity sm02Entity, String userid) throws Exception {

		sm02Entity.setUserid(userid);
		sm02Entity.setRegdate(DateUtil.getTodayTime());
		return sm02Dao.insertSM02Vo(sm02Entity);
	}

	public List<SM02Entity> selectSM02Vo(SM02Entity sm02Entity, String userid) throws Exception {

		sm02Entity.setUserid(userid);
		return sm02Dao.selectSM02Vo(sm02Entity);
	}

	public SM02Entity selectOneSM02Vo(SM02Entity sm02Entity) throws Exception {
		return sm02Dao.selectOneSM02Vo(sm02Entity);
	}

	public int deleteSM02Vo(SM02Entity sm02Entity, String userid) throws Exception {

		sm02Entity.setUserid(userid);
		return sm02Dao.deleteSM02Vo(sm02Entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int updateSM02Vo(SM02Entity sm02Entity) throws Exception {

		return sm02Dao.updateSM02Vo(sm02Entity);
	}

}
