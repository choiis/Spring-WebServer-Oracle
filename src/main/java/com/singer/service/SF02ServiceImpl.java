package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.SF02Dao;
import com.singer.vo.SF02Vo;

@Service
public class SF02ServiceImpl implements SF02Service {

	@Inject
	private SF02Dao sf02Dao;

	@Override
	public int insertSF02Vo(SF02Vo sf02Vo, String userid) throws Exception {
		if (CommonUtil.isNull(sf02Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}
		sf02Vo.setUserid(userid);
		sf02Vo.setRegdate(DateUtil.getTodayTime());

		return sf02Dao.insertSF02Vo(sf02Vo);
	}

	@Override
	public int likeSF02Vo(SF02Vo sf02Vo) throws Exception {
		return sf02Dao.likeSF02Vo(sf02Vo);
	}

	@Override
	public List<SF02Vo> selectSF02Vo(SF02Vo sf02Vo, String userid) throws Exception {

		if (sf02Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
			sf02Vo.setTotCnt(sf02Dao.selectSF02Total(sf02Vo));
		}
		List<SF02Vo> list;
		if (sf02Vo.getParents() > 0) {
			list = sf02Dao.selectReplySF02Vo(sf02Vo);
		} else {
			list = sf02Dao.selectSF02Vo(sf02Vo);
		}

		Stream<SF02Vo> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return list;
	}

	@Override
	public int updateSF02Vo(SF02Vo sf02Vo) throws Exception {
		return sf02Dao.updateSF02Vo(sf02Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int deleteSF02Vo(SF02Vo sf02Vo) throws Exception {

		if (sf02Vo.getParents() > 0) {
			sf02Dao.deleteChild(sf02Vo);
			sf02Vo.setParents(0);
		}
		return sf02Dao.deleteSF02Vo(sf02Vo);
	}

}
