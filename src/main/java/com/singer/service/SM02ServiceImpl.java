package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.AppException;
import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.SM02Dao;
import com.singer.vo.SM02Vo;

@Service("sm02Service")
public class SM02ServiceImpl implements SM02Service {
	@Resource(name = "sm02Dao")
	private SM02Dao sm02Dao;

	@Transactional
	@Override
	public int insertSM02Vo(SM02Vo sm02Vo) throws Exception {

		if (CommonUtil.isNull(sm02Vo.getTitle())) {
			throw new AppException("제목을 필수 입력해야 합니다");
		}
		if (CommonUtil.isNull(sm02Vo.getText())) {
			throw new AppException("내용을 필수 입력해야 합니다");
		}

		sm02Vo.setRegdate(DateUtil.getTodayTime());
		return sm02Dao.insertSM02Vo(sm02Vo);
	}

	@Transactional
	@Override
	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo) throws Exception {

		return sm02Dao.selectSM02Vo(sm02Vo);
	}

	@Transactional
	@Override
	public SM02Vo selectOneSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sm02Dao.selectOneSM02Vo(sm02Vo);
	}

	@Transactional
	@Override
	public int deleteSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sm02Dao.deleteSM02Vo(sm02Vo);
	}

	@Transactional
	@Override
	public int updateSM02Vo(SM02Vo sm02Vo) throws Exception {
		if (CommonUtil.isNull(sm02Vo.getTitle())) {
			throw new AppException("제목을 필수 입력해야 합니다");
		}
		if (CommonUtil.isNull(sm02Vo.getText())) {
			throw new AppException("내용을 필수 입력해야 합니다");
		}

		return sm02Dao.updateSM02Vo(sm02Vo);
	}

}
