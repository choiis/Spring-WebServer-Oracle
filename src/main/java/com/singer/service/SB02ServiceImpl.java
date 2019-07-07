package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.common.DateUtil;
import com.singer.dao.SB02Dao;
import com.singer.vo.SB02Vo;

@Service("sb02Service")
public class SB02ServiceImpl implements SB02Service {

	@Resource(name = "sb02Dao")
	private SB02Dao sb02Dao;

	@Override
	public int insertSB02Vo(SB02Vo sb02Vo) throws Exception {

		sb02Vo.setRegdate(DateUtil.getTodayTime());

		return sb02Dao.insertSB02Vo(sb02Vo);
	}

	@Override
	public int likeSB02Vo(SB02Vo sb02Vo) throws Exception {
		return sb02Dao.likeSB02Vo(sb02Vo);
	}

	@Override
	public List<SB02Vo> selectSB02Vo(SB02Vo sb02Vo, String userid) throws Exception {

		List<SB02Vo> list = sb02Dao.selectSB02Vo(sb02Vo);

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getUserid().equals(userid)) {
				list.get(i).setDeleteYn(true);
			}
		}

		return list;
	}

	@Override
	public int updateSB02Vo(SB02Vo sb02Vo) throws Exception {
		return sb02Dao.updateSB02Vo(sb02Vo);
	}

	@Override
	public int deleteSB02Vo(SB02Vo sb02Vo) throws Exception {
		return sb02Dao.deleteSB02Vo(sb02Vo);
	}

}
