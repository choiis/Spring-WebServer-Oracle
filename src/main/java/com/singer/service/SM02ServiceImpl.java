package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SM02Dao;
import com.singer.vo.SM02Vo;

@Service("sm02Service")
public class SM02ServiceImpl implements SM02Service {
	@Resource(name = "sm02Dao")
	private SM02Dao sm02Dao;

	@Transactional
	@Override
	public List<SM02Vo> insertSM02Vo(SM02Vo sm02Vo) throws Exception {

		sm02Vo.setRegdate(DateUtil.getTodayTime());

		sm02Dao.insertSM02Vo(sm02Vo);
		
		int nowPage = sm02Vo.getNowPage();
		sm02Vo.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sm02Vo.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);
		
		return sm02Dao.selectSM02Vo(sm02Vo);
	}

	@Transactional
	@Override
	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo) throws Exception {

		int nowPage = sm02Vo.getNowPage();
		sm02Vo.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sm02Vo.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);

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
		return sm02Dao.updateSM02Vo(sm02Vo);
	}

}
