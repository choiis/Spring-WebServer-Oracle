package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.DateUtil;
import com.singer.dao.SV04Dao;
import com.singer.vo.SV04Vo;

@Service
public class SV04ServiceImpl implements SV04Service {

	@Inject
	private SV04Dao sv04Dao;

	@Override
	public int insertSV04Vo(SV04Vo sv04Vo, String userid) throws Exception {
		if (StringUtils.isEmpty(sv04Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}

		sv04Vo.setUserid(userid);
		sv04Vo.setRegdate(DateUtil.getTodayTime());

		return sv04Dao.insertSV04Vo(sv04Vo);
	}

	@Override
	public int likeSV04Vo(SV04Vo sv04Vo) throws Exception {
		return sv04Dao.likeSV04Vo(sv04Vo);
	}

	@Override
	public List<SV04Vo> selectSV04Vo(SV04Vo sv04Vo, String userid) throws Exception {
		if (sv04Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
			sv04Vo.setTotCnt(sv04Dao.selectSV04Total(sv04Vo));
		}
		List<SV04Vo> list;
		if (sv04Vo.getParents() > 0) {
			list = sv04Dao.selectReplySV04Vo(sv04Vo);
		} else {
			list = sv04Dao.selectSV04Vo(sv04Vo);
		}

		Stream<SV04Vo> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return list;
	}

	@Override
	public int updateSV04Vo(SV04Vo sv04Vo) throws Exception {
		return sv04Dao.updateSV04Vo(sv04Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int deleteSV04Vo(SV04Vo sv04Vo) throws Exception {

		if (sv04Vo.getParents() > 0) {
			sv04Dao.deleteChild(sv04Vo);
			sv04Vo.setParents(0);
		}
		return sv04Dao.deleteSV04Vo(sv04Vo);
	}

}
