package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.dao.CommDao;
import com.singer.vo.CommVo;

@Service("commService")
public class CommServiceImpl implements CommService {

	@Resource(name = "commDao")
	private CommDao commDao;

	@Override
	public List<CommVo> selectCode(CommVo vo) throws Exception {
		return commDao.selectCode(vo);
	}

	@Override
	public List<CommVo> selectMenu(CommVo vo, String authlevel) throws Exception {
		vo.setAuthlevel(authlevel);
		return commDao.selectMenu(vo);
	}

}
