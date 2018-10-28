package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.dao.SL01Dao;
import com.singer.vo.SL01Vo;

@Service("sl01Service")
public class SL01ServiceImpl implements SL01Service {

	@Resource(name = "sl01Dao")
	private SL01Dao sl01Dao;

	@Override
	public List<SL01Vo> selectSL01(SL01Vo sl01Vo) throws Exception {
		return sl01Dao.selectSL01(sl01Vo);
	}

	@Override
	public int insertSL01(SL01Vo sl01Vo) throws Exception {
		return sl01Dao.insertSL01(sl01Vo);
	}

}
