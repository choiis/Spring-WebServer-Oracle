package com.singer.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.singer.common.DateUtil;
import com.singer.dao.SR02Dao;
import com.singer.vo.SR01Vo;
import com.singer.vo.SR02Vo;

@Service
public class SR02ServiceImpl implements SR02Service {

	@Inject
	private SR02Dao sr02Dao;

	@Override
	public SR01Vo insertSR02Vo(SR02Vo sr02Vo, String sessionid) throws Exception {

		sr02Vo.setUserid(sessionid);
		sr02Vo.setRegdate(DateUtil.getTodayTime());
		sr02Dao.insertSR02Vo(sr02Vo);

		SR01Vo sr01Vo = new SR01Vo();
		sr01Vo.setSeq(sr02Vo.getSeq());
		return sr02Dao.selectGradeSR02Vo(sr01Vo);
	}

	@Override
	public SR01Vo selectOneSR02Vo(SR01Vo sr01Vo, String userid) throws Exception {
		sr01Vo.setUserid(userid);
		return sr02Dao.selectOneSR02Vo(sr01Vo);
	}

	@Override
	public int deleteSR02Vo(SR01Vo sr01Vo) throws Exception {

		return sr02Dao.deleteSR02Vo(sr01Vo);
	}

}
