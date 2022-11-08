package com.singer.application.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sm.SM02Dao;
import com.singer.domain.entity.sm.SM02Vo;

@Service
public class SM02Service {

	@Autowired
	private SM02Dao sm02Dao;

	@Transactional(rollbackFor = { Exception.class })
	public int insertSM02Vo(SM02Vo sm02Vo, String userid) throws Exception {

		sm02Vo.setUserid(userid);
		sm02Vo.setRegdate(DateUtil.getTodayTime());
		return sm02Dao.insertSM02Vo(sm02Vo);
	}

	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo, String userid) throws Exception {

		sm02Vo.setUserid(userid);
		return sm02Dao.selectSM02Vo(sm02Vo);
	}

	public SM02Vo selectOneSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sm02Dao.selectOneSM02Vo(sm02Vo);
	}

	public int deleteSM02Vo(SM02Vo sm02Vo, String userid) throws Exception {

		sm02Vo.setUserid(userid);
		return sm02Dao.deleteSM02Vo(sm02Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int updateSM02Vo(SM02Vo sm02Vo) throws Exception {

		return sm02Dao.updateSM02Vo(sm02Vo);
	}

}
