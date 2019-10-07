package com.singer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.common.DateUtil;
import com.singer.dao.SR02Dao;
import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.vo.SR01Vo;

@Service("sr02Service")
public class SR02ServiceImpl implements SR02Service {

	@Resource(name = "sr02Dao")
	private SR02Dao sr02Dao;

	@Override
	public int insertSR02Vo(SR01Vo sr01Vo, String sessionid) throws Exception {
		if (sr01Vo.getGrade() < 0 || sr01Vo.getGrade() > 5) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_7);
		}

		sr01Vo.setUserid(sessionid);
		sr01Vo.setRegdate(DateUtil.getTodayTime());
		return sr02Dao.insertSR02Vo(sr01Vo);
	}

	@Override
	public SR01Vo selectOneSR02Vo(SR01Vo sr01Vo, String userid) throws Exception {
		sr01Vo.setUserid(userid);
		return sr02Dao.selectOneSR02Vo(sr01Vo);
	}

	@Override
	public int deleteSR02Vo(SR01Vo sr01Vo, String sessionid) throws Exception {
		sr01Vo.setUserid(sessionid);
		return sr02Dao.deleteSR02Vo(sr01Vo);
	}

}
