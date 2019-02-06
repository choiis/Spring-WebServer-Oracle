package com.singer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.dao.SV02Dao;
import com.singer.vo.SV02Vo;

@Service("sv02Service")
public class SV02ServiceImpl implements SV02Service {

	@Resource(name = "sv02Dao")
	private SV02Dao sv02Dao;

	@Override
	public int updateSV01Vo(SV02Vo sv02Vo) throws Exception {
		return sv02Dao.updateSV02Vo(sv02Vo);
	}

}
