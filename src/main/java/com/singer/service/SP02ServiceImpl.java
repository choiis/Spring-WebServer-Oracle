package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.common.DateUtil;
import com.singer.dao.SP02Dao;
import com.singer.vo.SP02Vo;

@Service("sp02Service")
public class SP02ServiceImpl implements SP02Service {

	@Resource(name = "sp02Dao")
	private SP02Dao sp02Dao;

	@Override
	public int insertSP02Vo(SP02Vo sp02Vo) throws Exception {

		sp02Vo.setRegdate(DateUtil.getTodayTime());

		return sp02Dao.insertSP02Vo(sp02Vo);
	}

	@Override
	public int likeSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sp02Dao.likeSP02Vo(sp02Vo);
	}

	@Override
	public List<SP02Vo> selectSP02Vo(SP02Vo sp02Vo, String userid) throws Exception {

		List<SP02Vo> list = sp02Dao.selectSP02Vo(sp02Vo);

		Stream<SP02Vo> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return list;
	}

	@Override
	public int updateSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sp02Dao.updateSP02Vo(sp02Vo);
	}

	@Override
	public int deleteSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sp02Dao.deleteSP02Vo(sp02Vo);
	}

}
