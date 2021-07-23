package com.singer.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.singer.dao.SL01Dao;
import com.singer.vo.SL01Vo;

@Service
public class SL01ServiceImpl implements SL01Service {

	@Inject
	private SL01Dao sl01Dao;

	@Override
	public List<SL01Vo> selectSL01(SL01Vo sl01Vo) throws Exception {
		return sl01Dao.selectSL01(sl01Vo);
	}

	@Override
	public int insertChatLog(SL01Vo sl01Vo) throws Exception {
		return sl01Dao.insertChatLog(sl01Vo);
	}

}
