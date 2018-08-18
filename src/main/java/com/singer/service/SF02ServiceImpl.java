package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SF02Dao;
import com.singer.vo.SF02Vo;

@Service("sf02Service")
public class SF02ServiceImpl implements SF02Service {

	@Resource(name = "sf02Dao")
	private SF02Dao sf02Dao;

	@Override
	public int insertSF02Vo(SF02Vo sb02) throws Exception {

		sb02.setRegdate(DateUtil.getTodayTime());

		return sf02Dao.insertSF02Vo(sb02);
	}

	@Override
	public int likeSF02Vo(SF02Vo sb02) throws Exception {
		return sf02Dao.likeSF02Vo(sb02);
	}

	@Override
	public List<SF02Vo> selectSF02Vo(SF02Vo sb02, String userid) throws Exception {

		int nowPage = sb02.getNowPage();
		sb02.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sb02.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);

		List<SF02Vo> list = sf02Dao.selectSF02Vo(sb02);

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getUserid().equals(userid)) {
				list.get(i).setDeleteYn(true);
			}
		}

		return list;
	}

	@Override
	public int updateSF02Vo(SF02Vo sb02) throws Exception {
		return sf02Dao.updateSF02Vo(sb02);
	}

	@Override
	public int deleteSF02Vo(SF02Vo sb02) throws Exception {
		return sf02Dao.deleteSF02Vo(sb02);
	}

}
