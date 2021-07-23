package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.SR03Dao;
import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.vo.SR03Vo;

@Service
public class SR03ServiceImpl implements SR03Service {

	@Inject
	private SR03Dao sr03Dao;

	@Override
	public int insertSR03Vo(SR03Vo sr03Vo, String userid) throws Exception {
		if (CommonUtil.isNull(sr03Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}
		sr03Vo.setUserid(userid);
		sr03Vo.setRegdate(DateUtil.getTodayTime());

		return sr03Dao.insertSR03Vo(sr03Vo);
	}

	@Override
	public int likeSR03Vo(SR03Vo sr03Vo) throws Exception {
		return sr03Dao.likeSR03Vo(sr03Vo);
	}

	@Override
	public List<SR03Vo> selectSR03Vo(SR03Vo sr03Vo, String userid) throws Exception {
		if (sr03Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
			sr03Vo.setTotCnt(sr03Dao.selectSR03Total(sr03Vo));
		}
		List<SR03Vo> list;
		if (sr03Vo.getParents() > 0) {
			list = sr03Dao.selectReplySR03Vo(sr03Vo);
		} else {
			list = sr03Dao.selectSR03Vo(sr03Vo);
		}

		Stream<SR03Vo> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return list;
	}

	@Override
	public int updateSR03Vo(SR03Vo sr03Vo) throws Exception {
		return sr03Dao.updateSR03Vo(sr03Vo);
	}

	@Override
	public int deleteSR03Vo(SR03Vo sr03Vo) throws Exception {

		if (sr03Vo.getParents() > 0) {
			sr03Dao.deleteChild(sr03Vo);
			sr03Vo.setParents(0);
		}
		return sr03Dao.deleteSR03Vo(sr03Vo);
	}

}
