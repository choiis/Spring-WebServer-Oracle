package com.singer.application.service.sr;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sr.SR02Dao;
import com.singer.domain.entity.sr.SR01Vo;
import com.singer.domain.entity.sr.SR02Vo;

@Service
public class SR02Service {

	@Autowired
	private SR02Dao sr02Dao;

	public SR01Vo insertSR02Vo(SR02Vo sr02Vo, String sessionid) throws Exception {

		sr02Vo.setUserid(sessionid);
		sr02Vo.setRegdate(DateUtil.getTodayTime());
		sr02Dao.insertSR02Vo(sr02Vo);

		SR01Vo sr01Vo = new SR01Vo();
		sr01Vo.setSeq(sr02Vo.getSeq());
		return sr02Dao.selectGradeSR02Vo(sr01Vo);
	}

	public SR01Vo selectOneSR02Vo(SR01Vo sr01Vo, String userid) throws Exception {
		sr01Vo.setUserid(userid);
		return sr02Dao.selectOneSR02Vo(sr01Vo);
	}

	public int deleteSR02Vo(SR01Vo sr01Vo) throws Exception {

		return sr02Dao.deleteSR02Vo(sr01Vo);
	}

}
